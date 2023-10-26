package com.example.shared.config.logging;


import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Plugin(name = "logmasker", category = PatternConverter.CATEGORY)
@ConverterKeys({"msk", "mask"})
public class MaskingConverter extends LogEventPatternConverter {

    private static final Map<String, LogMasker> OPTIONS_TO_MASKER =
        Map.of("phone", new PhoneMasker(),
            "identifyNumber", new IdentifyNumberMasker(),
            "email", new EmailMasker(),
            "authorization", new AuthorizationMasker(),
            "password", new PasswordMasker(),
            "otp", new OtpMasker()
        );

    private static final List<LogMasker> ALL_MASKERS = OPTIONS_TO_MASKER.values().stream().toList();

    private List<LogMasker> maskers;

    protected MaskingConverter(List<LogMasker> maskers) {
        super("LogMaskerConverter", null);
        this.maskers = maskers;
    }

    public static MaskingConverter newInstance(String[] options) throws Exception {
        if (options == null || options.length == 0) {
            return new MaskingConverter(ALL_MASKERS);
        }

        List<LogMasker> maskers = new ArrayList<>();
        for (String option : options) {
            LogMasker masker = OPTIONS_TO_MASKER.get(option);
            if (masker == null) {
                throw new Exception("Invalid option detected for masker: " + option);
            }
            maskers.add(masker);
        }

        return new MaskingConverter(maskers);
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        StringBuffer logMessage = new StringBuffer(event.getMessage().getFormattedMessage());
        for (LogMasker masker : maskers) {
            logMessage = masker.mask(logMessage, "*");
        }
        toAppendTo.append(logMessage);
    }

}
