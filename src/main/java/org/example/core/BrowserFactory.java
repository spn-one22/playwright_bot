package org.example.core;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.Proxy;
import org.example.core.geo.GeoApplier;
import org.example.core.profile.AccountProfile;

import java.util.List;

public class BrowserFactory {

    public static Browser create(Playwright playwright, AccountProfile profile) {

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(false);

        // GEO
        GeoApplier.applyLaunchArgs(options, profile.geo);

        // 🔥 PROXY
        if (profile.proxy != null) {

            String scheme = profile.proxy.getType().equals("socks5") ? "socks5://" : "http://";

            Proxy proxy = new Proxy(
                    scheme + profile.proxy.getHost() + ":" + profile.proxy.getPort()
            );

            if (profile.proxy.getUsername() != null) {
                proxy.setUsername(profile.proxy.getUsername());
                proxy.setPassword(profile.proxy.getPassword());
            }

            options.setProxy(proxy);

            System.out.println("🌐 Прокси: " + scheme + profile.proxy.getHost() + ":" + profile.proxy.getPort());
        }

        return playwright.chromium().launch(options);
    }
}