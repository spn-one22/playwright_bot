package org.example.core.profile;

import java.nio.file.Files;

public class ProfileManager {

    public static AccountProfile create(String id) {

        AccountProfile profile = new AccountProfile(id);

        // создаём папку профиля
        try {
            Files.createDirectories(profile.folder);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось создать папку профиля", e);
        }

        return profile;
    }

    public static boolean hasSession(AccountProfile profile) {
        return profile.sessionFile.toFile().exists();
    }
}