package com.example.shared.response;

import com.example.shared.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> implements Serializable {
    private static final long serialVersionUID = -6669686067446636607L;

    protected String code;

    protected String message;

    protected T result;

    public CommonResponse(T result) {
        this.code = Constant.SUCCESS_CODE;
        this.message = Constant.SUCCESS_MESSAGE;
        this.result = result;
    }

    public static CommonResponse<Object> internalError() {
        return new CommonResponse<>(
                Constant.INTERNAL_SERVER_ERROR_CODE,
                Constant.INTERNAL_SERVER_ERROR_MESSAGE,
                null
        );
    }

    public static CommonResponse<Object> badRequest(String message) {
        return new CommonResponse<>(
                HttpStatus.BAD_REQUEST.toString(),
                message,
                null
        );
    }
}
