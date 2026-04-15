package org.example.profile;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;

public class ProfileLoader {

    private static final Gson gson = new Gson();

    public static void load(AccountProfile profile) throws IOException {

        try {
            // =========================
            // 🧬 FINGERPRINT
            // =========================
            if (Files.exists(profile.fingerprintFile)) {

                // 👉 читаем из файла
                profile.fingerprint = gson.fromJson(
                        Files.readString(profile.fingerprintFile),
                        Fingerprint.class
                );

                System.out.println("📂 Fingerprint загружен");

            } else {

                // 👉 создаём новый
                profile.fingerprint = FingerprintGenerator.generate();

                // 👉 сохраняем
                Files.writeString(
                        profile.fingerprintFile,
                        gson.toJson(profile.fingerprint)
                );

                System.out.println("🆕 Fingerprint создан");
            }

        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки профиля", e);
        }
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

            // 👉 пока просто null (или можешь захардкодить тестовый)
            profile.proxy = null;

            System.out.println("⚠️ Proxy не задан");
        }
    }
}