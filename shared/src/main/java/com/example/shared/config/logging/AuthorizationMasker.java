package com.example.shared.config.logging;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorizationMasker implements LogMasker {

    private static final Pattern authorizationRegex =
        Pattern.compile(
            "(Authorization|authorization|secret|token|apiKey|x-api-key)(=|:| )((\\[([^\\]]*)\\])|([a-zA-Z\\d]+))");

    public StringBuffer mask(StringBuffer stringBuffer, String maskChar) {
        Matcher matcher = authorizationRegex.matcher(stringBuffer);
        if (matcher.find()) {
            String password = matcher.group();
            int idx = stringBuffer.indexOf(password);
            stringBuffer.replace(idx, idx + password.length(), "Authorization=[******]");
        }
        return stringBuffer;
    }
}
