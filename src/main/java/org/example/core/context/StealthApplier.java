package org.example.core.context;

import com.microsoft.playwright.BrowserContext;
import org.example.core.fingerprint.FingerprintSnapshot;

public class StealthApplier {

    public static void apply(BrowserContext context, FingerprintSnapshot fp) {

        context.addInitScript(
                """
                // =========================
                // 🚫 AUTOMATION SIGNALS
                // =========================

                Object.defineProperty(navigator, 'webdriver', {
                    get: () => undefined
                });

                // =========================
                // 💻 DEVICE SIGNALS
                // =========================

                Object.defineProperty(navigator, 'platform', {
                    get: () => '%s'
                });

                Object.defineProperty(navigator, 'hardwareConcurrency', {
                    get: () => %d
                });

                Object.defineProperty(navigator, 'deviceMemory', {
                    get: () => %d
                });
                """.formatted(
                        fp.device.platform,
                        fp.device.cpuCores,
                        fp.device.memoryGB
                )
        );
    }
}