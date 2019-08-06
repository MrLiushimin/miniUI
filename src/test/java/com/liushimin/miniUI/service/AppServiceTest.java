package com.liushimin.miniUI.service;

import com.liushimin.miniUI.dao.EmployeeDao;
import com.liushimin.miniUI.dao.FileDao;
import com.liushimin.miniUI.db.DataBase;
import org.junit.Test;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class AppServiceTest {

    @Test
    public void getFileById() {
        System.out.println("haha");
    }

    @Test
    public void tranTest() throws Exception {
        Connection con = DataBase.getConnection();
        con.setAutoCommit(false);
        EmployeeDao employeeDao = new EmployeeDao();
        FileDao fileDao = new FileDao();
        Map employee = new HashMap<String, Object>();
        employee.put("name", "刘世民");
        Map file = new HashMap<String, Object>();
        file.put("name", "haha");
        employeeDao.insert(employee, con);
        fileDao.insert(file, con);
        System.out.println(1/0);
        con.commit();
        DataBase.close(con);

    }

}