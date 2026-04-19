package org.example.core.device.constraints;

public interface DeviceConstraint {
    HardwareConstraint hardware();
    DisplayConstraint display();
    SoftwareConstraint software();
    InputConstraint input();
}
