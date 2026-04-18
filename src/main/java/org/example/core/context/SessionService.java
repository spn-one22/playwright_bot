package org.example.core.context;

import com.microsoft.playwright.BrowserContext;
import org.example.core.profile.AccountProfile;

import java.nio.file.Files;

public class SessionService {

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