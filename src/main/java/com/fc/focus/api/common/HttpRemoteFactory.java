package com.fc.focus.api.common;

import java.util.Map;

/**
 * Created by Eason on 16/1/26.
 */
public interface HttpRemoteFactory {

    Map<Request, Class<? extends Response>> make();
   // Map<Request, Response> make();
}
