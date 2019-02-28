package com.gxhl.jts.common.mybatis.builder;

public class DeleteSQLBuilder extends SQLBuilder {

    private DeleteSQLBuilder sqlBuilder;

    /**
     *
     */
    public DeleteSQLBuilder() {
        if (sqlBuilder == null) {
            sqlBuilder = new DeleteSQLBuilder();
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
