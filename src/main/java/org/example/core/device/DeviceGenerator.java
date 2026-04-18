package org.example.core.device;

public class DeviceGenerator {

    public static DeviceProfile generate() {

        DeviceProfile d = new DeviceProfile();

        // 🖥️ экран
        d.width = 1920;
        d.height = 1080;

        // 📱 тип устройства
        d.isMobile = false;
        d.hasTouch = false;

        // 🔍 масштаб
        d.deviceScaleFactor = 1.0;

        // ⚙️ железо
        d.cpuCores = 8;
        d.memoryGB = 8;

        // 💻 платформа
        d.platform = "Win32";

        return d;
    }
}