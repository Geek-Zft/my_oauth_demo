package com.zft.oauth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zft.oauth.constants.OauthConstants.ACCESS_TOKEN_URL;
import static com.zft.oauth.constants.OauthConstants.CLIENT_ID;
import static com.zft.oauth.constants.OauthConstants.CLIENT_SECRET;
import static com.zft.oauth.constants.OauthConstants.USER_URL;

/**
 * Description: Oauth controller
 * @author  fengtan.zhang
 * @date    2020/12/11 11:32
 */
@Controller
public class OauthController {

    /**
     * Description: github授权回调接口
     * @param code 授权码
     * @author  fengtan.zhang
     * @date    2020/12/11 11:39
     */
    @RequestMapping(value = "/oauth/redirect", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> oauthCallback(@RequestParam String code) {

        Map<String, Object> res = new HashMap<>();

        // 从github获取token
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(ACCESS_TOKEN_URL);

        // 组织参数
        List<NameValuePair> urlParams = new ArrayList<>();
        urlParams.add(new BasicNameValuePair("client_id", CLIENT_ID));
        urlParams.add(new BasicNameValuePair("client_secret", CLIENT_SECRET));
        urlParams.add(new BasicNameValuePair("code", code));
        try {
            post.setEntity(new UrlEncodedFormEntity(urlParams));
            // 关于Accept的说明 https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html
            // github提供了三种返回格式，具体使用哪种取决于Accept header, 这里指定json，另外两种可参考github官方文档
            // https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/#web-application-flow
            post.addHeader("Accept", "application/json");
            CloseableHttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            String resBody = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            // 解析response，拿到access_token
            JSONObject jsonObject = JSON.parseObject(resBody);
            String access_token = (String) jsonObject.get("access_token");
            // res.put("access_token", access_token);

            // 获取授权用户信息
            return getUser(access_token);

            // res.put("responseStatus", response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    private Map<String, Object> getUser(String access_token) {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(USER_URL);

        httpGet.addHeader("Authorization", "Bearer " + access_token);
        httpGet.addHeader("Accept", "application/json");

        try {
            CloseableHttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String resBody = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            return JSONObject.parseObject(resBody);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }


}
