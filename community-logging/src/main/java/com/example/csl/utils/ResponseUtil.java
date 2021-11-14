package com.example.csl.utils;

import com.example.csl.result.Result;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ResponseUtil {
    public static Result Response(String uri) throws IOException {
        Result result = new Result();
        CloseableHttpResponse response = null;
        // 创建Httpclient对象,相当于打开了浏览器
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            // 创建HttpGet请求，相当于在浏览器输入地址
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求，相当于敲完地址后按下回车。获取响应
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                // 解析响应，获取数据
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
             result.setMessage(content);
            }
        }catch (Exception e){
            result.setStatus(0);
            result.setMessage(e.toString());
        }finally {
            if (response != null) {
                // 关闭资源
                response.close();
            }
            // 关闭浏览器
            httpclient.close();

        }
        return result;
    }
}
