package com.gxhl.jts.common.mybatis.provider;

import com.gxhl.jts.common.mybatis.exception.AutoSQLException;
import com.gxhl.jts.common.mybatis.mapping.ColumnMapping;
import com.gxhl.jts.common.mybatis.mapping.ColumnValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SQLProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(SQLProvider.class);
    private SQLBuilderHelper sqlHelper = SQLBuilderHelper.getInstance();

    /**
     * @param item
     * @param <T>
     * @return
     * @throws AutoSQLException
     */
    public <T> String selectById(T item) throws AutoSQLException {
        String table = sqlHelper.getTableName(item.getClass());
        ColumnMapping columnMapping = getIdColumn(item.getClass());
        if (columnMapping == null) {
            throw new AutoSQLException(String.format("There is no identity column in table[%s]", table));
        }
        SelectSQLBuilder sqlBuilder = new SelectSQLBuilder();
        sqlBuilder.table(table).condition(columnMapping.getColumnName(), columnMapping.getField().getName(), "=");
        return sqlBuilder.toSqlString();
    }

    /**
     *
     * @param item
     * @param <T>
     * @return
     * @throws AutoSQLException
     */
    public <T> String selectOne(T item) throws AutoSQLException {
        String table = sqlHelper.getTableName(item.getClass());
        List<ColumnValue> values = sqlHelper.getColumnValue(item);
        SelectSQLBuilder sqlBuilder = new SelectSQLBuilder();
        sqlBuilder.table(table);
        for (ColumnValue value : values) {
            if (!value.isValueNull()) {
                sqlBuilder.condition(value.getColumnName(), value.getFieldName(), "=");
            }
        }
        return sqlBuilder.toSqlString();
    }

    /**
     *
     * TODO
     *
     * @param item
     * @param <T>
     * @return
     * @throws AutoSQLException
     */
    public <T> String select(T item) throws AutoSQLException {
        return selectOne(item);
    }

    /**
     *
     * @param item
     * @param <T>
     * @return
     * @throws AutoSQLException
     */
    public <T> String delete(T item) throws AutoSQLException {
        String table = sqlHelper.getTableName(item.getClass());
        List<ColumnValue> values = sqlHelper.getColumnValue(item);
        DeleteSQLBuilder sqlBuilder = new DeleteSQLBuilder();
        sqlBuilder.table(table);
        for (ColumnValue value : values) {
            if (!value.isValueNull()) {
                sqlBuilder.condition(value.getColumnName(), value.getFieldName(), "=");
            }
        }
        return sqlBuilder.toSqlString();
    }

    public <T> String deleteById(T item) throws AutoSQLException {
        ColumnMapping mapping = getIdColumn(item.getClass());
        String table = sqlHelper.getTableName(item.getClass());
        if (mapping == null) {
            throw new AutoSQLException(String.format("There is no identity column in table[%s]", table));
        }
        DeleteSQLBuilder sqlBuilder = new DeleteSQLBuilder();
        sqlBuilder.table(table).condition(mapping.getColumnName(), mapping.getFieldName(), "=");
        return sqlBuilder.toSqlString();
    }

    /**
     *
     * @param item
     * @param <T>
     * @return
     * @throws AutoSQLException
     */
    public <T> String updateById(T item) throws AutoSQLException {
        String table = sqlHelper.getTableName(item.getClass());
        List<ColumnValue> values = sqlHelper.getColumnValue(item);
        UpdateSQLBuilder sqlBuilder = new UpdateSQLBuilder();
        sqlBuilder.table(table);
        for (ColumnValue value : values) {
            if (!value.getColumnMapping().isId()) {
                sqlBuilder.set(value.getColumnName(), value.getFieldName());
            }
            if (value.getColumnMapping().isId()) {
                sqlBuilder.condition(value.getColumnName(), value.getFieldName(), "=");
            }
        }
        return sqlBuilder.toSqlString();
    }

    /**
     *
     * @param item
     * @param <T>
     * @return
     * @throws AutoSQLException
     */
    public <T> String updateSelectiveById(T item) throws AutoSQLException {
        String table = sqlHelper.getTableName(item.getClass());
        List<ColumnValue> values = sqlHelper.getColumnValue(item);
        UpdateSQLBuilder sqlBuilder = new UpdateSQLBuilder();
        sqlBuilder.table(table);
        for (ColumnValue value : values) {
            if (!value.isValueNull() && !value.getColumnMapping().isId()) {
                sqlBuilder.set(value.getColumnName(), value.getFieldName());
            }
            if (value.getColumnMapping().isId()) {
                sqlBuilder.condition(value.getColumnName(), value.getFieldName(), "=");
            }
        }
        return sqlBuilder.toSqlString();
    }

    /**
     *
     * @param clazz
     * @return
     */
    private ColumnMapping getIdColumn(Class<?> clazz) {
        List<ColumnMapping> mappings = sqlHelper.getColumnMapping(clazz);
        for (ColumnMapping mapping : mappings) {
            if (mapping.isId()) {
                return mapping;
            }
        }
        return null;
    }

    public <T> String insert(T item) throws AutoSQLException {
        String tableName = sqlHelper.getTableName(item.getClass());
        List<ColumnMapping> values =
                sqlHelper.getColumnMapping(item.getClass());
        if (values.isEmpty()) {
            throw new AutoSQLException(String.format("Table[%s] has no column"));
        }
        InsertSQLBuilder sqlBuilder = new InsertSQLBuilder();
        sqlBuilder.table(tableName);
        for (ColumnMapping mapping : values) {
            sqlBuilder.column(mapping.getColumnName());
            sqlBuilder.field(mapping.getField().getName());
        }
        return sqlBuilder.toSqlString();
    }
}