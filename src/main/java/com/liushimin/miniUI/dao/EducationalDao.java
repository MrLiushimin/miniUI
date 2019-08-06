package com.liushimin.miniUI.dao;

import com.liushimin.miniUI.enumeration.IdType;
import com.liushimin.miniUI.pojo.Educational;

public class EducationalDao extends BaseDao<Educational> {

    @Override
    protected String getTableName() {
        return "T_EDUCATIONAL";
    }
    @Override
    protected String getIdType() {
        return IdType.AUTO.getCode();
    }
    @Override
    protected String getSeqName() {
        return "SEQ_EDU";
    }
}
