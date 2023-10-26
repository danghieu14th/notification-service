package com.example.shared.config.logging;

import org.apache.commons.lang3.RegExUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailMasker implements LogMasker {
    private final Pattern emailFindPattern =
        Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    private final Pattern emailMaskPattern =
        Pattern.compile("(?<=.)[^@](?=[^@]*?[^@]@)|(?:(?<=@.)|(?!^)\\G(?=[^@]*$)).(?=.*[^@]\\.)");

    public StringBuffer mask(StringBuffer stringBuffer, String maskChar) {
        Matcher matcher = emailFindPattern.matcher(stringBuffer);
        while (matcher.find()) {
            String email = matcher.group();
            String masked = RegExUtils.replaceAll(email, emailMaskPattern, maskChar);
            int idx = stringBuffer.indexOf(email);
            stringBuffer.replace(idx, idx + email.length(), masked);
        }
        return stringBuffer;
    }
}
