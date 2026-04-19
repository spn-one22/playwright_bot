package org.example.core.device.type;

import java.util.Map;
import java.util.Random;

public class DeviceTypeGenerator {

    // 🎲 генератор случайных чисел (лучше чем Math.random)
    private final Random random = new Random();

    // 📦 веса устройств берём из отдельного класса
    private final Map<DeviceType, Integer> weights = DeviceTypeWeights.get();

    public DeviceType generate() {

        // 📊 общий вес всех устройств
        int totalWeight = 0;

        for (int weight : weights.values()) {
            totalWeight += weight;
        }

        // 🎲 случайное число в диапазоне общего веса
        int randomValue = random.nextInt(totalWeight);

        // 📦 накопительный счётчик
        int currentSum = 0;

        // 🔁 проходим по всем устройствам
        for (Map.Entry<DeviceType, Integer> entry : weights.entrySet()) {

            currentSum += entry.getValue();

            // 🎯 если случайное число попало в диапазон — выбираем устройство
            if (randomValue < currentSum) {
                return entry.getKey();
            }
        }

        // 🛑 fallback (теоретически не должен сработать)
        return DeviceType.DESKTOP_WINDOWS;
    }
}