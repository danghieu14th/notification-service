package com.example.shared.enumeration;

import lombok.Getter;

@Getter
public enum SortDirection {
    ASC("ASC"),
    DESC("DESC");
    
    private final String value;
    
    SortDirection(String value) {
        this.value = value;
    }
}
