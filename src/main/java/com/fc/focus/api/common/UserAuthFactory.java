package com.fc.focus.api.common;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.*;

/**
 * Created by Administrator on 2016/3/10.
 */
public class UserAuthFactory {

    public UserAuthFactory() {
    }

    //xml获取用户
    private static Document getDocument() {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            String filePath = Class.class.getClass().getResource("/").getPath() + "loginUserCfg.xml";
            document = saxReader.read(new File(filePath));
            return document;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static Map<String,UserAuth> getEleList(List<Element> userinfo) {

        Map<String, UserAuth> userMap = new HashMap<String, UserAuth>();
        Iterator<Element> iterator = userinfo.iterator();

        while (iterator.hasNext()) {
            UserAuth user = new UserAuth();
            Element ele = iterator.next();
            user.setAppId(ele.element("AppId").getData().toString());
            user.setClientId(ele.element("clientId").getData().toString());
            user.setClientSecret(ele.element("clientSecret").getData().toString());
            user.setUrl(ele.element("url").getData().toString());

            userMap.put(ele.element("AppId").getData().toString(),user);
        }
        return userMap;
    }


    private static Map<String,Map<String,String>> getEleMap(List<Element> userinfo) {

        Map<String, Map<String,String>> userMap = new HashMap<String, Map<String,String>>();
        Iterator<Element> iterator = userinfo.iterator();
        while (iterator.hasNext()) {
            Element ele = iterator.next();
            List<Element> elelist = ele.elements();
            Iterator<Element> it = elelist.iterator();
            Map<String,String> map = new HashMap<String, String>();
            while(it.hasNext()){
                Element e = it.next();
                String key = e.getName();
                String value = e.getData().toString();

                map.put(key,value);
            }
            userMap.put(map.get("AppId"),map);
        }
        return userMap;
    }


    //getUser
    public static UserAuth getInstance(String appId){
        Element root = getDocument().getRootElement();
        List<Element> userinfo = root.elements("userinfo");
        Map<String, UserAuth> userList = getEleList(userinfo);
        UserAuth userAuth = userList.get(appId);
        return userAuth;
    }

    public static Map<String,String> getInstance1(String appId){
        Element root = getDocument().getRootElement();
        List<Element> userinfo = root.elements("userinfo");
        Map<String, Map<String, String>> eleMap = getEleMap(userinfo);
        Map map = eleMap.get(appId);
        return map;
    }

    //用户类
    public static class UserAuth {
        private String AppId;
        private String clientId;
        private String clientSecret;
        private String url;

        public UserAuth() {
        }

        public String getAppId() {
            return AppId;
        }

        public void setAppId(String appId) {
            AppId = appId;
        }

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
