package org.example.core.device.pools.software;

/**
 * Описание одной ОС
 */
public class OsOption {

    public final String name;       // Windows 10
    public final String family;     // windows / android / ios
    public final String version;    // 10, 11, 13, etc.

    public final double weight;     // вероятность выбора

    public OsOption(String name, String family, String version, double weight) {
        this.name = name;
        this.family = family;
        this.version = version;
        this.weight = weight;
    }
}