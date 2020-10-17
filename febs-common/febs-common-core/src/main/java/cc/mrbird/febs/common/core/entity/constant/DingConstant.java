package cc.mrbird.febs.common.core.entity.constant;

/**
 * 项目中的常量定义类
 */
public interface DingConstant {


    Long AGENT_ID =  335540530L;
    /**
     * 开发者后台->企业自建应用->选择您创建的E应用->查看->AppKey
     */
    String APP_KEY = "dingmtvcilrdyy6zjncf";
    /**
     * 开发者后台->企业自建应用->选择您创建的E应用->查看->AppSecret
     */
    String APP_SECRET = "LygHf93Idit02WfgZzzUx85SKXFa0dP33MkeOkdqP-3lSj0h-ubCzDPyqksAj5Vh";


    /**
     * 应用的SuiteKey，登录开发者后台，点击应用管理，进入应用详情可见  Corpid
     */
    String SUITE_KEY = "ding604ca88369c9094535c2f4657eb6378f";
    /**
     * 应用的SuiteSecret，登录开发者后台，点击应用管理，进入应用详情可见
     */
    String SUITE_SECRET = "";

    /**
     * 回调URL加解密用。应用的数据加密密钥，登录开发者后台，点击应用管理，进入应用详情可见
     */
    String ENCODING_AES_KEY = "xxxxxxxxlvdhntotr3x9qhlbytb18zyz5zxxxxxxxxx";

    /**
     * 回调URL签名用。应用的签名Token, 登录开发者后台，点击应用管理，进入应用详情可见
     */
    String TOKEN = "123456";


    /**
     * 钉钉网关gettoken地址
     */
    String URL_GET_TOKKEN = "https://oapi.dingtalk.com/gettoken";

    /**
     * 获取用户在企业内userId的接口URL
     */
    String URL_GET_USER_INFO = "https://oapi.dingtalk.com/user/getuserinfo";

    /**
     * 获取用户详细信息
     */
    String URL_USER_GET = "https://oapi.dingtalk.com/user/get";
}
