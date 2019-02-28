package com.gxhl.jts.common.mybatis.builder;

public class InsertSQLBuilder extends SQLBuilder {

    private InsertSQLBuilder sqlBuilder;

    /**
     *
     */
    public InsertSQLBuilder() {
        if (sqlBuilder == null) {
            sqlBuilder = new InsertSQLBuilder();
        }
    }

    /**
     *
     * @return
     */
    @Override
    public SQLBuilder getBuilder() {
        return null;
    }
}
