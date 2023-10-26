package com.example.shared.config.logging;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneMasker implements LogMasker {
    private static final Pattern phoneFindPattern =
        Pattern.compile("\\b(\\+?84|0)(\\d{9})\\b");

    private static final String phoneMaskPattern = "(\\+?\\d{2,3})\\d{4}(\\d{4})";

    public StringBuffer mask(StringBuffer stringBuffer, String maskChar) {
        Matcher matcher = phoneFindPattern.matcher(stringBuffer);
        while (matcher.find()) {
            String phoneNumber = matcher.group();
            String maskedPhoneNumber = phoneNumber.replaceAll(phoneMaskPattern, "$1****$2");
            int idx = stringBuffer.indexOf(phoneNumber);
            stringBuffer.replace(idx, idx + phoneNumber.length(), maskedPhoneNumber);
        }
        return stringBuffer;
    }
}
