package com.fc.focus.api.common.packageScan;

/**
 * Created by hwa on 2016/2/1.
 */
public interface ScanFilter {


    public void getNext(ScanClass scanClass, Class<?> clazz);

    public Boolean pass(Class<?> clazz);


}
