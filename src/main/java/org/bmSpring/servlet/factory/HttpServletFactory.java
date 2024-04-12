package org.bmSpring.servlet.factory;

import org.bmSpring.servlet.model.HttpMethod;

import java.util.HashMap;

public interface HttpServletFactory {
    HashMap<String, HttpMethod> getMappings();

    HttpMethod getHttpMethod(String key);
}
