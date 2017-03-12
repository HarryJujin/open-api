package com.fc.focus.api.common;

import java.util.Map;

/**
 * Created by Eason on 15/12/5.
 */
public interface Request {

    /**
     * have one json per case.
     *
     * @return
     */
    String getParamJson();

    String getURL();

    String getEndpoint();

    Map<String, String> getHeader();

    String getMethod();

}
