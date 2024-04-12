package org.bmSpring.servlet.runner;

import org.bmSpring.servlet.model.HttpServletRequestInfo;
import org.bmSpring.servlet.model.HttpServletResponseInfo;

public interface HttpServletRunner {

    void runServlet(HttpServletRequestInfo request, HttpServletResponseInfo response, Object controller);

    String createHttpMessage(int code);

}
