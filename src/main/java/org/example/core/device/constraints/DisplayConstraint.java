package org.example.core.device.constraints;

/**
 * Ограничения дисплея
 */
public interface DisplayConstraint {

    boolean isMobile();

    boolean allowTouch();

    int minWidth();
    int maxWidth();

    int minHeight();
    int maxHeight();
}