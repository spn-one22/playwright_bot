package org.example.core.device;

public class DeviceProfile {

    public DeviceType type;

    // screen
    public int width;
    public int height;

    public int availWidth;
    public int availHeight;

    // input
    public boolean isMobile;
    public boolean hasTouch;

    // scaling
    public double deviceScaleFactor;

    // hardware
    public int cpuCores;
    public int memoryGB;

    public String platform;

    // extra signals
    public int colorDepth;
    public String screenOrientation;
}