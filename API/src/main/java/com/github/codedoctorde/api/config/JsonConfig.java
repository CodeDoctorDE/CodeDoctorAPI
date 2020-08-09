package com.github.codedoctorde.api.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author CodeDoctorDE
 */
public abstract class JsonConfig {
    Gson gson = new Gson();
    JsonObject jsonObject;

    public abstract void reload();

    public abstract void save();

    public JsonObject getJsonObject(String... path) {
        JsonObject current = jsonObject;
        for (String currentPath:
             path)
            current = current.getAsJsonObject(currentPath);
        return current;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    /**
     * Set default values from the given json object
     * ! Only available if the json element is a json object !
     *
     * @param defaultJsonObject
     */
    public void setDefault(JsonObject defaultJsonObject) {
        for (Map.Entry<String, JsonElement> entry :
                defaultJsonObject.entrySet())
            setDefault(entry.getKey(), entry.getValue());
    }

    public void setDefault(String key, JsonElement value) {
        setDefault(new String[0], key, value);
    }

    /**
     * Set default values
     * ! Only available if the json element is a json object !
     *
     * @param path
     * @param key
     * @param value
     */
    private void setDefault(String[] path, String key, JsonElement value) {
        JsonObject currentObject = jsonObject.getAsJsonObject();
        List<String> nextPath = new ArrayList<>(Arrays.asList(path));
        nextPath.add(key);
        for (String current :
                path) {
            if (currentObject.get(current) == null || currentObject.get(current).isJsonNull())
                currentObject.add(current, new JsonObject());
            currentObject = currentObject.getAsJsonObject(current);
        }
        if (currentObject.get(key) == null || currentObject.get(key).isJsonNull()) currentObject.add(key, value);
        else if (value.isJsonObject()) for (Map.Entry<String, JsonElement> entry :
                value.getAsJsonObject().entrySet())
            setDefault(nextPath.toArray(new String[0]), entry.getKey(), entry.getValue());
    }
}
