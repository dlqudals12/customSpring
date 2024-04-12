package org.bmSpring.servlet.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bmSpring.servlet.model.HttpServletRequestInfo;
import org.bmSpring.servlet.model.HttpServletResponseInfo;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpServletRunnerImpl implements HttpServletRunner {

    private final ObjectMapper objectMapper;

    public HttpServletRunnerImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void runServlet(HttpServletRequestInfo reader, HttpServletResponseInfo writer, Object controller) {
        PrintWriter out = writer.getPrintWriter();
        try {
            Method method = reader.getMethod();

            List<Object> parameters = new ArrayList<>();

            if (!reader.getRequestParams().isEmpty()) parameters.addAll(reader.getRequestParams());
            if (reader.getResponseBody() != null) parameters.add(reader.getResponseBody());
            if (reader.isHttpServletRequest()) parameters.add(reader);
            if (reader.isHttpServletResponse()) parameters.add(writer);

            Object invoke = parameters.isEmpty() ? method.invoke(controller) : method.invoke(controller, parameters);

            String dataJson = objectMapper.writeValueAsString(invoke);

            out.println(createHttpMessage(200));

            //header
            for (Map.Entry<String, Object> headers : writer.getHeader().entrySet()) {
                String header = headers.getKey() + ": " + headers.getValue();
                out.println(header);
            }

            out.println();
            out.println(dataJson);
            out.close();
        } catch (Throwable e) {
            out.println(createHttpMessage(500));
            out.println("Content-Type: " + writer.getContentType());
            out.println();
            out.close();
        }
    }

    public String createHttpMessage(int code) {
        String msg = switch (code) {
            case 200 -> "OK";
            default -> "ERROR";
        };

        return String.format("HTTP/1.1 %d %s", code, msg);
    }


}
