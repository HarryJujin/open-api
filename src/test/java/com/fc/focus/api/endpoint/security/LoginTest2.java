package com.fc.focus.api.endpoint.security;

import com.alibaba.fastjson.JSON;
import com.fc.focus.api.common.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eason on 16/1/20.
 */

@Focus(groups = "endpoint")
public class LoginTest2 implements HttpRemoteFactory {

    private static final String URL = TestConstance.HOST + "/aiaf/sgw/v1/security/login";

   /* @DataProvider
    public Object[][] date() {

        return new Object[][] {
                { "15000000000", "12345678a", false},
                { "15000000001", "12345678a", false},
        };



    }

    @Test(dataProvider = "date", groups = "security")
    public void login(String username, String password, boolean assert0) {

        Map map = new HashMap();


        map.put("clientId", username);
        map.put("clientSecret", password);

        String json = JSON.toJSONString(map);
        Map<String, String> header = new HashMap<String, String>();
        header.put("AppId", "079b8de8-0894-411b-aa48-853bb48f069d");

        try {
            HttpUtils.HttpResult httpResult = HttpUtils.post(URL, json, header);
            System.out.println(httpResult.getResponseBodyString());
            if (!assert0) {
                Assert.assertEquals(httpResult.getStatusCode(), 400);
            } else {
                Assert.assertEquals(httpResult.getStatusCode(), 200);
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }*/


    public Map<Request, Class<? extends Response>> make() {

        Map map = new HashMap();
        map.put("clientId", "15000000000");
        map.put("clientSecret", "12345678a");
        String json = JSON.toJSONString(map);
        Map<String, String> header = new HashMap<String, String>();
        header.put("AppId", "079b8de8-0894-411b-aa48-853bb48f069d");


        HashMap map1 = new HashMap();
        map1.put("clientId", "15000000001");
        map1.put("clientSecret", "12345678a");
        String json2 = JSON.toJSONString(map1);
        //新建request
        LoginReq login = new LoginReq();
        login.setHeader(header);
        login.setParamJson(json);
        login.setURL(URL);

        LoginReq login2 = new LoginReq();
        login2.setHeader(header);
        login2.setParamJson(json2);
        login2.setURL(URL);

        //新建Response
        LoginRes loginRes = new LoginRes();


        HashMap<Request, Class<? extends Response>> resultMap = new HashMap<Request, Class<? extends Response>>();
        resultMap.put(login,LoginRes.class);
        resultMap.put(login2,LoginRes.class);

        return resultMap;
    }

    private class LoginReq implements Request{
        private  String URL ;
        private  Map<String,String> header;
        private  String paramJson ;

        public String getURL() {
            return URL;
        }

        public String getEndpoint() {
            return null;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }

        public Map<String, String> getHeader() {
            return header;
        }

        public String getMethod() {
            return "POST";
        }

        public void setHeader(Map<String, String> header) {
            this.header = header;
        }

        public String getParamJson() {
            return paramJson;
        }

        public void setParamJson(String paramJson) {
            this.paramJson = paramJson;
        }
    }

    public static class LoginRes implements Response{
        private byte[] httpResponseBody;
        private int httpCode;

        public byte[] getHttpResponseBody() {
            return httpResponseBody;
        }

        public void setHttpResponseBody(byte[] httpResponseBody) {
            this.httpResponseBody = httpResponseBody;
        }

        public int getHttpCode() {
            return httpCode;
        }

        public boolean assert0() {
            return true;
        }

        public void setResult(byte[] httpResponseBody) {
            this.httpResponseBody = httpResponseBody;
        }

        public void setHttpCode(int httpCode) {
            this.httpCode = httpCode;
        }

    }


}