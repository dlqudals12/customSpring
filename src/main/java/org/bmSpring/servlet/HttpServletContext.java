package org.bmSpring.servlet;

import org.bmSpring.bean.BeanFactory;
import org.bmSpring.servlet.enums.HttpType;
import org.bmSpring.servlet.model.HttpServletRequest;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpServletContext {

    private final BeanFactory beanFactory;
    private final HttpServletFactory httpServletFactory;

    public HttpServletContext(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        this.httpServletFactory = new HttpServletFactory();

        for (Map.Entry<String, HttpMethod> mapping : httpServletFactory.getMappings().entrySet()) {
            System.out.printf("HTTP Type: %s >> HTTP Path: %s \n", mapping.getValue().getHttpType(), mapping.getKey());
        }
    }

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

                // 요청 라인 읽기
                String requestInfo = in.readLine();
                String[] split = requestInfo.split(" ");

                String key = HttpType.valueOf(split[0]).getValue() + split[1];

                HttpMethod httpMethod = httpServletFactory.getHttpMethod(key);

                if (httpMethod == null) throw new NullPointerException();

                HashMap<String, Object> headerMap = new HashMap<>();

                String headerLine;
                while ((headerLine = in.readLine()) != null && !headerLine.isEmpty()) {
                    System.out.println(headerLine);
                    String[] mapArray = headerLine.split(": ");
                    headerMap.put(mapArray[0], mapArray[1]);
                }

                HttpServletRequest httpServletRequest = new HttpServletRequest(httpMethod);
                httpServletRequest.newRequest(headerMap);

                // 요청 본문 읽기
                StringBuilder body = new StringBuilder();
                while (in.ready()) {
                    body.append((char) in.read());
                }
                System.out.println("Request Body: " + body);


                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: text/plain");
                out.println();
                out.println("Hello, client!");
                out.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
