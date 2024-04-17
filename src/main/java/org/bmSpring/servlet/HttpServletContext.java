package org.bmSpring.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bmSpring.bean.BeanFactory;
import org.bmSpring.exception.ServletException;
import org.bmSpring.exception.enums.ExceptionCode;
import org.bmSpring.servlet.enums.MediaType;
import org.bmSpring.servlet.factory.HttpServletFactory;
import org.bmSpring.servlet.factory.HttpServletFactoryImpl;
import org.bmSpring.servlet.model.HttpMethod;
import org.bmSpring.servlet.runner.HttpServletRunner;
import org.bmSpring.servlet.runner.HttpServletRunnerImpl;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class HttpServletContext {

    private final BeanFactory beanFactory;
    private final HttpServletFactory httpServletFactory;
    private final HttpServletRunner runner;

    public HttpServletContext(BeanFactory beanFactory) {
        ObjectMapper objectMapper = new ObjectMapper();

        this.beanFactory = beanFactory;
        this.httpServletFactory = new HttpServletFactoryImpl();
        this.runner = new HttpServletRunnerImpl(objectMapper);

        for (Map.Entry<String, HttpMethod> mapping : httpServletFactory.getMappings().entrySet()) {
            System.out.printf("HTTP Type: %s >> HTTP Path: %s \n", mapping.getValue().getHttpType(), mapping.getKey());
        }
    }

    @SuppressWarnings("all")
    public void waitServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(19050);

            while (true) {
                System.out.println("Connection wait");
                Socket socket = serverSocket.accept();

                InetSocketAddress remoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();

                System.out.println("Connected: " + remoteSocketAddress.getHostName());

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

                new Thread(() -> {
                    try {
                        CreateServletModel httpModel = new CreateServletModel(in, out, httpServletFactory);

                        runner.runServlet(httpModel.getHttpServletRequestInfo(), httpModel.getHttpServletResponseInfo(),
                                beanFactory.getBean(httpModel.getHttpServletRequestInfo().getControllerName()));
                    } catch (ServletException e) {
                        ExceptionCode code = e.getExceptionCode();
                        out.println(String.format("HTTP/1.1 %d %s", code.getCode(), code.getMsg()));
                        out.println("Content-Type: " + MediaType.APPLICATION_JSON_VALUE.getContentName());
                        out.println();
                        out.println(e.getMessage());
                        out.close();
                    }
                }).start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
