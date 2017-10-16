package com.dcits.ftp.exception;

/**
 * Created by zhanghuiq on 2017/2/8.
 */
public class BusinessException extends RuntimeException {

    public BusinessException() {
        super();
    }

    /**
     *
     * @param message String
     * @param cause Throwable
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param message String
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     *
     * @param cause Throwable
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }
}
