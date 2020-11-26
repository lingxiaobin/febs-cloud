package cc.mrbird.febs.auth.service.impl;

import cc.mrbird.febs.common.core.api.DingApiUtil;
import cc.mrbird.febs.auth.entity.BindUser;
import cc.mrbird.febs.auth.entity.KUser;
import cc.mrbird.febs.auth.entity.UserConnection;
import cc.mrbird.febs.auth.manager.UserManager;
import cc.mrbird.febs.auth.mapper.KUserMapper;
import cc.mrbird.febs.auth.mapper.UserMapper;
import cc.mrbird.febs.auth.mapper.UserRoleMapper;
import cc.mrbird.febs.auth.properties.FebsAuthProperties;
import cc.mrbird.febs.auth.service.SocialLoginService;
import cc.mrbird.febs.auth.service.UserConnectionService;
import cc.mrbird.febs.common.core.entity.FebsResponse;
import cc.mrbird.febs.common.core.entity.constant.*;
import cc.mrbird.febs.common.core.entity.system.SystemUser;
import cc.mrbird.febs.common.core.entity.system.UserRole;
import cc.mrbird.febs.common.core.exception.FebsException;
import cc.mrbird.febs.common.core.utils.FebsUtil;
import cn.hutool.core.util.StrUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.taobao.api.ApiException;
import com.xkcoding.justauth.AuthRequestFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author MrBird
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SocialLoginServiceImpl implements SocialLoginService {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String NOT_BIND = "not_bind";
    private static final String SOCIAL_LOGIN_SUCCESS = "social_login_success";

    private final UserManager userManager;
    private final AuthRequestFactory factory;
    private final FebsAuthProperties properties;
    private final PasswordEncoder passwordEncoder;
    private final UserConnectionService userConnectionService;
    private final ResourceOwnerPasswordTokenGranter granter;
    private final RedisClientDetailsService redisClientDetailsService;

    private final KUserMapper kUserMapper;
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;

    @Override
    public AuthRequest renderAuth(String oauthType) throws FebsException {
        return factory.get(getAuthSource(oauthType));
    }

    @Override
    public FebsResponse resolveBind(String oauthType, AuthCallback callback) throws FebsException {
        FebsResponse febsResponse = new FebsResponse();
        AuthRequest authRequest = factory.get(getAuthSource(oauthType));
        AuthResponse<?> response = authRequest.login(resolveAuthCallback(callback));
        if (response.ok()) {
            febsResponse.data(response.getData());
        } else {
            throw new FebsException(String.format("第三方登录失败，%s", response.getMsg()));
        }
        return febsResponse;
    }

    @Override
    public FebsResponse resolveLogin(String oauthType, AuthCallback callback) throws FebsException {
        FebsResponse febsResponse = new FebsResponse();
        AuthRequest authRequest = factory.get(getAuthSource(oauthType));
        AuthResponse<?> response = authRequest.login(resolveAuthCallback(callback));
        if (response.ok()) {
            AuthUser authUser = (AuthUser) response.getData();
            UserConnection userConnection = userConnectionService.selectByCondition(authUser.getSource().toString(), authUser.getUuid());
            if (userConnection == null) {
                febsResponse.message(NOT_BIND).data(authUser);
            } else {
                SystemUser user = userManager.findByName(userConnection.getUserName());
                if (user == null) {
                    throw new FebsException("系统中未找到与第三方账号对应的账户");
                }
                OAuth2AccessToken oAuth2AccessToken = getOauth2AccessToken(user);
                febsResponse.message(SOCIAL_LOGIN_SUCCESS).data(oAuth2AccessToken);
                febsResponse.put(USERNAME, user.getUsername());
            }
        } else {
            throw new FebsException(String.format("第三方登录失败，%s", response.getMsg()));
        }
        return febsResponse;
    }


    @Override
    public FebsResponse resolveLoginDD(String requestAuthCode) throws FebsException {
        FebsResponse febsResponse = new FebsResponse();
        //获取accessToken,注意正是代码要有异常流处理
        String accessToken = DingApiUtil.getToken();
        //获取用户信息
        DingTalkClient client = new DefaultDingTalkClient(DingConstant.URL_GET_USER_INFO);
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(requestAuthCode);
        request.setHttpMethod("GET");
        OapiUserGetuserinfoResponse response;
        try {
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            log.error("钉钉登录异常: {}", e);
            e.printStackTrace();
            return null;
        }
        String userId = response.getUserid();
        KUser kUser = kUserMapper.selectById(userId);
        if (kUser == null) {
            throw new FebsException("系统中未找到与钉钉账号对应的账户");
        }
        if (StringUtils.isEmpty(kUser.getJobnumber())) {
            throw new FebsException("钉钉账号对应的工号为空");
        }
        SystemUser user = userManager.findByName(kUser.getJobnumber());
        if (user == null) {
            user = new SystemUser();
//            throw new FebsException("系统中未找到与第三方账号对应的账户");
            user.setCreateTime(new Date());
            user.setUsername(kUser.getJobnumber());  //用户名 -工号
            user.setDdUserId(kUser.getId());     //钉钉的userId
            user.setName(kUser.getName());
            user.setPosition(kUser.getPosition());
            user.setDeptNameStr(kUser.getDeptName());
            user.setStatus(SystemUser.STATUS_VALID);
            user.setAvatar(SystemUser.DEFAULT_AVATAR);
            String psssWd = new Random().nextInt(999999 - 100000 + 1) + 100000 + "";  // 随机长度的字符串
            user.setPassword(passwordEncoder.encode(psssWd));
            user.setPs(psssWd);
            user.setDeptId(SystemUser.DEFAULT_DEPTID);
            user.setSex(SystemUser.SEX_UNKNOW);
            userMapper.insert(user);

            UserRole userRole = new UserRole();
            userRole.setUserId(user.getUserId());
            userRole.setRoleId(UserRole.DEFAULT_ROLE_ID);
            userRoleMapper.insert(userRole);
        }
        OAuth2AccessToken oAuth2AccessToken = getOauth2AccessToken(user);
        febsResponse.message(SOCIAL_LOGIN_SUCCESS).data(oAuth2AccessToken);
        return febsResponse;
    }

    @Override
    public OAuth2AccessToken bindLogin(BindUser bindUser, AuthUser authUser) throws FebsException {
        SystemUser systemUser = userManager.findByName(bindUser.getBindUsername());
        if (systemUser == null || !passwordEncoder.matches(bindUser.getBindPassword(), systemUser.getPassword())) {
            throw new FebsException("绑定系统账号失败，用户名或密码错误！");
        }
        this.createConnection(systemUser, authUser);
        return this.getOauth2AccessToken(systemUser);
    }

    @Override
    public OAuth2AccessToken signLogin(BindUser registUser, AuthUser authUser) throws FebsException {
        SystemUser user = this.userManager.findByName(registUser.getBindUsername());
        if (user != null) {
            throw new FebsException("该用户名已存在！");
        }
        String encryptPassword = passwordEncoder.encode(registUser.getBindPassword());
        SystemUser systemUser = this.userManager.registUser(registUser.getBindUsername(), encryptPassword);
        this.createConnection(systemUser, authUser);
        return this.getOauth2AccessToken(systemUser);
    }

    @Override
    public void bind(BindUser bindUser, AuthUser authUser) throws FebsException {
        String username = bindUser.getBindUsername();
        if (isCurrentUser(username)) {
            UserConnection userConnection = userConnectionService.selectByCondition(authUser.getSource().toString(), authUser.getUuid());
            if (userConnection != null) {
                throw new FebsException("绑定失败，该第三方账号已绑定" + userConnection.getUserName() + "系统账户");
            }
            SystemUser systemUser = new SystemUser();
            systemUser.setUsername(username);
            this.createConnection(systemUser, authUser);
        } else {
            throw new FebsException("绑定失败，您无权绑定别人的账号");
        }
    }

    @Override
    public void unbind(BindUser bindUser, String oauthType) throws FebsException {
        String username = bindUser.getBindUsername();
        if (isCurrentUser(username)) {
            this.userConnectionService.deleteByCondition(username, oauthType);
        } else {
            throw new FebsException("解绑失败，您无权解绑别人的账号");
        }
    }

    @Override
    public List<UserConnection> findUserConnections(String username) {
        return this.userConnectionService.selectByCondition(username);
    }


    private void createConnection(SystemUser systemUser, AuthUser authUser) {
        UserConnection userConnection = new UserConnection();
        userConnection.setUserName(systemUser.getUsername());
        userConnection.setProviderName(authUser.getSource().toString());
        userConnection.setProviderUserId(authUser.getUuid());
        userConnection.setProviderUserName(authUser.getUsername());
        userConnection.setImageUrl(authUser.getAvatar());
        userConnection.setNickName(authUser.getNickname());
        userConnection.setLocation(authUser.getLocation());
        this.userConnectionService.createUserConnection(userConnection);
    }

    private AuthCallback resolveAuthCallback(AuthCallback callback) {
        int stateLength = 3;
        String state = callback.getState();
        String[] strings = StringUtils.splitByWholeSeparatorPreserveAllTokens(state, StringConstant.DOUBLE_COLON);
        if (strings.length == stateLength) {
            callback.setState(strings[0] + StringConstant.DOUBLE_COLON + strings[1]);
        }
        return callback;
    }

    private AuthSource getAuthSource(String type) throws FebsException {
        if (StrUtil.isNotBlank(type)) {
            return AuthSource.valueOf(type.toUpperCase());
        } else {
            throw new FebsException(String.format("暂不支持%s第三方登录", type));
        }
    }

    private boolean isCurrentUser(String username) {
        String currentUsername = FebsUtil.getCurrentUsername();
        return StringUtils.equalsIgnoreCase(username, currentUsername);
    }

    private OAuth2AccessToken getOauth2AccessToken(SystemUser user) throws FebsException {
        final HttpServletRequest httpServletRequest = FebsUtil.getHttpServletRequest();
        httpServletRequest.setAttribute(ParamsConstant.LOGIN_TYPE, SocialConstant.SOCIAL_LOGIN);
        String socialLoginClientId = properties.getSocialLoginClientId();
        ClientDetails clientDetails = null;
        try {
            clientDetails = redisClientDetailsService.loadClientByClientId(socialLoginClientId);
        } catch (Exception e) {
            throw new FebsException("获取第三方登录可用的Client失败");
        }
        if (clientDetails == null) {
            throw new FebsException("未找到第三方登录可用的Client");
        }
        Map<String, String> requestParameters = new HashMap<>(5);
        requestParameters.put(ParamsConstant.GRANT_TYPE, GrantTypeConstant.PASSWORD);
        requestParameters.put(USERNAME, user.getUsername());
        requestParameters.put(PASSWORD, SocialConstant.SOCIAL_LOGIN_PASSWORD);

        String grantTypes = String.join(StringConstant.COMMA, clientDetails.getAuthorizedGrantTypes());
        TokenRequest tokenRequest = new TokenRequest(requestParameters, clientDetails.getClientId(), clientDetails.getScope(), grantTypes);
        return granter.grant(GrantTypeConstant.PASSWORD, tokenRequest);
    }
}
