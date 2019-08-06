package com.liushimin.miniUI.dao;

import com.liushimin.miniUI.db.DataBase;

import java.util.ArrayList;

public class TestDao {
    public static void main(String[] args) throws Exception {
        DepartmentDao dao = new DepartmentDao();
        dao.getIdType();

        String sql = "SELECT column_name FROM user_col_comments WHERE table_name = 'T_EMPLOYEE'";
//        ArrayList list = DataBase.select(sql, null, DataBase.getConnection());
//        System.out.println(list);

    }

}
