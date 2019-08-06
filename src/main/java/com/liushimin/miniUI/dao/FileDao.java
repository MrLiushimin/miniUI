package com.liushimin.miniUI.dao;

import com.liushimin.miniUI.enumeration.IdType;
import com.liushimin.miniUI.pojo.File;

public class FileDao extends BaseDao<File> {

    @Override
    protected String getTableName() {
        return "PLUS_FILE";
    }
    @Override
    protected String getIdType() {
        return IdType.AUTO.getCode();
    }
    @Override
    protected String getSeqName() {
        return "SEQ_FILE";
    }
}
