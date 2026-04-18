package org.example.core.geo;

import java.util.Locale;

/**
 * 🌐 GeoMapper
 *
 * Отвечает за преобразование:
 * countryCode → language + locale
 *
 * ❗ НЕ делает HTTP
 * ❗ НЕ знает про API
 * ✔ только маппинг
 */
public class GeoMapper {

    public static String resolveLanguage(String countryCode) {

        Locale locale = new Locale("", countryCode);

        // получаем язык страны (например de для DE)
        return locale.getLanguage().isEmpty()
                ? "en"
                : locale.getLanguage();
    }

    public static String resolveLocale(String countryCode) {

        Locale locale = new Locale("", countryCode);

        String language = resolveLanguage(countryCode);

        // формируем стандартный формат: de-DE
        return language + "-" + countryCode;
    }
}