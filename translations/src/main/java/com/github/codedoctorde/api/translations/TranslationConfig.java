package com.github.codedoctorde.api.translations;

import com.github.codedoctorde.api.config.JsonConfig;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CodeDoctorDE
 */
public class TranslationConfig extends JsonConfig {
    private Translation instance = new Translation();

    public TranslationConfig(File file) {
        super(file);
    }

    public TranslationConfig(Gson gson, File file) {
        super(gson, file);
    }

    private void reloadTranslation() {
        instance = new Translation(jsonObject);
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
    public void reload() {
        super.reload();
        reloadTranslation();
    }
}
