package org.example.core.profile;

import org.example.core.geo.GeoData;
import org.example.core.proxy.ProxyData;

import java.nio.file.Path;

public class AccountProfile {

    public String id;

    public Path folder;

    public Path sessionFile;
    public Path proxyFile;

    public ProxyData proxy;
    public GeoData geo;

    public AccountProfile(String id) {
        this.id = id;

        // 📁 папка профиля
        this.folder = Path.of("profiles", id);

        // 📄 файлы
        this.sessionFile = folder.resolve("session.json");
        this.proxyFile = folder.resolve("proxy.json");
    }
}