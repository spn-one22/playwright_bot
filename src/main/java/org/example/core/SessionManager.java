package org.example.core;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.Proxy;
import org.example.profile.AccountProfile;

import java.nio.file.Files;
import java.nio.file.Paths;

public class SessionManager {

    public static BrowserContext initContext(Browser browser, AccountProfile profile) {

        Browser.NewContextOptions options = new Browser.NewContextOptions()

                // =========================
                // 🧬 FINGERPRINT (уже готовый)
                // =========================
                .setUserAgent(profile.fingerprint.userAgent)
                .setViewportSize(
                        profile.fingerprint.width,
                        profile.fingerprint.height
                )
                .setLocale(profile.fingerprint.locale)
                .setTimezoneId(profile.fingerprint.timezone)
                .setDeviceScaleFactor(profile.fingerprint.deviceScaleFactor)
                .setIsMobile(profile.fingerprint.isMobile)
                .setHasTouch(profile.fingerprint.hasTouch);

        // =========================
        // 🌐 PROXY
        // =========================
        if (profile.proxy != null) {

            Proxy proxy = new Proxy(
                    "http://" + profile.proxy.host + ":" + profile.proxy.port
            );

            if (profile.proxy.username != null) {
                proxy.setUsername(profile.proxy.username);
                proxy.setPassword(profile.proxy.password);
            }

            options.setProxy(proxy);
        }

        // =========================
        // 🍪 SESSION (если есть)
        // =========================
        if (Files.exists(profile.sessionFile)) {
            options.setStorageStatePath(profile.sessionFile);
        }

        BrowserContext context = browser.newContext(options);

        // =========================
        // 🛡️ BASIC ANTI-DETECT SCRIPT
        // =========================
        context.addInitScript("""
            Object.defineProperty(navigator, 'webdriver', {
                get: () => undefined
            });

            Object.defineProperty(navigator, 'languages', {
                get: () => ['ru-RU', 'ru']
            });

            Object.defineProperty(navigator, 'platform', {
                get: () => '%s'
            });

            Object.defineProperty(navigator, 'plugins', {
                get: () => [1, 2, 3, 4, 5]
            });

            Object.defineProperty(navigator, 'hardwareConcurrency', {
                get: () => %d
            });

            Object.defineProperty(navigator, 'deviceMemory', {
                get: () => %d
            });
        """.formatted(
                profile.fingerprint.platform,
                profile.fingerprint.cpuCores,
                profile.fingerprint.memoryGB
        ));

        return context;
    }

    // =========================
    // 💾 SAVE SESSION
    // =========================
    public static void save(BrowserContext context, AccountProfile profile) {

        try {
            Files.createDirectories(profile.folder);

            context.storageState(
                    new BrowserContext.StorageStateOptions()
                            .setPath(profile.sessionFile)
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to save session", e);
        }
    }
}