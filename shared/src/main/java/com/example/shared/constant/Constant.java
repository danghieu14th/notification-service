package com.example.shared.constant;

import java.time.ZoneOffset;

public class Constant {
    public static final String SYSTEM = "System";

    public static final String DEFAULT_NATION = "Việt Nam";
    public static final String USER_VI = "Người dùng";

    public static final String UNKNOWN = "Unknown";
    public static final String SUCCESS_CODE = "200";
    public static final String INTERNAL_SERVER_ERROR_CODE = "500";

    public static final String SUCCESS_MESSAGE = "Success";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Unknown error";

    public static final String VIETNAM_OFFSET_ID = "+07:00";
    public static final ZoneOffset VIETNAM_ZONE_OFFSET = ZoneOffset.of(VIETNAM_OFFSET_ID);
}
