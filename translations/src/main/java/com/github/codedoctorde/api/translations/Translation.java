package com.github.codedoctorde.api.translations;

import com.github.codedoctorde.api.utils.ItemStackBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Arrays;
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
        translations = recursiveJsonMap("", jsonObject);
    }

    private Map<String, String> recursiveJsonMap(String path, JsonObject jsonObject) {
        Map<String, String> map = new HashMap<>();
        String prefix = path + (path.isBlank() ? "" : ".");
        jsonObject.entrySet().forEach(entry -> {
            if (entry.getValue().isJsonObject())
                map.putAll(recursiveJsonMap(prefix + entry.getKey(), entry.getValue().getAsJsonObject()));
            else if (entry.getValue().isJsonArray())
                map.putAll(recursiveJsonArray(prefix + entry.getKey(), entry.getValue().getAsJsonArray()));
            else if (entry.getValue().isJsonPrimitive())
                map.put(prefix + entry.getKey(), entry.getValue().getAsString());
        });
        return map;
    }

    private Map<String, String> recursiveJsonArray(String path, JsonArray jsonArray) {
        Map<String, String> map = new HashMap<>();
        String prefix = path + (path.isBlank() ? "" : ".");
        for (int i = 0; i < jsonArray.size(); i++) {
            var jsonElement = jsonArray.get(i);
            if (jsonElement.isJsonObject())
                map.putAll(recursiveJsonMap(prefix + i, jsonElement.getAsJsonObject()));
            else if (jsonElement.isJsonArray())
                map.putAll(recursiveJsonArray(prefix + i, jsonElement.getAsJsonArray()));
            else if (jsonElement.isJsonPrimitive())
                map.put(prefix + i, jsonElement.getAsString());
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
            if (placeholders.length == 0)
                return translations.get(key);
            else
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
        if (!itemStackBuilder.getLore().isEmpty())
            itemStackBuilder.setLore(getTranslation(String.join("", itemStackBuilder.getLore()), placeholders).split("\n"));
    }

    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        translations.forEach((key, value) -> {
            String[] path = key.split("\\.");
            if (path.length == 0)
                return;
            String[] namespace = Arrays.copyOfRange(path, 0, path.length - 1);
            JsonObject currentJsonObject = jsonObject;
            for (String current : namespace) {
                if (!currentJsonObject.has(current) || !currentJsonObject.get(current).isJsonObject())
                    currentJsonObject.add(current, new JsonObject());
                currentJsonObject = currentJsonObject.getAsJsonObject(current);
            }
            currentJsonObject.addProperty(path[path.length - 1], value);
        });
        return jsonObject;
    }

}
