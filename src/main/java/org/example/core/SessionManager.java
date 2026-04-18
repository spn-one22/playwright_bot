package org.example.core;

import com.microsoft.playwright.*;

import org.example.core.context.ContextFactory;
import org.example.core.context.SessionService;
import org.example.core.context.StealthApplier;
import org.example.core.profile.*;
import org.example.core.fingerprint.*;

/**
 * Фасад для работы с сессией и контекстом.
 * НЕ содержит логики — только делегирует.
 */
public class SessionManager {

    public static BrowserContext initContext(
            Browser browser,
            FingerprintSnapshot fp,
            AccountProfile profile
    ) {

        BrowserContext context = ContextFactory.create(browser, fp, profile);

        StealthApplier.apply(context, fp);

        return context;
    }

    public static void save(BrowserContext context, AccountProfile profile) {
        SessionService.save(context, profile);
    }
}