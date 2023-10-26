package com.example.shared.enumeration;

import lombok.Getter;

@Getter
public enum NotificationType {
  ALERT("ALERT"),
    REMINDER("REMINDER"),
    ;
  String value;
    NotificationType(String value) {
        this.value = value;
    }


}
