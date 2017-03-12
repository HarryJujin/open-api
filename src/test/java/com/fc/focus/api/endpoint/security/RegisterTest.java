package com.fc.focus.api.endpoint.security;

import com.alibaba.fastjson.JSON;
import com.fc.focus.api.common.Focus;
import com.fc.focus.api.common.HttpRemoteFactory;
import com.fc.focus.api.common.Request;
import com.fc.focus.api.common.Response;
import com.fc.focus.api.http.HttpUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eason on 16/1/20.
 */
@Focus(groups = "endpoint")
public class RegisterTest implements HttpRemoteFactory{

    private static final String URL = "";

    @DataProvider
    public Object[][] date() {
        return new Object[][] {
                { "15000000000", "12345678a", "abc@163.com", true},
                { "15000000000", "12345", "abc@163.com", false},
                { "123123123123", "12345678a", "abc@163.com", false},
                { "15000000000", "12345678a", "163.com", false},
        };
    }

    @DataProvider
    public Object[][] date2() {

        Register re1 = new Register("123123", "123123");
        Register re2 = new Register("123123", "123123");

        return new Object[][] {
                { re1, true},
                { re2, false},
        };
    }

    @Test(dataProvider = "date", groups = "security")
    public void register(String username, String password, boolean assert0) {

        Map map = new HashMap();
        map.put("username", username);
        map.put("password", password);

        String json = JSON.toJSONString(map);
//        try {
//            HttpUtils.HttpResult httpResult = HttpUtils.post(URL, json, new HashMap<String, String>());
//
//            if (!assert0) {
//                Assert.assertEquals(httpResult.getStatusCode(), 400);
//            } else {
//                Assert.assertEquals(httpResult.getStatusCode(), 200);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException();
//        }
    }

    @Test(dataProvider = "date2", groups = "security")
    public void register0(Register register, boolean assert0) {

        Map map = new HashMap();
        map.put("username", register.getUsername());
        map.put("password", register.getPassword());

        String json = JSON.toJSONString(map);
//        try {
//            HttpUtils.HttpResult httpResult = HttpUtils.post(URL, json, new HashMap<String, String>());
//
//            if (!assert0) {
//                Assert.assertEquals(httpResult.getStatusCode(), 400);
//            } else {
//                Assert.assertEquals(httpResult.getStatusCode(), 200);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException();
//        }
    }

    public Map<Request, Class<? extends Response>> make() {
        return null;
    }


    private class Register implements Request{
        private String username;
        private String password;
        private final Map<String, String> header ;

        public Register(String username, String password) {
            this.username = username;
            this.password = password;
            this.header = new HashMap<String, String>();
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getParamJson() {
            return null;
        }

        public String getURL() {
            return null;
        }

        public String getEndpoint() {
            return null;
        }

        public Map<String, String> getHeader() {
            return header;
        }

        public String getMethod() {
            return "POST";
        }

        public void setHeader(String key, String value) {
            this.header.put(key, value);
        }
    }
}
