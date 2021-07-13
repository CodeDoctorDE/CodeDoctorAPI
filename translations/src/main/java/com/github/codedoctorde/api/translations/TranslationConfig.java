package com.github.codedoctorde.api.translations;

import com.github.codedoctorde.api.config.JsonConfig;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CodeDoctorDE
 */
public class TranslationConfig extends JsonConfig {
    private Translation instance = new Translation();

    public TranslationConfig(String filePath) {
        super(filePath);
    }

    public TranslationConfig(Gson gson, String filePath) {
        super(gson, filePath);
    }

    public Translation getInstance() {
        return instance;
    }

    public void setInstance(Translation instance) {
        this.instance = instance;
    }

    public String getTranslation(String key, Object... placeholder) {
        return instance.getTranslation(key, placeholder);
    }

    public boolean hasTranslation(String key) {
        return instance.hasTranslation(key);
    }

    public Translation subTranslation(String namespace) {
        return instance.subTranslation(namespace);
    }

    public void setDefault(Translation translation) {
        var map = new HashMap<>(translation.getTranslations());
        for (Map.Entry<String, String> entry : translation.getTranslations().entrySet())
            if (!instance.getTranslationKeys().contains(entry.getKey()))
                map.put(entry.getKey(), entry.getValue());
        instance = new Translation(map);
    }

    @Override
    protected void read(BufferedReader reader) {
        instance = new Translation(getGson().fromJson(reader, JsonObject.class));
    }

    @Override
    public JsonObject getJsonObject() {
        return instance.toJsonObject();
    }

    @Override
    public void setJsonObject(JsonObject jsonObject) {
        instance = new Translation(jsonObject);
    }

    @Override
    protected String getData() {
        return getGson().toJson(instance.toJsonObject());
    }
}
