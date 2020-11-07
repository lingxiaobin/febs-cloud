package cc.mrbird.febs.common.security.starter.configure;

import cc.mrbird.febs.common.core.entity.constant.FebsConstant;
import cc.mrbird.febs.common.core.entity.system.SystemUser;
import cc.mrbird.febs.common.core.utils.FebsUtil;
import cc.mrbird.febs.common.security.starter.handler.FebsAccessDeniedHandler;
import cc.mrbird.febs.common.security.starter.handler.FebsAuthExceptionEntryPoint;
import cc.mrbird.febs.common.security.starter.properties.FebsCloudSecurityProperties;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.net.URI;

/**
 * @author MrBird
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(FebsCloudSecurityProperties.class)
@ConditionalOnProperty(value = "febs.cloud.security.enable", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class FebsCloudSecurityAutoConfigure {


    @Bean
    @ConditionalOnMissingBean(name = "accessDeniedHandler")
    public FebsAccessDeniedHandler accessDeniedHandler() {
        return new FebsAccessDeniedHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name = "authenticationEntryPoint")
    public FebsAuthExceptionEntryPoint authenticationEntryPoint() {
        return new FebsAuthExceptionEntryPoint();
    }

    @Bean
    @ConditionalOnMissingBean(value = PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FebsCloudSecurityInteceptorConfigure febsCloudSecurityInteceptorConfigure() {
        return new FebsCloudSecurityInteceptorConfigure();
    }

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor() {
        return requestTemplate -> {
//            Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
//            if (details instanceof OAuth2AuthenticationDetails) {
//                String authorizationToken = ((OAuth2AuthenticationDetails) details).getTokenValue();
//                requestTemplate.header(HttpHeaders.AUTHORIZATION, "bearer " + authorizationToken);
//            }
            String gatewayToken = new String(Base64Utils.encode(FebsConstant.GATEWAY_TOKEN_VALUE.getBytes()));
            requestTemplate.header(FebsConstant.GATEWAY_TOKEN_HEADER, gatewayToken);
            String authorizationToken = FebsUtil.getCurrentTokenValue();
            if (StringUtils.isNotBlank(authorizationToken)) {
                requestTemplate.header(HttpHeaders.AUTHORIZATION, FebsConstant.OAUTH2_TOKEN_TYPE + authorizationToken);
            }
            else {
                try {
                    authorizationToken= getToken();
                    requestTemplate.header(HttpHeaders.AUTHORIZATION, FebsConstant.OAUTH2_TOKEN_TYPE + authorizationToken);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private static String getToken() throws Exception {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 定义请求的参数
        URI uri = new URIBuilder("http://localhost:8301/auth/oauth/token")
                .setParameter("grant_type", "password")
                .setParameter("username", "mrbird")
                .setParameter("password", "1234qwer")
                .setParameter("key", "1234qwer")
                .setParameter("code", "666666")
                .build();
        // 创建http GET请求
        HttpPost httpGet = new HttpPost(uri);
        httpGet.addHeader("Authorization", "Basic ZmViczoxMjM0NTY=");
        //response 对象
        CloseableHttpResponse response = null;
        try {
            // 执行http get请求
            response = httpclient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
//            System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
            JSONObject object = JSON.parseObject(EntityUtils.toString(responseEntity));
            return object.getString("access_token");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
        return "";
    }
}
