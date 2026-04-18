package org.example.core.geo;

/**
 * 🌐 DTO для ответа от ip-api.com
 * Используется только внутри GeoService
 */
public class IpApiResponse {

    public String status;

    public String country;
    public String countryCode;

    public String region;
    public String regionName;

    public String city;

    public double lat;
    public double lon;

    public String timezone;
}