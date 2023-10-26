package com.example.shared.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GimoException extends RuntimeException {

    private final String code;

    private final String message;

    private final HttpStatus httpStatus;

    public GimoException(Throwable cause, String code, String message, HttpStatus httpStatus) {
        super(cause);
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public GimoException(Throwable cause, ErrorCodeList errorCode, HttpStatus httpStatus ) {
        super(cause);
        this.code = errorCode.toCode();
        this.message = errorCode.toString();
        this.httpStatus = httpStatus;
    }
}
