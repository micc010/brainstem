package com.gxhl.jts.common.mybatis.exception;

public class AutoSQLException extends RuntimeException {

    public AutoSQLException() {

    }

    public AutoSQLException(String message) {
        super(message);
    }

    public AutoSQLException(String message, Throwable cause) {
        super(message, cause);
    }

    public AutoSQLException(Throwable cause) {
        super(cause);
    }

    public AutoSQLException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
