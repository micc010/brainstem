package com.gxhl.jts.common.mybatis.builder;

public class SelectSQLBuilder extends SQLBuilder {

    private SelectSQLBuilder sqlBuilder;

    /**
     *
     */
    public SelectSQLBuilder() {
        if (sqlBuilder == null) {
            sqlBuilder = new SelectSQLBuilder();
        }
    }

    /**
     *
     * @return
     */
    @Override
    public SelectSQLBuilder getBuilder() {
        return sqlBuilder;
    }
}
