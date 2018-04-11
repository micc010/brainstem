package com.github.rogerli.common.exception;

/**
 * Created by lt on 2017/6/20.
 */
public class EndpointException extends RuntimeException{

    public EndpointException() {

    }

    public EndpointException(String message) {
        super(message);
    }

    public EndpointException(String message, Throwable cause) {
        super(message, cause);
    }

    public EndpointException(Throwable cause) {
        super(cause);
    }

    public EndpointException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
