package com.fc.focus.api.common;

/**
 * Created by Eason on 15/12/5.
 */
public interface Response {

    void setResult(byte[] httpResponseBody);
    void setHttpCode(int httpCode);

    /**
     * http Code
     * @see
     * @return
     */
    int getHttpCode();


    boolean assert0();
}
