package com.gxhl.jts.common.mybatis.builder;

public abstract class SQLBuilder {

    /**
     *
     * @param table
     * @return
     */
    public SQLBuilder table(String table) {

        // TODO

        return this;
    }

    /**
     * TODO
     *
     * @return
     */
    public String toSqlString() {
        return null;
    }

    /**
     * @param columnName
     * @param fieldName
     * @param operator
     * @return
     */
    public SQLBuilder condition(String columnName, String fieldName, String operator) {

        // TODO

        return this;
    }

    /**
     * TODO
     *
     * @param columnName
     * @param fieldName
     */
    public void set(String columnName, String fieldName) {

    }

    /**
     * @return
     */
    public abstract SQLBuilder getBuilder();

}
