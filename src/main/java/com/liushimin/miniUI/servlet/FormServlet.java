package com.liushimin.miniUI.servlet;

import com.liushimin.miniUI.util.FileUtils;
import com.liushimin.miniUI.util.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class FormServlet extends BaseServlet {

    public void saveData(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String submitJSON = req.getParameter("data");
        HashMap data = (HashMap) JSON.decode(submitJSON);
        String userName = data.get("UserName") != null ? data.get("UserName").toString() : "";
        String pwd = data.get("pwd") != null ? data.get("pwd").toString() : "";

        System.out.println(userName + ":" + pwd);
        resp.getWriter().write(JSON.encode(data));
    }

    public void loadData(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String path = req.getSession().getServletContext().getRealPath("/");
        System.out.println(path);
        String file = path + "/html/data/form.txt";
        String read = FileUtils.read(file);
        HashMap data = (HashMap) JSON.decode(read);
        String json = JSON.encode(data);
        resp.getWriter().write(json);
    }

}
