package org.example.core.device.type;

import java.util.Map;

public class DeviceTypeWeights {

    public static Map<DeviceType, Integer> get() {
        return Map.of(
                DeviceType.DESKTOP_WINDOWS, 50,
                DeviceType.DESKTOP_MAC, 15,
                DeviceType.ANDROID_PHONE, 20,
                DeviceType.ANDROID_TABLET, 5,
                DeviceType.IOS_PHONE, 8,
                DeviceType.IOS_TABLET, 2
        );
    }
}