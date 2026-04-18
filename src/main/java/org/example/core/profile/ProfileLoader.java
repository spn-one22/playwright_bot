package org.example.core.profile;

import com.google.gson.Gson;
import org.example.core.geo.*;
import org.example.core.proxy.ProxyData;
import org.example.core.proxy.ProxyValidator;

import java.io.IOException;
import java.nio.file.Files;

public class ProfileLoader {

    private static final Gson gson = new Gson();

    public static void load(AccountProfile profile) throws IOException {

        // =========================
        // 🌐 PROXY
        // =========================
        if (Files.exists(profile.proxyFile)) {

            profile.proxy = gson.fromJson(
                    Files.readString(profile.proxyFile),
                    ProxyData.class
            );

            System.out.println("📂 Proxy загружен");

        } else {

            profile.proxy = null;
            System.out.println("⚠️ Proxy не задан");
        }

        // 🌍 GEO
        String proxyIp = ProxyValidator.getIpThroughProxy(profile.proxy);
        GeoData geo = GeoService.resolve(proxyIp);
        profile.geo = geo;

    }
}