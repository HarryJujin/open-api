package com.fc.focus.test;

import com.fc.focus.api.common.ExcelUtil;
import com.fc.focus.api.common.TestCaseExcel;
import com.fc.focus.api.http.HttpUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Note : Excel测试接口
 * Author : hwa
 * Date : 2016/2/3
 */

public class ExcelTest {


    @DataProvider(name = "dataProvider")
    public Object[][] dataProvider() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return initTestCaseExcel();
    }

    @Test(dataProvider = "dataProvider")
    public void doTest(TestCaseExcel testCase) throws Exception {


        String assertType = testCase.getAssertType();

        if (testCase.getAuth().equals("Y")){
            if (assertType.equals("HC")) {
                if (testCase.getMethod().equals("GET")) {
                    Assert.assertEquals(HttpUtils.AuthGet(testCase).getStatusCode() + "", testCase.getExpected());
                } else {
                    Assert.assertEquals(HttpUtils.AuthPost(testCase).getStatusCode() + "", testCase.getExpected());
                }
            }else if (assertType.equals("RB")) {
                if (testCase.getMethod().equals("GET")) {
                    HttpUtils.HttpResult result = HttpUtils.AuthGet(testCase);
                    String responseBody = new String(result.getResponseBody());
                    Assert.assertEquals(responseBody, testCase.getExpected());
                } else {
                    HttpUtils.HttpResult result = HttpUtils.AuthPost(testCase);
                    String responseBody = new String(result.getResponseBody());
                    Assert.assertEquals(responseBody, testCase.getExpected());
                }
            } else if (assertType.equals("GV")) {
                if (testCase.getMethod().equals("GET")) {
                    HttpUtils.HttpResult result = HttpUtils.AuthGet(testCase);
                    String responseBody = new String(result.getResponseBody());
                    Object groovyResult = ExcelUtil.getGroovyResult(responseBody, testCase.getGroovyScript());
                    Assert.assertEquals(groovyResult.toString(), testCase.getExpected());
                } else {
                    HttpUtils.HttpResult result = HttpUtils.AuthPost(testCase);
                    String responseBody = new String(result.getResponseBody());
                    Object groovyResult = ExcelUtil.getGroovyResult(responseBody, testCase.getGroovyScript());
                    Assert.assertEquals(groovyResult.toString(), testCase.getExpected());
                }
            } else {
                Assert.fail("断言类型错误，请检查Excel");
            }
        }else{
            if (assertType.equals("HC")) {
                if (testCase.getMethod().equals("GET")) {
                    HttpUtils.HttpResult result = HttpUtils.get(testCase);
                    int statusCode = result.getStatusCode();
                    Assert.assertEquals(statusCode + "", testCase.getExpected());
                } else {
                    HttpUtils.HttpResult result = HttpUtils.post(testCase);
                    int statusCode = result.getStatusCode();
                    Assert.assertEquals(statusCode + "", testCase.getExpected());
                }
            } else if (assertType.equals("RB")) {
                if (testCase.getMethod().equals("GET")) {
                    HttpUtils.HttpResult result = HttpUtils.get(testCase);
                    String responseBody = new String(result.getResponseBody());
                    Assert.assertEquals(responseBody, testCase.getExpected());
                } else {
                    HttpUtils.HttpResult result = HttpUtils.post(testCase);
                    String responseBody = new String(result.getResponseBody());
                    Assert.assertEquals(responseBody, testCase.getExpected());
                }
            } else if (assertType.equals("GV")) {
                if (testCase.getMethod().equals("GET")) {
                    HttpUtils.HttpResult result = HttpUtils.get(testCase);
                    String responseBody = new String(result.getResponseBody());

                    Object groovyResult = ExcelUtil.getGroovyResult(responseBody, testCase.getGroovyScript());
                    Assert.assertEquals(groovyResult.toString(), testCase.getExpected());
                } else {
                    HttpUtils.HttpResult result = HttpUtils.post(testCase);
                    String responseBody = new String(result.getResponseBody());

                    Object groovyResult = ExcelUtil.getGroovyResult(responseBody, testCase.getGroovyScript());
                    Assert.assertEquals(groovyResult.toString(), testCase.getExpected());
                }
            } else {
                Assert.fail("断言类型错误，请检查Excel");
            }
        }


    }

    private Object[][] initTestCaseExcel() {

        try {

            List<XSSFSheet> list = ExcelUtil.getSheetList();
            System.out.println("===============开始读取数据=================");
            return ExcelUtil.getRowDataSum(list);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}
