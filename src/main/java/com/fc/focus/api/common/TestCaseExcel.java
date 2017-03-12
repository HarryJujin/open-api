package com.fc.focus.api.common;

import java.util.Map;

/**
 * Created by Administrator on 2016/2/2.
 */
public class TestCaseExcel {

    private String type;
    private String url;
    private String paramJson;
    private Map<String, String> header;
    private String method;
    private String auth;
    private String groovyScript;
    private String assertType;
    private String expected;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParamJson() {
        return paramJson;
    }

    public void setParamJson(String paramJson) {
        this.paramJson = paramJson;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getGroovyScript() {
        return groovyScript;
    }

    public void setGroovyScript(String groovyScript) {
        this.groovyScript = groovyScript;
    }

    public String getAssertType() {
        return assertType;
    }

    public void setAssertType(String assertType) {
        this.assertType = assertType;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    @Override
    public String toString() {
        return "TestCaseExcel{" +
                "type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", paramJson='" + paramJson + '\'' +
                ", header=" + header +
                ", method='" + method + '\'' +
                ", auth='" + auth + '\'' +
                ", groovyScript='" + groovyScript + '\'' +
                ", assertType='" + assertType + '\'' +
                ", expected='" + expected + '\'' +
                '}';
    }
}
