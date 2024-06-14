package org.bmSpring.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bmSpring.bean.BeanContext;
import org.bmSpring.exception.ServletException;
import org.bmSpring.exception.enums.ExceptionCode;
import org.bmSpring.servlet.enums.MediaType;
import org.bmSpring.servlet.factory.HttpServletFactory;
import org.bmSpring.servlet.factory.HttpServletFactoryImpl;
import org.bmSpring.servlet.model.HttpMethod;
import org.bmSpring.servlet.model.ThreadPool;
import org.bmSpring.servlet.runner.HttpServletRunner;
import org.bmSpring.servlet.runner.HttpServletRunnerImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class HttpServletContext {

    private final BeanContext beanContext;
    private final HttpServletFactory httpServletFactory;
    private final HttpServletRunner runner;
    private final ThreadPool threadPool;

    public HttpServletContext(BeanContext beanContext) {
        ObjectMapper objectMapper = new ObjectMapper();

        this.beanContext = beanContext;
        this.httpServletFactory = new HttpServletFactoryImpl();
        this.runner = new HttpServletRunnerImpl(objectMapper);
        this.threadPool = new ThreadPool(500);

        for (Map.Entry<String, HttpMethod> mapping : httpServletFactory.getMappings().entrySet()) {
            System.out.printf("HTTP Type: %s >> HTTP Path: %s \n", mapping.getValue().getHttpType(), mapping.getKey());
        }
    }

    @SuppressWarnings("all")
    public void waitServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(19050);

            while (true) {
                Socket socket = serverSocket.accept();

                Thread runnable = new Thread(() -> {
                    boolean isIOException = false;

                    BufferedReader in = null;
                    PrintWriter out = null;

                    try {
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    } catch (IOException e) {
                        isIOException = true;
                    }

                    if (isIOException) throw new ServletException(ExceptionCode.NOT_FOUND);
                    
                    try {
                        CreateServletModel httpModel = new CreateServletModel(in, out, httpServletFactory);

                        runner.runServlet(httpModel.getHttpServletRequestInfo(), httpModel.getHttpServletResponseInfo(),
                                beanContext.getBean(httpModel.getHttpServletRequestInfo().getControllerName()));
                    } catch (ServletException e) {
                        ExceptionCode code = e.getExceptionCode();
                        out.println(String.format("HTTP/1.1 %d %s", code.getCode(), code.getMsg()));
                        out.println("Content-Type: " + MediaType.APPLICATION_JSON_VALUE.getContentName());
                        out.println();
                        out.println(e.getMessage());
                        out.close();
                    }
                });

                threadPool.addThread(runnable);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
