package com.fc.focus.test;

import com.fc.focus.api.common.HttpRemoteFactory;
import com.fc.focus.api.common.Request;
import com.fc.focus.api.common.Response;
import com.fc.focus.api.common.packageScan.ClassScanUtil;
import com.fc.focus.api.http.HttpUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by Eason on 16/1/19.
 */
public class MainTest {

    @DataProvider(name = "dataProvider")
    public Object[][] dataProvider() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return initTestCase();
    }

    @Test(dataProvider = "dataProvider")
    public void doTest(Request request, Class<? extends Response> responseClass) throws Exception {

        if (request != null && responseClass != null) {
            Response response1 = HttpUtils.invoke(request, responseClass);
            if (response1 == null) {
                Assert.fail("Response实例化失败，请检查测试用例");
            } else {
                Assert.assertEquals(response1.assert0(), true);
            }
        } else {
            Assert.fail("测试用例数据异常，请检查");
        }
    }

    private Object[][] initTestCase() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        List<Class<?>> scanList = ClassScanUtil.scan();
        int arraySize = getArraySize(scanList);
        Object[][] testCase = new Object[arraySize][2];
        int j = 0;
        for (int i = 0; i < scanList.size(); i++) {
            Class<?> testClass = Class.forName(scanList.get(i).getName());
            HttpRemoteFactory instance = (HttpRemoteFactory) testClass.newInstance();
            Map<Request, Class<? extends Response>> map = instance.make();

            if (map != null) {
                for (Request req : map.keySet()) {
                    testCase[j][0] = req;
                    testCase[j][1] = map.get(req);
                    j++;
                }
            } else {
                System.out.println("Map不能为空，该测试数据已被忽略，请检查测试用例");
            }

        }
        return testCase;
    }

    private int getArraySize(List<Class<?>> scanList) {
        int count = 0;
        try {
            for (int i = 0; i < scanList.size(); i++) {
                Class<?> testClass = Class.forName(scanList.get(i).getName());
                HttpRemoteFactory instance = (HttpRemoteFactory) testClass.newInstance();
                Map<Request, Class<? extends Response>> map = instance.make();
                if (map != null) {
                    count += map.size();
                }
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

}
