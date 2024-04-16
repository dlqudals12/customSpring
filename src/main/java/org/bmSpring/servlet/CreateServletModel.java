package org.bmSpring.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.Getter;
import org.bmSpring.exception.ServletRequestNotFoundException;
import org.bmSpring.servlet.enums.HttpType;
import org.bmSpring.servlet.enums.MediaType;
import org.bmSpring.servlet.factory.HttpServletFactory;
import org.bmSpring.servlet.model.HttpMethod;
import org.bmSpring.servlet.model.HttpServletRequestInfo;
import org.bmSpring.servlet.model.HttpServletResponseInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class CreateServletModel {

    private HttpServletRequestInfo httpServletRequestInfo;
    private HttpServletResponseInfo httpServletResponseInfo;

    public CreateServletModel(BufferedReader in, PrintWriter out, HttpServletFactory httpServletFactory) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String requestInfo = in.readLine();
            String[] split = requestInfo.split(" ");
            String uri = split[1];
            String queryParams = "";

            if (uri.contains("?")) {
                String[] splitUri = uri.split("\\?");
                uri = splitUri[0];
                queryParams = splitUri[1];
            }

            String key = HttpType.valueOf(split[0]).getValue() + uri;

            HttpMethod httpMethod = httpServletFactory.getHttpMethod(key);

            if (httpMethod == null) throw new ServletRequestNotFoundException();

            HttpServletRequestInfo httpServletRequestInfo = new HttpServletRequestInfo(httpMethod);

            HashMap<String, Object> headers = headers(in);

            Object responseBody = responseBody(in, httpMethod, objectMapper);
            List<Object> requestParams = requestParams(queryParams, httpMethod);

            httpServletRequestInfo.newRequest(headers);
            httpServletRequestInfo.setParameters(responseBody, requestParams);

            this.httpServletRequestInfo = httpServletRequestInfo;
            this.httpServletResponseInfo = new HttpServletResponseInfo(out, httpMethod, headers, httpServletRequestInfo.getCookies());
        } catch (Exception e) {
            out.println("HTTP/1.1 500 SERVER_ERROR");
            out.println("Content-Type: " + MediaType.APPLICATION_JSON_VALUE.getContentName());
            out.println();
            out.println(e.getMessage());
            out.close();
        }
    }

    public HashMap<String, Object> headers(BufferedReader in) throws IOException {
        HashMap<String, Object> headerMap = new HashMap<>();

        String headerLine;
        while ((headerLine = in.readLine()) != null && !headerLine.isEmpty()) {
            System.out.println(headerLine);
            String[] mapArray = headerLine.split(": ");
            headerMap.put(mapArray[0], mapArray[1]);
        }
        return headerMap;
    }

    public List<Object> requestParams(String queryParams, HttpMethod httpMethod) {
        HashMap<String, Object> requestParam = new HashMap<>();

        String[] splitParameters = queryParams.split("&");

        for (String splitParameter : splitParameters) {
            String[] splitParams = splitParameter.split("=");

            requestParam.put(splitParams[0], splitParams[1]);
        }

        List<Object> requestValues = new ArrayList<>();

        for (Map.Entry<String, Class<?>> requestEntry : httpMethod.getRequestParam().entrySet()) {
            String requestKey = requestEntry.getKey();
            boolean required = false;

            if (requestKey.contains("required/")) {
                required = true;
                requestKey = requestKey.replaceAll("required/", "");
            }

            Object value = requestParam.get(requestKey);

            if (required && value == null) {
                throw new InvalidRequestStateException();
            } else if (value != null) {
                requestValues.add(requestEntry.getValue().cast(value));
            }
        }
        return requestValues;
    }

    public Object responseBody(BufferedReader in, HttpMethod httpMethod, ObjectMapper objectMapper) throws IOException {
        StringBuilder body = new StringBuilder();
        while (in.ready()) {
            body.append((char) in.read());
        }

        Object bodyParameter = null;

        if (httpMethod.getBodyType() != null)
            bodyParameter = objectMapper.convertValue(body, httpMethod.getBodyType());

        return bodyParameter;
    }

}
