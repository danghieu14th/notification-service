package com.example.shared.exception;

import org.springframework.http.HttpStatus;

public class CommonException extends GimoException {

     public CommonException() {
         super(null, "CMN_001", ErrorCodeList.InvalidParameter.toString(), HttpStatus.BAD_REQUEST);
//         super(ErrorCodeList.InvalidParameter.toString());
     }
}
