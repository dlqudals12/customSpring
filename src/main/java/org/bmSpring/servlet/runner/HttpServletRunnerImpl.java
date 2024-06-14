package org.bmSpring.servlet.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bmSpring.annotations.parameter.RequestParam;
import org.bmSpring.annotations.parameter.ResponseBody;
import org.bmSpring.exception.HttpRequestException;
import org.bmSpring.exception.HttpServerException;
import org.bmSpring.servlet.model.*;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpServletRunnerImpl implements HttpServletRunner {

    private final ObjectMapper objectMapper;

    private static final String BODY = "@REQUEST_BODY";
    private static final String SERVLET_REQUEST = "@SERVLET_REQUEST";
    private static final String SERVLET_RESPONSE = "@SERVLET_RESPONSE";

    public HttpServletRunnerImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void runServlet(HttpServletRequestInfo reader, HttpServletResponseInfo writer, Object controller) {
        PrintWriter out = writer.getPrintWriter();
        try {
            Method method = reader.getMethod();

            Map<String, Object> paramterMap = new HashMap<>();
            List<Object> parameters = new ArrayList<>();

            if (!reader.getRequestParams().isEmpty()) {
                paramterMap = reader.getRequestParams();
            }

            if (reader.getResponseBody() != null) paramterMap.put(BODY, reader.getResponseBody());
            if (reader.isHttpServletRequest()) paramterMap.put(SERVLET_REQUEST, reader);
            if (reader.isHttpServletResponse()) paramterMap.put(SERVLET_RESPONSE, writer);

            for (Parameter parameter : method.getParameters()) {

                boolean isRequest = parameter.isAnnotationPresent(RequestParam.class);
                boolean isResponse = parameter.isAnnotationPresent(ResponseBody.class);

                if (isRequest && isResponse) throw new HttpRequestException();

                if (isRequest) {
                    RequestParam requestParam = parameter.getAnnotation(RequestParam.class);

                    String name = !requestParam.name().isEmpty() ? requestParam.name() : parameter.getName();

                    parameters.add(paramterMap.get(name));
                }

                if (isResponse) parameters.add(paramterMap.get(BODY));
                if (parameter.getType() == HttpServletRequest.class) parameters.add(paramterMap.get(SERVLET_REQUEST));
                if (parameter.getType() == HttpServletResponse.class) parameters.add(paramterMap.get(SERVLET_RESPONSE));
            }

            Object invoke = parameters.isEmpty() ? method.invoke(controller) : method.invoke(controller, parameters.toArray(new Object[0]));

            String dataJson = objectMapper.writeValueAsString(invoke);

            out.println(createHttpMessage(200));

            //header
            for (Map.Entry<String, Object> headers : writer.getHeader().entrySet()) {
                if (headers.getKey().equals("cookie") || headers.getKey().equals("Cookie")) continue;

                String header = headers.getKey() + ": " + headers.getValue();
                out.println(header);
            }

            StringBuilder cookieBuilder = new StringBuilder();

            cookieBuilder.append("Cookie: ");

            //cookie
            for (Cookie cookie : writer.getCookies()) {
                cookie.cookieString(cookieBuilder);
            }

            out.println(cookieBuilder);
            out.println();
            out.println(dataJson);
            out.close();
        } catch (Throwable e) {
            throw new HttpServerException();
        }
    }

    @SuppressWarnings("all")
    public String createHttpMessage(int code) {
        String msg = switch (code) {
            case 200 -> "OK";
            default -> "ERROR";
        };

        return String.format("HTTP/1.1 %d %s", code, msg);
    }


}
