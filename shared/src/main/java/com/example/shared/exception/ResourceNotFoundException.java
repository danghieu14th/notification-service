package com.example.shared.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends GimoException {

     public ResourceNotFoundException(String source, String by, String value) {
         super(null, "400", String.format("%s not found with %s : '%s'", source, by, value), HttpStatus.BAD_REQUEST);
//         super(ErrorCodeList.InvalidParameter.toString());
     }
}
