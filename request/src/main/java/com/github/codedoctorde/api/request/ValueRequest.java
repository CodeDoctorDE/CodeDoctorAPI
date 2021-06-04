package com.github.codedoctorde.api.request;

import org.bukkit.entity.Player;

/**
 * @author CodeDoctorDE
 */
public class ValueRequest extends Request<Float> {
    private final float defaultValue;
    private float fastSteps = 5;
    private float steps = 1;
    private float value;

    public ValueRequest(final Player player, float initialValue) {
        super(player);
        value = initialValue;
        defaultValue = value;
    }

    public ValueRequest(final Player player) {
        this(player, 0);
    }

    public float getSteps() {
        return steps;
    }

    public void setSteps(float steps) {
        this.steps = steps;
    }

    public float getFastSteps() {
        return fastSteps;
    }

    public void setFastSteps(float fastSteps) {
        this.fastSteps = fastSteps;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getDefaultValue() {
        return defaultValue;
    }

    public void submit() {
        raise(value);
    }
}
