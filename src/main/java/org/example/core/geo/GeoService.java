package org.example.core.geo;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 🌍 GeoService
 *
 * Получает GeoData по IP через внешний API
 */
public class GeoService {

    private static final Gson gson = new Gson();

    /**
     * 🔥 Основной метод
     */
    public static GeoData resolve(String ip) {

        IpApiResponse response = fetch(ip);

        if (response == null || !"success".equals(response.status)) {
            throw new RuntimeException("❌ Geo lookup failed for IP: " + ip);
        }

        return mapToGeoData(response);
    }

    /**
     * 🌐 HTTP запрос к ip-api
     */
    private static IpApiResponse fetch(String ip) {

        try {
            URL url = new URL("http://ip-api.com/json/" + ip);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.setConnectTimeout(3000); // максимум 3 сек на подключение
            conn.setReadTimeout(3000);    // максимум 3 сек на чтение ответа

            return gson.fromJson(
                    new InputStreamReader(conn.getInputStream()),
                    IpApiResponse.class
            );

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to fetch geo for IP: " + ip, e);
        }
    }

    /**
     * 🔄 Маппинг API → GeoData
     */
    private static GeoData mapToGeoData(IpApiResponse r) {

        GeoData geo = new GeoData();

        // =========================
        // 🌍 LOCATION
        // =========================
        geo.country = r.countryCode;
        geo.region = r.regionName;
        geo.city = r.city;

        // =========================
        // 📍 COORDINATES
        // =========================
        geo.latitude = r.lat;
        geo.longitude = r.lon;

        // =========================
        // 🕒 TIMEZONE
        // =========================
        geo.timezone = r.timezone;

        // =========================
        // 🌐 LANGUAGE
        // =========================
        geo.language = GeoMapper.resolveLanguage(r.countryCode);
        geo.locale = GeoMapper.resolveLocale(r.countryCode);

        geo.languages = new String[]{geo.locale, geo.language};

        return geo;
    }
}