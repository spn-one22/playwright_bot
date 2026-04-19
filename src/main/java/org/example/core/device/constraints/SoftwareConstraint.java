package org.example.core.device.constraints;

import java.util.List;

/**
 * Ограничения ПО
 */
public interface SoftwareConstraint {

    List<String> allowedOsFamilies(); // windows, android, ios

    List<String> allowedPlatforms(); // Win32, MacIntel, Linux arm
}