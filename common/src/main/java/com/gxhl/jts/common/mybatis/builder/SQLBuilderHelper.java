package com.gxhl.jts.common.mybatis.builder;

import com.rogerli.common.mybatis.mapping.ColumnMapping;
import com.rogerli.common.mybatis.mapping.ColumnValue;

import java.util.List;

public final class SQLBuilderHelper<T> {

    private static SQLBuilderHelper self;

    /**
     *
     * @return
     */
    public static SQLBuilderHelper getInstance() {
        if (self == null) {
            self = new SQLBuilderHelper();
        }
        return self;
    }

    /**
     *
     * @param clazz
     * @return
     */
    public List<ColumnMapping> getColumnMapping(Class clazz) {
        return null;
    }

    /**
     * TODO
     *
     * @param clazz
     * @return
     */
    public String getTableName(Class clazz) {
        return null;
    }

    /**
     * TODO
     *
     * @param item
     * @return
     */
    public List<ColumnValue> getColumnValue(T item) {
        return null;
    }

}
