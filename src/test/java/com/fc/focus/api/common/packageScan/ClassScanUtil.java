package com.fc.focus.api.common.packageScan;

import com.fc.focus.api.common.XmlUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Eason on 16/1/26.
 */
public class ClassScanUtil {


    public static List<Class> scan(String pName, ScanFilter filter) {
        String p_path = StringUtils.replace(pName, ".", "/");
        return null;
    }

    public static void scan(String packageName, List<Class<?>> list) throws Exception {
        String path = Class.class.getClass().getResource("/").getPath() + StringUtils.replace(packageName, ".", "/");
        File dir = new File(path);
        File[] files = dir.listFiles();
        Class<?> clazz = null;
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    String childName = packageName + "." + f.getName();
                    scan(childName, list);

                } else {
                    clazz = Class.forName(packageName + "." + f.getName().split("\\.")[0]);
                    //此处加过滤
                    List<ScanFilter> filterList = XmlUtil.getFilters();
                    ScanClass scanClass = new ScanClass(filterList);
                    scanClass.invoke(clazz);
                    if (scanClass.getPassFlag() && !list.contains(clazz)) {
                        list.add(clazz);
                    }
                }
            }
        }
    }

    public static List<Class<?>> scan() {
        final List<String> packagesList = XmlUtil.getPackages();
        Iterator<String> ite = packagesList.iterator();
        List<Class<?>> classList = new ArrayList<Class<?>>();

        while (ite.hasNext()) {
            try {
                scan(ite.next().toString(), classList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return classList;
    }

    public static void main(String[] args) {
        for (Class<?> clazz : scan()) {
            System.out.println(clazz);
        }

    }


}
