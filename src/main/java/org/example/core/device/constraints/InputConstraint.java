package org.example.core.device.constraints;

/**
 * Ограничения ввода
 */
public interface InputConstraint {

    boolean hasMouse();
    boolean hasKeyboard();
    boolean hasTouch();
}