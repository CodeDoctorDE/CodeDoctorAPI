package com.github.codedoctorde.api.translations;

import com.github.codedoctorde.api.utils.ItemStackBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author CodeDoctorDE
 */
public class Translation {
    private final Map<String, String> translations;

    public Translation() {
        this(new HashMap<>());
    }

    public Translation(Map<String, String> translations) {
        this.translations = translations;
    }

    public Translation(JsonObject jsonObject) {
        translations = recursiveJsonMap(jsonObject, "");
    }

    private Map<String, String> recursiveJsonMap(JsonObject jsonObject, String path) {
        Map<String, String> map = new HashMap<>();
        String prefix = path + (path.isBlank() ? "" : ".");
        jsonObject.entrySet().forEach(entry -> {
            if(entry.getValue().isJsonObject())
                map.putAll(recursiveJsonMap(entry.getValue().getAsJsonObject(), prefix + entry.getKey()));
            else if(entry.getValue().isJsonArray())
                map.putAll(recursiveJsonArray(entry.getValue().getAsJsonArray(), prefix + entry.getKey()));
            else if(entry.getValue().isJsonPrimitive())
                map.put(entry.getValue().getAsString(), prefix + entry.getKey());
        });
        return map;
    }

    private Map<String, String> recursiveJsonArray(JsonArray jsonArray, String path) {
        Map<String, String> map = new HashMap<>();
        String prefix = path + (path.isBlank() ? "" : ".");
        for (int i = 0; i < jsonArray.size(); i++) {
            var jsonElement = jsonArray.get(i);
            if(jsonElement.isJsonObject())
                map.putAll(recursiveJsonMap(jsonElement.getAsJsonObject(), prefix + i));
            else if(jsonElement.isJsonArray())
                map.putAll(recursiveJsonArray(jsonElement.getAsJsonArray(), prefix + i));
            else if(jsonElement.isJsonPrimitive())
                map.put(jsonElement.getAsString(), prefix + i);
        }
        return map;
    }

    public Map<String, String> getTranslations() {
        return translations;
    }

    public Set<String> getTranslationKeys() {
        return translations.keySet();
    }

    public String getTranslation(String key, Object... placeholders) {
        if (translations.containsKey(key))
            return String.format(translations.get(key), placeholders);
        return key;
    }

    public boolean hasTranslation(String key) {
        return translations.containsKey(key);
    }

    public Translation subTranslation(String namespace) {
        Map<String, String> map = new HashMap<>();
        translations.forEach((key, value) -> {
            if (key.startsWith(namespace + "."))
                map.put(key.substring((namespace + ".").length()), value);
        });
        return new Translation(map);
    }

    public void translate(ItemStackBuilder itemStackBuilder, Object... placeholders) {
        itemStackBuilder.setDisplayName(getTranslation(itemStackBuilder.getDisplayName(), placeholders));
        if(!itemStackBuilder.getLore().isEmpty())
        itemStackBuilder.setLore(getTranslation(String.join("\n", itemStackBuilder.getLore()), placeholders));
    }
}
