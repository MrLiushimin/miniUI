package com.liushimin.miniUI.factory;

import com.liushimin.miniUI.dao.*;

public class DaoFactory {
    public static EmployeeDao getEmployeeDao() {
        return new EmployeeDao();
    }

    public static DepartmentDao getDeptDao() {
        return new DepartmentDao();
    }

    public static EducationalDao getEduDao() {
        return new EducationalDao();
    }

    public static PositionDao getPositionDao() {
        return new PositionDao();
    }

    public static FileDao getFileDao() {
        return new FileDao();
    }
}
