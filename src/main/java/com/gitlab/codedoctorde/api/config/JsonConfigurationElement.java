package com.gitlab.codedoctorde.api.config;

import com.google.gson.JsonElement;

public abstract class JsonConfigurationElement {
    public abstract JsonElement getElement();

    public abstract void fromElement(JsonElement element);

    public Object getObject() {
        return this;
    }

    public <T extends JsonConfigurationElement> T toJsonConfigurationElement(T element) {
        element.fromElement(getElement());
        return element;
    }

    public JsonConfigurationSection toConfigSection() {
        return (JsonConfigurationSection) this;
    }

    public JsonConfiguration toConfig() {
        return (JsonConfiguration) this;
    }

    public JsonConfigurationArray toConfigArray() {
        return (JsonConfigurationArray) this;
    }

    public JsonConfigurationValue toConfigValue() {
        return (JsonConfigurationValue) this;
    }
}
