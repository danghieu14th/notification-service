package com.example.shared.exception;

public class ResourceNotFoundException extends RuntimeException {

     public ResourceNotFoundException(String source, String by, String value) {
         super(String.format("%s not found with %s : '%s'", source, by, value));
     }
}
