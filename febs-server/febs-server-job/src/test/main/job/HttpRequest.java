package job;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.net.URI;

@Slf4j
public class HttpRequest {

//    @Test
//    public void test2() {
//        System.out.println("惠东晚早下按打卡算".indexOf("打卡算"));
//        DecimalFormat decimalFormat = new DecimalFormat("#.00");
//        decimalFormat.setRoundingMode(RoundingMode.DOWN);
//        String format = decimalFormat.format(3.1415926);
//        System.out.println(format);
//        String str = "04-25 07:41:35 - 上班早到18.42".substring(0, 14);
//        System.out.println(str);
//    }

    //发送 GET 请求，第一个参数为url，第二个参数为请求参数
    //在实际开发中，main方法可以删除；直接在所需的位置调用HttpRequest.sendGet()或者HttpRequest.sendPost()方法


    public static void main(String[] args) throws Exception {
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
            System.out.println("响应内容长度为:" + responseEntity.getContentLength());
            System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
            JSON.parseObject( EntityUtils.toString(responseEntity));
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
    }


}
