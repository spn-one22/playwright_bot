package org.example.core.proxy;

/**
 * ProxyGuard — отвечает за валидацию прокси перед запуском браузера.
 *
 * ❗ Функция:
 * - проверяет, жив ли прокси
 * - проверяет, не течёт ли реальный IP
 * - блокирует запуск при ошибке
 */
public class ProxyGuard {

    /**
     * Проверяет прокси и кидает исключение, если он плохой
     */
    public static void validateOrThrow(ProxyData proxy) {

        // 1. получаем реальный IP (без прокси)
        String realIp = RealIpService.getRealIp();

        // 2. получаем IP через прокси
        String proxyIp = ProxyValidator.getIpThroughProxy(proxy);

        // 3. проверка: прокси вообще жив?
        if (proxyIp == null) {
            throw new RuntimeException("❌ Proxy is DEAD");
        }

        // 4. проверка: нет ли утечки IP
        if (proxyIp.equals(realIp)) {
            throw new RuntimeException("🚨 Proxy LEAK detected (real IP exposed)");
        }

        // 5. успех
        System.out.println("✅ Proxy is VALID");
        System.out.println("🌍 Real IP: " + realIp);
        System.out.println("🧩 Proxy IP: " + proxyIp);
    }
}