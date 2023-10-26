package com.example.shared.config.logging;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdentifyNumberMasker implements LogMasker {
    private static final Pattern identifyNumberFindPattern = Pattern.compile("\\b((\\d{9})|(\\d{12}))\\b");
    private static final String identifyNumberMaskPattern = "(?<=^.).*(?=.{4}$)";

    public StringBuffer mask(StringBuffer stringBuffer, String maskChar) {
        Matcher matcher = identifyNumberFindPattern.matcher(stringBuffer);
        while (matcher.find()) {
            String phoneNumber = matcher.group();
            String maskedPhoneNumber = phoneNumber.replaceAll(identifyNumberMaskPattern,
                "*".repeat(phoneNumber.length() - 5));
            int idx = stringBuffer.indexOf(phoneNumber);
            stringBuffer.replace(idx, idx + phoneNumber.length(), maskedPhoneNumber);
        }
        return stringBuffer;
    }
}
