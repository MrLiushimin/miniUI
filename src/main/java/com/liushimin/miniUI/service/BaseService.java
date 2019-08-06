package com.liushimin.miniUI.service;

import com.liushimin.miniUI.dao.*;
import com.liushimin.miniUI.db.DataBase;
import com.liushimin.miniUI.factory.DaoFactory;

import java.sql.Connection;

public class BaseService {

    EmployeeDao employeeDao = DaoFactory.getEmployeeDao();
    DepartmentDao departmentDao = DaoFactory.getDeptDao();
    EducationalDao educationalDao = DaoFactory.getEduDao();
    PositionDao positionDao = DaoFactory.getPositionDao();
    FileDao fileDao = DaoFactory.getFileDao();

    protected Connection startTransaction() throws Exception {
        Connection conn = DataBase.getConnection();
        conn.setAutoCommit(false);
        return conn;
    }

    protected void closeTransaction(Connection conn) throws Exception {
        if (conn != null) {
            DataBase.close(conn);
        }
    }



}
