package com.example.shared.enumeration;

import lombok.Getter;

@Getter
public enum ChannelType {
    SMS("sms"),
    EMAIL("email"),

    ;

    private String value;

    ChannelType(String value) {
        this.value = value;
    }

}
