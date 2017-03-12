package com.fc.focus.api.common.packageScan;

import com.fc.focus.api.common.Focus;
import com.fc.focus.api.common.XmlUtil;

/**
 * Created by hwa on 2016/2/1.
 */
public class AnnotationFilter implements ScanFilter {


    public void getNext(ScanClass scanClass, Class<?> clazz) {
        scanClass.invoke(clazz);
    }

    public Boolean pass(Class<?> clazz) {

        try {
            clazz = Class.forName(clazz.getName());
            Focus focus = (Focus) clazz.getAnnotation(Focus.class);
            if (focus != null && XmlUtil.getGroups().contains(focus.groups())) {
                return true;
            } else {
                return false;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }
}
