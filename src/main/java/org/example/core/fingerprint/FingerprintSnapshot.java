package org.example.core.fingerprint;

import org.example.core.geo.GeoData;
import org.example.core.device.DeviceProfile;
import org.example.core.browser.BrowserProfile;

public class FingerprintSnapshot {

    public GeoData geo;
    public DeviceProfile device;
    public BrowserProfile browser;

    // 👉 конструктор
    public FingerprintSnapshot(
            GeoData geo,
            DeviceProfile device,
            BrowserProfile browser
    ) {
        this.geo = geo;
        this.device = device;
        this.browser = browser;
    }
}