package org.example.profile;

public class ProfileLoader {

    public static void load(AccountProfile profile) {

        // временно просто создаём fingerprint
        if (profile.fingerprint == null) {
            profile.fingerprint = FingerprintGenerator.generate();
        }
    }
}