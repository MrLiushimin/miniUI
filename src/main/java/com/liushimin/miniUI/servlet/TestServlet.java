package com.liushimin.miniUI.servlet;

import com.liushimin.miniUI.db.DataBase;
import com.liushimin.miniUI.util.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

public class TestServlet extends BaseServlet {

    public String test2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /*String sql = "select * from T_EMPLOYEE";
        ArrayList list = new DataBase().select(sql);
        System.out.println("访问test1");
        String json = JSON.encode(list);
        response.getWriter().println(json);*/
        return null;
    }

    public void test1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /*Map param = request.getParameterMap();
        System.out.println(param);
        request.setAttribute("msg", "访问成功2");
        response.getWriter().println("哈哈哈嗝");*/
       /* String sql = "select * from T_EMPLOYEE";
        ArrayList list = new DataBase().select(sql);
        System.out.println("访问test2");
        System.out.println("哈哈哈嗝");
        String json = JSON.encode(list);
        response.getWriter().println(json);*/
    }

}
