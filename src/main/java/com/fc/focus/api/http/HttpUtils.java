package com.fc.focus.api.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fc.focus.api.common.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Eason on 16/1/19.
 */
public class HttpUtils {

    /**
     * 超时时间 25秒
     */
    public static final int CONNECTION_TIMEOUT = 25000;

    /**
     * 读取时间 25秒
     */
    public static final int READ_TIMEOUT = 25000;

    /**
     * 默认编码 UTF-8
     */
    public static final String CHARSET = "UTF-8";


    public static Response invoke(Request request, Class<? extends Response> response) throws IOException, IllegalAccessException, InstantiationException {

        HttpResult result ;
        if(request.getMethod().equals("POST")){
            result = post(request);
        }else {
            result = get(request);
        }
        Class<?> resp = null;
        try {
            resp = Class.forName(response.getName());
            Response res = (Response) resp.newInstance();
            res.setResult(result.getResponseBody());
            res.setHttpCode(result.getStatusCode());
            return res;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static HttpResult post(String url, String params, Map<String, String> header,HttpClient httpClient,String accessToken,String rowInfo) throws IOException {

        if (httpClient==null){
            httpClient = new HttpClient(new HttpClientParams(),new SimpleHttpConnectionManager(true));
        }

        PostMethod method = new PostMethod(url);
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, CHARSET);
        method.setRequestEntity(new StringRequestEntity(params, "text/xml", CHARSET));

        if(accessToken!=null){
            method.addRequestHeader("x-auth-token", accessToken);
        }
        for (String key : header.keySet()) {
            if (!StringUtils.isBlank(key)) {
                method.addRequestHeader(key, header.get(key));
            }
        }

     //   httpClient.getHostConfiguration().setProxy("127.0.0.1", 8888);
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECTION_TIMEOUT);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(READ_TIMEOUT);

        int statusCode = httpClient.executeMethod(method);
        byte[] responseBody = method.getResponseBody();
        String resStr = new String(responseBody, CHARSET);
        System.out.println("--rowInfo:"+rowInfo);
        System.out.println("------URL:"+ url);
        System.out.println("-response:" + resStr);
        System.out.println("-----code:"+statusCode);



        return new HttpResult(statusCode, responseBody);
    }

    public static HttpResult get(String url, String params, Map<String, String> header,HttpClient httpClient,String accessToken,String rowInfo) throws IOException {

        if (httpClient==null){
            httpClient = new HttpClient(new HttpClientParams(),new SimpleHttpConnectionManager(true));
        }

        GetMethod getMethod = null;
        if(params!=null){
            getMethod = new GetMethod(url + "?" + params);
        }else{
            getMethod = new GetMethod(url);
        }


        getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, CHARSET);
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECTION_TIMEOUT);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(READ_TIMEOUT);
        if(accessToken!=null){
            getMethod.addRequestHeader("x-auth-token", accessToken);
        }

        for (String key : header.keySet()) {
            if (!StringUtils.isBlank(key)) {
                getMethod.addRequestHeader(key,header.get(key));
            }
        }

        try {
            int statusCode = httpClient.executeMethod(getMethod);
            byte[] responseBody = getMethod.getResponseBody();

            String resStr = new String(responseBody, CHARSET);
            System.out.println("--rowInfo:"+ rowInfo);
            System.out.println("------url:"+ url+ "?" + params);
            System.out.println("-response:" + resStr);
            System.out.println("-----code:"+statusCode);

            return new HttpResult(statusCode, responseBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static HttpResult post(Request request) throws IOException {

        String url = request.getURL();
        String params = request.getParamJson();
        Map<String,String> headers = request.getHeader();

        HttpResult result = post(url, params, headers, null, null,null);

        return result;
    }

    public static HttpResult post( TestCaseExcel testCase ) throws IOException {
        return post(testCase,null,null);
    }


    public static HttpResult post( TestCaseExcel testCase,HttpClient httpClient,String accessToken ) throws IOException {

        String url = testCase.getUrl();
        String params = testCase.getParamJson();
        Map<String,String> headers = testCase.getHeader();
        HttpResult httpResult = post(url, params, headers, httpClient, accessToken,testCase.getType());

        return httpResult;
    }

    public static HttpResult get(Request request) throws IOException {

        String url = request.getURL();
        String params = request.getParamJson();
        if(params!=null){
            params = params.replaceAll("\\\\|\\{|\\}|\\\"|\\\n", "")
                    .replaceAll(":","=")
                    .replaceAll(",","&")
                    .replaceAll(" ","");
        }
        Map<String ,String> headers = request.getHeader();
        HttpResult httpResult = get(url, params, headers, null, null,null);
        return httpResult;

    }


    public static HttpResult get(TestCaseExcel testCase) throws IOException {
        return get(testCase,null,null);
    }

    public static HttpResult get(TestCaseExcel testCase,HttpClient httpClient,String accessToken) throws IOException {

        String url = testCase.getUrl();
        String params = testCase.getParamJson();
        if(params!=null){
            params = params.replaceAll("\\\\|\\{|\\}|\\\"|\\\n", "")
                    .replaceAll(":","=")
                    .replaceAll(",","&")
                    .replaceAll(" ","");
        }
        Map<String ,String> headers = testCase.getHeader();
        HttpResult httpResult = get(url, params, headers, httpClient, accessToken,testCase.getType());
        return httpResult;
    }


    public static HttpResult AuthGet(TestCaseExcel testCase) throws Exception {

        //先登录,登录用POST
        HttpClient httpClient = new HttpClient(new HttpClientParams(),new SimpleHttpConnectionManager(false));
        String accessToken = loginAuth(httpClient,testCase);

        //用同一个连接发送请求
        HttpResult httpResult = get(testCase, httpClient,accessToken);
        return httpResult;
    }

    public static HttpResult AuthPost(TestCaseExcel testCase) throws Exception {

        //先登录,登录用POST
        HttpClient httpClient = new HttpClient(new HttpClientParams(),new SimpleHttpConnectionManager(false));
        String accessToken = loginAuth(httpClient,testCase);

        //用同一个连接发送请求
        HttpResult httpResult = post(testCase, httpClient,accessToken);
        return httpResult;
    }


    public static  String loginAuth(HttpClient httpClient,TestCaseExcel testCase) throws Exception {

        Map<String, String> userMap = UserAuthFactory.getInstance1(testCase.getHeader().get("AppId"));

        String url = userMap.get("url");
        Map<String, String> header = new HashMap<String, String>();
        header.put("AppId",userMap.get("AppId"));

        if(userMap.containsKey("X-App-Id")){
            header.put("X-App-Id",userMap.get("X-App-Id"));
            header.put("X-Token",userMap.get("X-Token"));
        }


        Map<String, String>  map2 = userMap;
        map2.remove("url");
        map2.remove("AppId");
        String params = JSON.toJSONString(map2);

        PostMethod method = new PostMethod(url);
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, CHARSET);
        method.setRequestEntity(new StringRequestEntity(params, "text/xml", CHARSET));

        for (String key : header.keySet()) {
            if (!StringUtils.isBlank(key)) {
                method.addRequestHeader(key, header.get(key));
            }
        }

        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECTION_TIMEOUT);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(READ_TIMEOUT);

        int status = httpClient.executeMethod(method);
        byte[] bodyResult = method.getResponseBody();
        String accessToken = "";
        if (bodyResult!=null&&status==200){
            String resBody = new String(bodyResult);
            JSONObject json = JSONObject.parseObject(resBody);
            accessToken = json.get("accessToken").toString();
        }
        return accessToken;
    }



    public static class HttpResult {
        private int statusCode;
        private byte[] responseBody;

        public HttpResult(int statusCode, byte[] responseBody) {
            this.statusCode = statusCode;
            this.responseBody = responseBody;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public byte[] getResponseBody() {
            return responseBody;
        }

        public String getResponseBodyString() {
            return new String(responseBody);
        }

    }

}
