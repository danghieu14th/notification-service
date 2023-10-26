package com.example.shared.enumeration;

import lombok.Getter;

@Getter
public enum SortBy {
    CREATED_FROM("created_at"),
    ID("id"),
    COMPLETED_FROM("completed_at");

    private final String value;

    SortBy(String value) {
        this.value = value;
    }
}
