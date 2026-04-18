package org.example.core.geo;

/**
 * 🌍 GeoData
 *
 * Финальный результат гео-резолва.
 * ❗ НЕ содержит логики — только данные
 */
public class GeoData {

    // 🌍 location
    public String country;   // DE
    public String region;    // Hesse
    public String city;      // Frankfurt

    // 📍 coordinates
    public double latitude;
    public double longitude;

    // 🕒 time
    public String timezone;

    // 🌐 browser identity
    public String locale;    // de-DE
    public String language;  // de

    // 🔥 derived (готовый массив для navigator.languages)
    public String[] languages;
}