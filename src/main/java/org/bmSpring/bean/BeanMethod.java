package org.bmSpring.bean;

import java.lang.reflect.Method;

public class BeanMethod {
    private final String className;
    private final String beanName;
    private final Method method;
    private final Class<?> returnType;

    public BeanMethod(String className, String methodName, Method method, Class<?> returnType) {
        this.className = className;
        this.beanName = methodName;
        this.method = method;
        this.returnType = returnType;
    }

    public String getClassName() {
        return className;
    }

    public String getBeanName() {
        return beanName;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getReturnType() {
        return returnType;
    }
}
