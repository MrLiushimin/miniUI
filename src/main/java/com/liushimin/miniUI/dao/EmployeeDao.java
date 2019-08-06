package com.liushimin.miniUI.dao;

import com.liushimin.miniUI.enumeration.IdType;
import com.liushimin.miniUI.pojo.Employee;

public class EmployeeDao extends BaseDao<Employee> {

    @Override
    protected String getTableName() {
        return "T_EMPLOYEE";
    }

    @Override
    protected String getIdType() {
        return IdType.UUID.getCode();
    }
}
