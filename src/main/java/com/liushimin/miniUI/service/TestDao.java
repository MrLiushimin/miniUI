package com.liushimin.miniUI.service;

import com.liushimin.miniUI.Exception.CommonException;
import com.liushimin.miniUI.dao.DepartmentDao;
import com.liushimin.miniUI.dao.EducationalDao;
import com.liushimin.miniUI.dao.EmployeeDao;
import com.liushimin.miniUI.db.DataBase;
import com.liushimin.miniUI.util.JSON;

import java.sql.Timestamp;
import java.util.*;

public class TestDao {
    public static void main(String[] args) throws Exception {
        /*String sql = "SELECT column_name FROM user_col_comments WHERE table_name = 'T_EMPLOYEE'";
        ArrayList list = DataBase.select(sql, null, DataBase.getConnection());*/
//        DepartmentDao dao = new DepartmentDao();
//        HashMap sc = dao.findByid("sc");
        /*HashMap<String, Boolean> orderParam = new HashMap<String, Boolean>();
        orderParam.put("name", true);
        orderParam.put("id", false);
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("name", "人事部");
        param.put("id", "rs");
        ArrayList sortPage = dao.findSortPage(param, orderParam, 0, 15);*/
//        System.out.println(sc);
        /*HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("name", "人事部sfsf");
        param.put("manager_name", "654");
        param.put("id", "rs");
        dao.update(param);*/
//id=rs, manager=null, name=人事部, manager_name=null
//        System.out.println(sortPage);
        /*EducationalDao dao = new EducationalDao();
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("name", "是否");
        param.put("id", 7);
        dao.insert(param);*/


       /* String sql = "select \"birthday\" from T_EMPLOYEE where \"id\" = @id";
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("id", "8197fb45-2b60-4309-820c-e70824fc9b33");
        Object o = DataBase.selectScalar(sql, param, null);
        System.out.println(o);*/

        EmployeeDao dao = new EmployeeDao();
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("loginname", "123");
        param.put("birthday", new Timestamp(2018,12,4,16,16,57,00));

//        param.put("id", 7);
        dao.insert(param);

    }

}
