package dev.linwood.api.translations;

import dev.linwood.api.config.JsonConfig;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CodeDoctorDE
 */
public class TranslationConfig extends JsonConfig {
    private Translation instance;

    public TranslationConfig(String filePath) {
        super(filePath);
    }

    public TranslationConfig(@NotNull Gson gson, String filePath) {
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

    public @NotNull Translation subTranslation(String namespace) {
        return instance.subTranslation(namespace);
    }

    public void setDefault(@NotNull Translation translation) {
        var map = new HashMap<>(instance.getTranslations());
        for (Map.Entry<String, String> entry : translation.getTranslations().entrySet())
            if (!map.containsKey(entry.getKey()))
                map.put(entry.getKey(), entry.getValue());
        instance = new Translation(map);
    }

    @Override
    protected void read(@NotNull BufferedReader reader) {
        var map = getGson().fromJson(reader, JsonObject.class);
        instance = new Translation(map);
    }

    @Override
    public @NotNull JsonObject getJsonObject() {
        return instance.toJsonObject();
    }

    @Override
    public void setJsonObject(@NotNull JsonObject jsonObject) {
        instance = new Translation(jsonObject);
    }

    @Override
    protected String getData() {
        return getGson().toJson(instance.toJsonObject());
    }
}
