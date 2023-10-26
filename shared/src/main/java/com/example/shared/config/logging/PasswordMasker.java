package com.example.shared.config.logging;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordMasker implements LogMasker {

    @Override
    public StringBuffer mask(StringBuffer stringBuffer, String maskChar) {
        Matcher matcher = passwordFindPattern.matcher(stringBuffer);
        StringBuffer output = new StringBuffer();
        while (matcher.find()) {
            String match = matcher.group();
            String replacement = "password=" + maskChar.repeat(match.length() - 9);
            matcher.appendReplacement(output, replacement);
        }
        matcher.appendTail(output);
        return output;
    }

    private static final Pattern passwordFindPattern =
        Pattern.compile("(?i)((?:password)(?::|=| =|)(?:\\s*)[A-Za-z0-9@$!%*?&.;<>]+)");
}
