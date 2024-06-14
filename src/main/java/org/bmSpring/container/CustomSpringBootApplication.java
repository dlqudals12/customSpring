package org.bmSpring.container;

import org.bmSpring.bean.BeanContext;
import org.bmSpring.scan.ResourceLoader;
import org.bmSpring.servlet.HttpServletContext;

import java.util.HashMap;

public class CustomSpringBootApplication {

    public static void run(Class<?> main, String[] args) {
        ResourceLoader.register(main);

        for (Class<?> c : ResourceLoader.getAllClasses()) {
            System.out.println("Load Resource: " + c.getName());
        }

        BeanContext beanContext = new BeanContext();

        HashMap<String, Object> beans = beanContext.getBeans();

        for (String body : beans.keySet()) {
            System.out.println("Load Beans: " + body);
        }

        HttpServletContext httpServletContext = new HttpServletContext(beanContext);

        httpServletContext.waitServer();
    }
}
