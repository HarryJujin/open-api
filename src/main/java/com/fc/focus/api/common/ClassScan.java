package com.fc.focus.api.common;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by Eason on 16/1/26.
 */
public class ClassScan {

    /**
     *
     * @param pName package name
     * @param filter
     * @return
     */
    public static List<Class> scan(String pName, ScanFilter filter) {
        String p_path = StringUtils.replace(pName, ".", "/");

        return null;
    }

    public static interface ScanFilter {

        boolean pass(Class c);

        ScanFilter getNext();
    }
}
