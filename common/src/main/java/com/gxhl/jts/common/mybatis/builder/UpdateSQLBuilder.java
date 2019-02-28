package com.gxhl.jts.common.mybatis.builder;

import java.util.HashMap;

public class UpdateSQLBuilder extends SQLBuilder {

    private UpdateSQLBuilder sqlBuilder;
    private HashMap sqlMap;

    /**
     *
     */
    public UpdateSQLBuilder() {
        if (sqlBuilder == null) {
            sqlBuilder = new UpdateSQLBuilder();
        }
        if (sqlMap == null) {
            sqlMap = new HashMap();
        }
    }

    @Override
    public SQLBuilder getBuilder() {
        return sqlBuilder;
    }
}
