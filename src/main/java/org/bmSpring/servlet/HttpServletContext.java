package org.bmSpring.servlet;

import org.bmSpring.bean.BeanCreate;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class HttpServletContext {

    private final BeanCreate beanCreate;

    public HttpServletContext(BeanCreate beanCreate) {
        this.beanCreate = beanCreate;
    }

    public void waitServer() {
        HttpServletFactory httpServletFactory = new HttpServletFactory();

        for (Map.Entry<String, String> mapping : httpServletFactory.getMappings().entrySet()) {
            System.out.println("KEY: " + mapping.getKey() + " VALUE: " + mapping.getValue());
        }

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
                String requestLine = in.readLine();
                System.out.println("Request Line: " + requestLine);

                // 헤더 읽기
                String headerLine;
                while ((headerLine = in.readLine()) != null && !headerLine.isEmpty()) {
                    System.out.println("Header Line: " + headerLine);
                }

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
            e.printStackTrace();
        }
    }

}
