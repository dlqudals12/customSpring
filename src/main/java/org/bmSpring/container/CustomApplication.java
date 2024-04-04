package org.bmSpring.container;

import org.bmSpring.bean.BeanCreate;
import org.bmSpring.scan.ResourceLoader;
import org.bmSpring.servlet.HttpServletContext;

import java.util.HashMap;

public class CustomApplication {

    public static void run(Class<?> main, String[] args) {
        ResourceLoader.register(main);

        for (Class<?> c : ResourceLoader.getAllClasses()) {
            System.out.println("Load Resource: " + c.getName());
        }

        BeanCreate beanCreate = new BeanCreate();

        HashMap<String, Object> beans = beanCreate.getBeans();

        for (String body : beans.keySet()) {
            System.out.println("Load Beans: " + body);
        }

        HttpServletContext httpServletContext = new HttpServletContext(beanCreate);

        httpServletContext.waitServer();
    }
}
