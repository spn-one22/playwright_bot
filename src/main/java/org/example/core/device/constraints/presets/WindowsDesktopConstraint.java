package org.example.core.device.constraints.presets;

import org.example.core.device.constraints.*;

import java.util.List;

/**
 * Ограничения для Windows Desktop устройства
 */
public class WindowsDesktopConstraint implements DeviceConstraint {

    // =========================
    // HARDWARE
    // =========================

    private final HardwareConstraint hardware = new HardwareConstraint() {
        @Override
        public int minCpuCores() {
            return 4;
        }

        @Override
        public int maxCpuCores() {
            return 16;
        }

        @Override
        public int minMemoryGb() {
            return 4;
        }

        @Override
        public int maxMemoryGb() {
            return 32;
        }

        @Override
        public boolean allowIntegratedGpu() {
            return true;
        }

        @Override
        public boolean allowDedicatedGpu() {
            return true;
        }
    };

    // =========================
    // DISPLAY
    // =========================

    private final DisplayConstraint display = new DisplayConstraint() {
        @Override
        public boolean isMobile() {
            return false;
        }

        @Override
        public boolean allowTouch() {
            return false;
        }

        @Override
        public int minWidth() {
            return 1280;
        }

        @Override
        public int maxWidth() {
            return 2560;
        }

        @Override
        public int minHeight() {
            return 720;
        }

        @Override
        public int maxHeight() {
            return 1440;
        }
    };

    // =========================
    // SOFTWARE
    // =========================

    private final SoftwareConstraint software = new SoftwareConstraint() {
        @Override
        public List<String> allowedOsFamilies() {
            return List.of("windows");
        }

        @Override
        public List<String> allowedPlatforms() {
            return List.of("Win32");
        }
    };

    // =========================
    // INPUT
    // =========================

    private final InputConstraint input = new InputConstraint() {
        @Override
        public boolean hasMouse() {
            return true;
        }

        @Override
        public boolean hasKeyboard() {
            return true;
        }

        @Override
        public boolean hasTouch() {
            return false;
        }
    };

    // =========================
    // GETTERS
    // =========================

    @Override
    public HardwareConstraint hardware() {
        return hardware;
    }

    @Override
    public DisplayConstraint display() {
        return display;
    }

    @Override
    public SoftwareConstraint software() {
        return software;
    }

    @Override
    public InputConstraint input() {
        return input;
    }
}