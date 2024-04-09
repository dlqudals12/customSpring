package org.bmSpring.container;

import org.bmSpring.bean.BeanFactory;
import org.bmSpring.scan.ResourceLoader;
import org.bmSpring.servlet.HttpServletContext;

import java.util.HashMap;

public class CustomApplication {

    public static void run(Class<?> main, String[] args) {
        ResourceLoader.register(main);

        for (Class<?> c : ResourceLoader.getAllClasses()) {
            System.out.println("Load Resource: " + c.getName());
        }

        BeanFactory beanFactory = new BeanFactory();

        HashMap<String, Object> beans = beanFactory.getBeans();

        for (String body : beans.keySet()) {
            System.out.println("Load Beans: " + body);
        }

        HttpServletContext httpServletContext = new HttpServletContext(beanFactory);

        httpServletContext.waitServer();
    }
}
