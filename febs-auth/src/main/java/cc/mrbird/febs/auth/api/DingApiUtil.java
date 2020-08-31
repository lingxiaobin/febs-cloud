package cc.mrbird.febs.auth.api;


import cc.mrbird.febs.common.core.entity.constant.DingConstant;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;


/**
 * 获取access_token工具类
 */
@Slf4j
public class DingApiUtil {

    public static String getToken() throws RuntimeException {
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(DingConstant.URL_GET_TOKKEN);
            OapiGettokenRequest request = new OapiGettokenRequest();

            request.setAppkey(DingConstant.APP_KEY);
            request.setAppsecret(DingConstant.APP_SECRET);
            request.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(request);
            String accessToken = response.getAccessToken();
            return accessToken;
        } catch (ApiException e) {
            log.error("getAccessToken failed", e);
            throw new RuntimeException();
        }

    }

    public static void main(String[] args)throws ApiException{
        String accessToken = DingApiUtil.getToken();
        System.out.println(accessToken);
    }
}
