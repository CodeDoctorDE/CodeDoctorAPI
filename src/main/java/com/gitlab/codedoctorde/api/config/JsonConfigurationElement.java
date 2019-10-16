package com.gitlab.codedoctorde.api.config;

import com.google.gson.JsonElement;

public abstract class JsonConfigurationElement {
    public abstract JsonElement getElement();

    public abstract void fromElement(JsonElement element);

    public abstract Object getObject();

    public <T> T toJsonConfigurationElement(T element) {
        if (element instanceof JsonConfigurationElement)
            ((JsonConfigurationElement) element).fromElement(getElement());
        return element;
    }

    public JsonConfigurationSection toJsonConfigurationSection() {
        return (JsonConfigurationSection) this;
    }

    public JsonConfiguration toJsonConfiguration() {
        return (JsonConfiguration) this;
    }

    public JsonConfigurationArray toJsonConfigurationArray() {
        return (JsonConfigurationArray) this;
    }

    public JsonConfigurationValue toJsonConfigurationValue() {
        return (JsonConfigurationValue) this;
    }
}
