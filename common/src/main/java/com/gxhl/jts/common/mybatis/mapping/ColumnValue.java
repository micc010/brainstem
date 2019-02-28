package com.gxhl.jts.common.mybatis.mapping;

import lombok.Data;

@Data
public class ColumnValue {

    private String columnName;
    private String fieldName;
    private ColumnMapping columnMapping;

    /**
     * @return
     */
    public boolean isValueNull() {
        return false;
    }
}
