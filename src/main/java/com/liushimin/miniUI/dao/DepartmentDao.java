package com.liushimin.miniUI.dao;

import com.liushimin.miniUI.pojo.Department;

public class DepartmentDao extends BaseDao<Department> {

    @Override
    protected String getTableName() {
        return "T_DEPARTMENT";
    }
}
