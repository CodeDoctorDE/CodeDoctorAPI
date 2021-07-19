package com.github.codedoctorde.api.utils;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author CodeDoctorDE
 */
public class NumberManager {
    private final float defaultValue;
    private float value;
    private float steps = 1;
    private float fastSteps = 5;

    public NumberManager(float defaultValue) {
        this.defaultValue = defaultValue;
        value = defaultValue;
    }

    public NumberManager() {
        this(1);
    }

    public float getDefaultValue() {
        return defaultValue;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getFastSteps() {
        return fastSteps;
    }

    public void setFastSteps(float fastSteps) {
        this.fastSteps = fastSteps;
    }

    public float getSteps() {
        return steps;
    }

    public void setSteps(float steps) {
        this.steps = steps;
    }

    public void next() {
        nextCustom(steps);
    }

    public void nextFast() {
        nextCustom(fastSteps);
    }

    public void nextCustom(float number) {
        value += number;
    }

    public void previous() {
        previousCustom(steps);
    }

    public void previousFast() {
        previousCustom(fastSteps);
    }

    public void previousCustom(float number) {
        value -= number;
    }

    public void reset() {
        value = defaultValue;
    }

    public void handleEvent(@NotNull InventoryClickEvent event) {
        handleClick(event.getClick());
    }

    public void handleClick(@NotNull ClickType type) {
        switch (type) {
            case LEFT:
                next();
                break;
            case SHIFT_LEFT:
                nextFast();
                break;
            case RIGHT:
                previous();
                break;
            case SHIFT_RIGHT:
                previousFast();
                break;
            case DROP:
                reset();
                break;
        }
    }
}
