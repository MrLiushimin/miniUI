package com.liushimin.miniUI.servlet;

import com.liushimin.miniUI.pojo.Employee;
import com.liushimin.miniUI.util.ConvertUtils;
import com.liushimin.miniUI.util.JSON;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeServlet extends BaseServlet {

    public void searchEmployees(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String pageIndex = request.getParameter("pageIndex");
        String pageSize = request.getParameter("pageSize");
        String sortField = request.getParameter("sortField");
        String sortOrder = request.getParameter("sortOrder");

        Integer page = StringUtils.isEmpty(pageIndex)?null:Integer.valueOf(pageIndex);
        Integer size = StringUtils.isEmpty(pageIndex)?null:Integer.valueOf(pageSize);
        HashMap<String, Boolean> orderFields = null;
        if (StringUtils.isNotBlank(sortField)) {
            orderFields = new HashMap<String, Boolean>();
            orderFields.put(sortField, (StringUtils.isNotBlank(sortField)&&"asc".equals(sortOrder))?true:false);
        }
        HashMap result = appService.qryEmpByPageAndSort(null, orderFields, page, size);
//        System.out.println(result);
        response.getWriter().println(JSON.encode(result));
    }

    public void saveEmployees(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String data = request.getParameter("data");
        ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) JSON.decode(data);
        appService.saveEmployees(list);
    }

    public void deleteEmployeeById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        appService.deleteEmployeeById(id);
    }

    public void findEmployeesById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        HashMap result = appService.findEmployeesById(id);
        response.getWriter().println(JSON.encode(result));
    }

}
