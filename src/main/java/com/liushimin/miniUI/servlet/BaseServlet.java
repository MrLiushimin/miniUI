package com.liushimin.miniUI.servlet;

import com.liushimin.miniUI.service.AppService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Map;

public class BaseServlet extends HttpServlet {

    AppService appService = new AppService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String methodName = req.getParameter("method");
        if (null == methodName || "".equals(methodName)) {
            resp.getWriter().println("method参数为null");
            return;
        }
        Class clazz = this.getClass();
        try {
            Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            String path = (String) method.invoke(this, req, resp);
            if (path != null) {
                req.getRequestDispatcher(path).forward(req, resp);
            }

        } catch (Exception e) {
            resp.sendError(503, e.getMessage());
            e.printStackTrace();
        }

    }

}
