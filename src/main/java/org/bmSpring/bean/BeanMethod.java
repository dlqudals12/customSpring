package org.bmSpring.bean;

import lombok.Getter;

import java.lang.reflect.Method;

@Getter
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

}
