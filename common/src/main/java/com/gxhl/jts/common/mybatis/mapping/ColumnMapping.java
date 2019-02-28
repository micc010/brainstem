package com.gxhl.jts.common.mybatis.mapping;

import lombok.Data;

import java.lang.reflect.Field;

@Data
public class ColumnMapping {

    private String columnName;
    private String fieldName;
    private Field field;

    /**
     * TODO
     *
     * @return
     */
    public boolean isId() {
        return false;
    }
}
