package com.liushimin.miniUI.dao;

import com.liushimin.miniUI.pojo.Position;

public class PositionDao extends BaseDao<Position> {

    @Override
    protected String getTableName() {
        return "T_POSITION";
    }
}
