package com.fc.focus.api.common.packageScan;

import java.util.List;

/**
 * Created by hwa on 2016/2/1.
 */
public class ScanClass {

    int index = -1;
    private Boolean passFlag = false;

    List<ScanFilter> filterList ;

    public ScanClass() { }
    public ScanClass(List<ScanFilter> filterList) {
        this.filterList = filterList;
    }


    public Boolean getPassFlag() {
        return passFlag;
    }

    public void setPassFlag(Boolean passFlag) {
        this.passFlag = passFlag;
    }

    //doFilter和invoke方法都可以实现递归调用，二者选一即可
    public void doFilter(List<ScanFilter> filters, Class<?> clazz) {
        index++;
        if (index >= filters.size()) {
            this.passFlag = true;
            return;
        }
        if (filters.get(index).pass(clazz)) {
            doFilter(filters, clazz);
        } else {
            this.passFlag = false;
            return;
        }
        return;
    }

    public void invoke(Class<?>clazz) {
        index++;
        if (index >= this.filterList.size()) {
            this.passFlag = true;
            return;
        }if (this.filterList.get(index).pass(clazz)) {
            this.filterList.get(index).getNext(this, clazz);
        }else{
            this.passFlag = false;
            return;
        }
        return;
    }

}
