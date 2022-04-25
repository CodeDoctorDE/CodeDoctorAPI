package dev.linwood.api.translation;

import dev.linwood.api.item.ItemStackBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * @author CodeDoctorDE
 */
public final class Translation {
    private final Map<String, String> translations;

    public Translation() {
        this(new HashMap<>());
    }

    public Translation(Map<String, String> translations) {
        this.translations = translations;
    }

    public Translation(@Nullable JsonObject jsonObject) {
        if(jsonObject != null)
            translations = recursiveJsonMap("", jsonObject);
        else
            translations = new HashMap<>();
    }

    private @NotNull Map<String, String> recursiveJsonMap(@NotNull String path, @NotNull JsonObject jsonObject) {
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

    private @NotNull Map<String, String> recursiveJsonArray(@NotNull String path, @NotNull JsonArray jsonArray) {
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
        return Collections.unmodifiableMap(translations);
    }

    public @NotNull Set<String> getTranslationKeys() {
        return translations.keySet();
    }

    public String getTranslation(String key, Object @NotNull ... placeholders) {
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

    public @NotNull Translation subTranslation(String namespace) {
        Map<String, String> map = new HashMap<>();
        translations.forEach((key, value) -> {
            if (key.startsWith(namespace + "."))
                map.put(key.substring((namespace + ".").length()), value);
        });
        return new Translation(map);
    }

    public ItemStackBuilder translate(@NotNull ItemStackBuilder itemStackBuilder, Object... placeholders) {
        itemStackBuilder.setDisplayName(getTranslation(itemStackBuilder.getDisplayName(), placeholders));
        if (!itemStackBuilder.getLore().isEmpty())
            itemStackBuilder.setLore(getTranslation(String.join("", itemStackBuilder.getLore()), placeholders).split("\n"));
        return itemStackBuilder;
    }

    public @NotNull JsonObject toJsonObject() {
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

    public Translation merge(Translation... other) {
        Map<String, String> map = new HashMap<>();
        Arrays.stream(other).forEach(translation -> map.putAll(translation.getTranslations()));
        map.putAll(translations);
        return new Translation(map);
    }

}
