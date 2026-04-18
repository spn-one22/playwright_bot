package org.example;

import com.microsoft.playwright.*;
import org.example.core.*;
import org.example.core.fingerprint.*;
import org.example.core.geo.GeoService;
import org.example.core.proxy.ProxyGuard;
import org.example.modules.*;
import org.example.core.profile.*;
import org.example.utils.NetworkTrafficCollector;


import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        AccountProfile profile = ProfileManager.create("spn_one2");

        try {
            ProfileLoader.load(profile);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки профиля", e);
        }

        ProxyGuard.validateOrThrow(profile.proxy);

        // 🌍 1. Определяем GEO по прокси
        profile.geo = GeoService.resolve(profile.proxy.getHost());

        // 🔍 для проверки
        System.out.println("GEO: " + profile.geo.country + " / " + profile.geo.timezone);

        // 🧬 строим fingerprint
        FingerprintSnapshot fp = FingerprintBuilder.build(profile);

        // 🔍 debug
        System.out.println("FP timezone: " + fp.browser.timezone);
        System.out.println("FP locale: " + fp.browser.locale);


        try (Playwright playwright = Playwright.create()) {

            System.out.println("==== PROFILE DEBUG ====");

            if (profile.proxy != null) {
                System.out.println("Proxy type: " + profile.proxy.getType());
                System.out.println("Proxy host: " + profile.proxy.getHost());
                System.out.println("Proxy port: " + profile.proxy.getPort());
            } else {
                System.out.println("Proxy: NULL");
            }

            System.out.println("======================");


            Browser browser = BrowserFactory.create(playwright, profile);
            BrowserContext context = SessionManager.initContext(browser, fp, profile);
            Page page = context.newPage();

            // TRAFFIC MEASURE
            NetworkTrafficCollector traffic = new NetworkTrafficCollector();
            traffic.attach(page);

            // LOGIN
            AuthModule.ensureLogin(page, context, profile);

            ProjectModule project = new ProjectModule(page);

            // 👉 получаем проекты
            List<String> projects = Collections.singletonList(project.getFirstValidProject());
            System.out.println("📦 Найдено проектов: " + projects.size());
            System.out.println("Затрачено времени на поиск: " + (System.currentTimeMillis() - start)/1000 + "секунд");

            // 👉 выводим ссылки
            for (String link : projects) {
                System.out.println(link);
            }

            // 👉 пока просто берём первый
            if (projects.isEmpty()) {
                System.out.println("❌ Нет проектов");
                return;
            }

            String projectUrl = projects.get(0);
            System.out.println("📌 Берём проект: " + projectUrl);

            PageLoader loader = new PageLoader(page, 10000, 1);
            loader.load(projectUrl);

            project.takeTask();

            project.waitForTaskState();

            TaskProcessor processor = new TaskProcessor(page);
            processor.processTask();

            // TRAFFIC INFO LOG
            System.out.println("Requests: " + traffic.getRequestCount());
            System.out.println("Total MB: " + traffic.getTotalMB());
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            long end = System.currentTimeMillis();
            System.out.printf("⏱ Время работы: %.2f секунд%n", (end - start)/1000.0);
        }

    }
}

