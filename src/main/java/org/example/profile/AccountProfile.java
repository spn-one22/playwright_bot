package org.example.profile;

import java.nio.file.Path;

public class AccountProfile {

    public String id;

    public Path folder;

    public Path sessionFile;
    public Path proxyFile;
    public Path fingerprintFile;

    public Fingerprint fingerprint;
    public ProxyData proxy;
}