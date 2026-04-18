package org.example.core.fingerprint;

import org.example.core.device.DeviceGenerator;
import org.example.core.browser.BrowserGenerator;
import org.example.core.profile.AccountProfile;
import org.example.core.browser.BrowserProfile;
import org.example.core.device.DeviceProfile;
import org.example.core.geo.GeoData;

public class FingerprintBuilder {

    public static FingerprintSnapshot build(AccountProfile profile) {

        // 1. GEO
        GeoData geo = profile.geo;

        // 2. DEVICE
        DeviceProfile device = DeviceGenerator.generate();

        // 3. BROWSER
        BrowserProfile browser = BrowserGenerator.generate(geo);

        // 4. сборка
        return new FingerprintSnapshot(geo, device, browser);
    }
}