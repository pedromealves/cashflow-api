package com.pedro.cashflow_api.exceptions;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String msg) {
        super(msg);
    }

}
