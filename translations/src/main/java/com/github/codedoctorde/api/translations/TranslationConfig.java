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
    private Translation translation;

    public TranslationConfig(File file) {
        super(file);
    }
    public TranslationConfig(Gson gson, File file) {
        super(gson, file);
    }

    private void reloadTranslation(){
        Map<String, String> map = new HashMap<>();
        for(Map.Entry<String, JsonElement> entry : getValues().entrySet()){
            map.put(entry.getKey(), entry.getValue().getAsString());
        }
        translation = new Translation(map);
    }

    public Translation getTranslation() {
        return translation;
    }

    public String getTranslation(String key, Object... placeholder) {
        return translation.getTranslation(key, placeholder);
    }

    public boolean hasTranslation(String key) {
        return translation.hasTranslation(key);
    }

    public Translation subTranslation(String namespace){
        return translation.subTranslation(namespace);
    }

    @Override
    public void reload() {
        super.reload();
        reloadTranslation();
    }
}
