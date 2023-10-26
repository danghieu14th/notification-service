package com.example.shared.config.logging;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OtpMasker implements LogMasker {
    private static final Pattern otpFindPattern = Pattern.compile("\\b(\\d{6})\\b");
    private static final String otpMaskPattern = "(\\d)\\d{3}(\\d{2})";

    public StringBuffer mask(StringBuffer stringBuffer, String maskChar) {
        Matcher matcher = otpFindPattern.matcher(stringBuffer);
        while (matcher.find()) {
            String otp = matcher.group();
            String maskedPhoneNumber = otp.replaceAll(otpMaskPattern, "$1***$2");
            int idx = stringBuffer.indexOf(otp);
            stringBuffer.replace(idx, idx + otp.length(), maskedPhoneNumber);
        }
        return stringBuffer;
    }
}
