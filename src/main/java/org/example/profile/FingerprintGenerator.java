package org.example.profile;

public class FingerprintGenerator {

    public static Fingerprint generate() {

        Fingerprint f = new Fingerprint();

        f.userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/120";
        f.width = 1920;
        f.height = 1080;
        f.locale = "ru-RU";
        f.timezone = "Europe/Moscow";
        f.platform = "Win32";

        f.cpuCores = 8;
        f.memoryGB = 8;

        f.isMobile = false;
        f.hasTouch = false;
        f.deviceScaleFactor = 1.0;

        return f;
    }
}