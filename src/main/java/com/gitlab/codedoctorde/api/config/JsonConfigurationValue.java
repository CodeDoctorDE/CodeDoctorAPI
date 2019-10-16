package com.gitlab.codedoctorde.api.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class JsonConfigurationValue extends JsonConfigurationElement {
    private JsonPrimitive value;

    public JsonConfigurationValue(String value) {
        this.value = new JsonPrimitive(value);
    }

    public JsonConfigurationValue(Integer value) {
        this.value = new JsonPrimitive(value);
    }

    public JsonConfigurationValue(Float value) {
        this.value = new JsonPrimitive(value);
    }

    public JsonConfigurationValue(Double value) {
        this.value = new JsonPrimitive(value);
    }

    public JsonConfigurationValue(Boolean value) {
        this.value = new JsonPrimitive(value);
    }

    public JsonConfigurationValue(Byte value) {
        this.value = new JsonPrimitive(value);
    }

    public JsonConfigurationValue(Long value) {
        this.value = new JsonPrimitive(value);
    }

    public JsonConfigurationValue(Character value) {
        this.value = new JsonPrimitive(value);
    }

    public JsonConfigurationValue(final JsonPrimitive value) {
        this.value = value;
    }

    public String getString() {
        return value.getAsString();
    }

    public int getInteger() {
        return value.getAsInt();
    }

    public boolean getBoolean() {
        return value.getAsBoolean();
    }

    public float getFloat() {
        return value.getAsFloat();
    }

    public double getDouble() {
        return value.getAsDouble();
    }

    public char getCharacter() {
        return value.getAsCharacter();
    }

    public byte getByte() {
        return value.getAsByte();
    }

    public long getLong() {
        return value.getAsLong();
    }

    @Override
    public JsonElement getElement() {
        return value;
    }

    @Override
    public void fromElement(JsonElement element) {
        value = element.getAsJsonPrimitive();
    }

    @Override
    public Object getObject() {
        return value;
    }
}
