package org.bmSpring.container;

import org.bmSpring.bean.BeanCreater;
import org.bmSpring.scan.ResourceLoader;
import org.bmSpring.servlet.HttpServletContext;

import java.util.HashMap;

public class CustomSpringBootApplication {

    public static void run(Class<?> main, String[] args) {
        ResourceLoader.register(main);

        for (Class<?> c : ResourceLoader.getAllClasses()) {
            System.out.println("Load Resource: " + c.getName());
        }

        BeanCreater beanCreater = new BeanCreater();

        HashMap<String, Object> beans = beanCreater.getBeans();

        for (String body : beans.keySet()) {
            System.out.println("Load Beans: " + body);
        }

        HttpServletContext httpServletContext = new HttpServletContext(beanCreater);

        httpServletContext.waitServer();
    }
}
