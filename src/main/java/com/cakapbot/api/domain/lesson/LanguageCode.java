package com.cakapbot.api.domain.lesson;

import java.util.Locale;

public enum LanguageCode {
    MS_MY("ms-MY"),
    AR_SA("ar-SA"),
    FR_FR("fr-FR");

    final String code;

    public static LanguageCode fromString(String code) {
        try {
            String cleaned = code.replace("-", "_").toUpperCase(Locale.ROOT);
            return LanguageCode.valueOf(cleaned);
        } catch(IllegalArgumentException e) {
            throw new RuntimeException("Unsupported language"); //todo handle
        }
    }

    LanguageCode(String code) {
        this.code = code;
    }

    public String toString() {
        return code;
    }
}
