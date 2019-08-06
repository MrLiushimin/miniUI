package com.liushimin.miniUI.service;

import com.liushimin.miniUI.dao.*;
import com.liushimin.miniUI.factory.DaoFactory;
import com.liushimin.miniUI.pojo.Employee;
import com.liushimin.miniUI.pojo.File;
import com.liushimin.miniUI.util.ConvertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppService extends BaseService {

    /*public void getFileById() throws Exception {
        //开启事务方法
        Connection conn = startTransaction();
        departmentDao.insert(new HashMap(), conn);
        fileDao.findByid("", conn);
        closeTransaction(conn);
    }*/

    public HashMap qryEmpByPageAndSort(HashMap<String, Object> param, HashMap<String, Boolean> orderFields, Integer page, Integer size) throws Exception {
        HashMap result = new HashMap();
        List<Employee> list = employeeDao.findSortPage(param, orderFields, page, size);
        int count = employeeDao.count(param);
        result.put("data", ConvertUtils.convertToMapList(Employee.class, list));
        result.put("total", count);
        return result;
    }

    public void deleteEmployeeById(String id) throws Exception {
        employeeDao.delete(id);
    }


    public void saveEmployees(ArrayList<HashMap<String, Object>> list) throws Exception {
        List<Employee> updateList = new ArrayList<Employee>();
        List<Employee> insertList = new ArrayList<Employee>();
        for (int i = 0; i < list.size(); i++) {
            String state = (String) list.get(i).get("_state");
            String id = (String) list.get(i).get("id");
            if ((StringUtils.isNotBlank(state) && "added".equals(state))||StringUtils.isBlank(id)) {
                insertList.add(ConvertUtils.convertToBean(Employee.class, list.get(i)));
            } else {
                updateList.add(ConvertUtils.convertToBean(Employee.class, list.get(i)));
            }
        }
        System.out.println("insertList.size:" + insertList.size() + "\tupdateList.size:" + updateList.size());
        if (CollectionUtils.isNotEmpty(updateList)) {
            employeeDao.updateList(updateList);
        }
        if (CollectionUtils.isNotEmpty(insertList)) {
            employeeDao.insertList(insertList);
        }
    }

    public HashMap findEmployeesById(String id) throws Exception {
        HashMap result = new HashMap();
        Employee employee = employeeDao.findByid(id);
        result.put("data", ConvertUtils.convertToMap(employee));
        return result;
    }
}
