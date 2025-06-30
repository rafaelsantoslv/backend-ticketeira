package com.unyx.ticketeira.exception;

import lombok.Getter;

@Getter
public class PaymentGatewayException extends PaymentException {
    private final int statusCode;
    private final String errorCode;
    private final String errorMessage;

    public PaymentGatewayException(String message, int statusCode, String errorCode, String errorMessage) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public PaymentGatewayException(String message, int statusCode, String errorCode, String errorMessage, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }


}
