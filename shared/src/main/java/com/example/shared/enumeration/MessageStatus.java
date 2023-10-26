package com.example.shared.enumeration;

import lombok.Getter;

@Getter
public enum MessageStatus {
    PENDING("pending"),
    FAILED("failed"),
    QUEUED("queued"),
    SENDING("sending"),
    DELIVERED("delivered"),
    SENT("sent"),
    UNDELIVERED("undelivered"),
    RECEIVING("receiving"),
    RECEIVED("received"),
    ACCEPTED("accepted"),
    SCHEDULED("scheduled"),
    READ("read"),
    PARTIALLY_DELIVERED("partially_delivered"),
    CANCELED("canceled")
    ;
    String value;

    MessageStatus(String value) {
        this.value = value;
    }

}
