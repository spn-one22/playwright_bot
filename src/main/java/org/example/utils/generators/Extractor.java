package org.example.utils.generators;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.net.URI;
import java.util.Random;

public class Extractor {

    // ---------------------------
    // 1️⃣ Получить ссылку (основная + fallback)
    // ---------------------------

    public static String extractUrl(Page page) {

        // 🔹 Вариант 1 — основная ссылка
        Locator mainLink = page.locator("a[href*='site/go?url=']").first();

        if (mainLink.count() > 0) {
            String href = mainLink.getAttribute("href");

            if (href != null && href.contains("url=")) {
                String url = href.split("url=")[1];
                System.out.println("✅ Найдена основная ссылка: " + url);
                return url;
            }
        }

        // 🔹 Вариант 2 — список ссылок (fallback)
        Locator links = page.locator(".one_page_url a[href*='site/go?url=']");
        int count = links.count();

        if (count > 0) {
            int randomIndex = new Random().nextInt(count);
            Locator randomLink = links.nth(randomIndex);

            String href = randomLink.getAttribute("href");

            if (href != null && href.contains("url=")) {
                String url = href.split("url=")[1];
                System.out.println("🎲 Выбрана случайная ссылка: " + url);
                return url;
            }
        }

        System.out.println("❌ Ссылки не найдены");
        return null;
    }

    // ---------------------------
    // 2️⃣ Получить чистый домен (base URL)
    // ---------------------------
    public static String extractBaseUrl(String fullUrl) {
        if (fullUrl == null || fullUrl.isEmpty()) {
            return null;
        }

        try {
            // 1️⃣ Убираем всё после запрещённых символов
            String cleaned = fullUrl
                    .replaceAll("[\\s\"'<>].*$|", "")   // пробелы, html мусор
                     .split("\\|")[0]                  // всё после |
                    .trim();

            // 2️⃣ Если нет схемы — добавляем
            if (!cleaned.matches("^(http|https)://.*")) {
                cleaned = "https://" + cleaned;
            }

            // 3️⃣ Пытаемся через URI
            URI uri = new URI(cleaned);

            String scheme = uri.getScheme();
            String host = uri.getHost();

            // 4️⃣ fallback если URI не смог вытащить host
            if (host == null) {
                host = cleaned.replaceAll("https?://([^/]+).*", "$1");
            }

            return scheme + "://" + host + "/";

        } catch (Exception e) {
            System.out.println("⚠️ Failed to parse URL: " + fullUrl);
            return null;
        }
    }

    public static String extractUsername(Page page) {
        Locator el = page.locator("#menuUrl");

        if (el.count() == 0 || !el.isVisible()) {
            return null;
        }

        el.waitFor();

        return el.textContent().trim().replace("_", "");
    }
}
//
//        // ⚠️ Костыль: нет ссылки на сайт заказчика → используем текущий URL задания
//        String currentProjectUrl = page.url();
//        System.out.println("⚠️ Ссылка на сайт заказчика не найдена, используем URL текущего проекта: " + currentProjectUrl);
//        return currentProjectUrl;


/*


public static String extractBaseUrl(String input) {

    // 🔹 Проверка на null — если строка пустая, сразу выходим
    if (input == null) return null;

    try {
        // ---------------------------
        // 1️⃣ ИЩЕМ URL В СТРОКЕ
        // ---------------------------

        */
/*
         regex:
         https?://        → ищем http:// или https://
         [^\\s\"'<>|]+    → дальше берём все символы,
                            пока не встретим:
                            - пробел
                            - кавычки
                            - < >
                            - |

         👉 по сути: "возьми нормальную часть URL и обрежь мусор"
        *//*

        Pattern pattern = Pattern.compile("(https?://[^\\s\"'<>|]+)");

        // создаём matcher для поиска в строке
        Matcher matcher = pattern.matcher(input);

        // если URL не найден — возвращаем null
        if (!matcher.find()) {
            return null;
        }

        // 🔹 берём найденный "чистый" URL (без мусора)
        String cleanUrl = matcher.group(1);

        // ---------------------------
        // 2️⃣ ПАРСИМ URL
        // ---------------------------

        */
/*
         URI разбивает ссылку на части:
         https://site.ru/path

         scheme → https
         host   → site.ru
         path   → /path
        *//*

        URI uri = new URI(cleanUrl);

        // ---------------------------
        // 3️⃣ СОБИРАЕМ BASE URL
        // ---------------------------

        */
/*
         Нам нужен только:
         protocol + host

         👉 https://site.ru/
         *//*

        return uri.getScheme() + "://" + uri.getHost() + "/";

    } catch (Exception e) {

        // если что-то пошло не так (битый URL, странный формат)
        System.out.println("⚠️ Failed to parse: " + input);

        return null;
    }
*/
