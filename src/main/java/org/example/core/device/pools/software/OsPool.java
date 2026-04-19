package org.example.core.device.pools.software;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Пул всех возможных ОС
 */
public class OsPool {

    private final List<OsOption> all;

    public OsPool() {

        this.all = List.of(
                // WINDOWS
                new OsOption("Windows 10", "windows", "10", 0.6),
                new OsOption("Windows 11", "windows", "11", 0.4),

                // ANDROID
                new OsOption("Android 10", "android", "10", 0.2),
                new OsOption("Android 11", "android", "11", 0.3),
                new OsOption("Android 12", "android", "12", 0.3),
                new OsOption("Android 13", "android", "13", 0.2),

                // IOS
                new OsOption("iOS 14", "ios", "14", 0.2),
                new OsOption("iOS 15", "ios", "15", 0.4),
                new OsOption("iOS 16", "ios", "16", 0.4)
        );
    }

    /**
     * Вернуть ВСЕ ОС
     */
    public List<OsOption> getAll() {
        return all;
    }

    /**
     * 🔥 ФИЛЬТРАЦИЯ ПО CONSTRAINT
     */
    public List<OsOption> filterByFamily(List<String> allowedFamilies) {

        return all.stream()
                .filter(os -> allowedFamilies.contains(os.family))
                .collect(Collectors.toList());
    }
}