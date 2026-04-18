package org.example.core.browser;

import org.example.core.geo.GeoData;

public class BrowserGenerator {

    public static BrowserProfile generate(GeoData geo) {

        BrowserProfile b = new BrowserProfile();

        // 🌐 user-agent
        b.userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/120";

        // 🌍 язык и локаль (из GEO!)
        b.locale = geo.locale;
        b.language = geo.language;

        // 🕒 таймзона (из GEO!)
        b.timezone = geo.timezone;

        return b;
    }
}