package com.gitlab.codedoctorde.api.config;

import com.google.gson.JsonElement;

public abstract class JsonConfigurationElement {
    public abstract JsonElement getElement();

    public abstract Object getObject();
}
