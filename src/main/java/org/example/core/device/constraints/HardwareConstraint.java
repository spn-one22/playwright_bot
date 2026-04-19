package org.example.core.device.constraints;

/**
 * Ограничения железа
 */
public interface HardwareConstraint {

    int minCpuCores();
    int maxCpuCores();

    int minMemoryGb();
    int maxMemoryGb();

    boolean allowIntegratedGpu();
    boolean allowDedicatedGpu();
}