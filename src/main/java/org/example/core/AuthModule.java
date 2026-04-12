package org.example.core;

import com.microsoft.playwright.*;
import org.example.config.*;


public class AuthModule {

    public static void ensureLogin(Page page, BrowserContext context) throws Exception {

        page.navigate(Config.BASE_URL);

        if (page.locator("text=Вход").count() > 0) {
            System.out.println("🔐 Логин вручную и ENTER...");
            System.in.read();

            SessionManager.save(context);
        }
        // System.out.println(page.content());
    }
}