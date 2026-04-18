package org.example.core.context;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import org.example.core.fingerprint.FingerprintSnapshot;
import org.example.core.profile.AccountProfile;
import org.example.core.geo.GeoApplier;

import java.nio.file.Files;

public class ContextFactory {

    public static BrowserContext create(
            Browser browser,
            FingerprintSnapshot fp,
            AccountProfile profile
    ) {

        Browser.NewContextOptions options = new Browser.NewContextOptions()
                .setUserAgent(fp.browser.userAgent)
                .setViewportSize(fp.device.width, fp.device.height)
                .setDeviceScaleFactor(fp.device.deviceScaleFactor)
                .setIsMobile(fp.device.isMobile)
                .setHasTouch(fp.device.hasTouch);


        GeoApplier.applyContext(options, fp.geo);

        if (Files.exists(profile.sessionFile)) {
            options.setStorageStatePath(profile.sessionFile);
        }

        BrowserContext context = browser.newContext(options);

        GeoApplier.applyNavigatorJs(context, fp.geo);
        StealthApplier.apply(context, fp);

        return browser.newContext(options);
    }
}