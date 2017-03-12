package com.fc.focus.api.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by hwa on 2016/1/29.
 */
public class PropertiesUtil {

    public static Properties getProperties()  {
        String path = Class.class.getResource("/").getPath() + "testExcelCfg.properties";

        File file = new File(path);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            Properties prop = new Properties();
            prop.load(inputStream);
            return prop;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
