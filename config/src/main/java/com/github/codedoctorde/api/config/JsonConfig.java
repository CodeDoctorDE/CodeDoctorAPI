package com.github.codedoctorde.api.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CodeDoctorDE
 */
public class JsonConfig extends FileConfig {
    protected JsonObject jsonObject;
    private Gson gson = new Gson();

    public JsonConfig(File file) {
        super(file);
    }

    public JsonConfig(Gson gson, File file) {
        this(file);
        this.gson = gson;
    }

    @Override
    protected String getData() {
        return jsonObject.toString();
    }

    @Override
    protected void read(BufferedReader reader) {
        jsonObject = gson.fromJson(reader, JsonObject.class);
    }

    public JsonElement getJsonElement(String path) {
        JsonElement current = jsonObject;
        for (String currentPath :
                path.split("\\."))
            current = current.getAsJsonObject().get(currentPath);
        return current;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public JsonObject createObject(String path) {
        JsonObject current = jsonObject;
        for (String currentPath : path.split("\\.")) {
            if (!current.has(currentPath))
                current.add(currentPath, new JsonObject());
            current = current.getAsJsonObject(currentPath);
        }
        return current;
    }

    public void setObject(String path, JsonElement value) {
        String[] paths = path.split("\\.");
        JsonObject namespace = createObject(String.join(".", Arrays.copyOfRange(paths, 0, paths.length - 1))).getAsJsonObject();
        namespace.add(paths[paths.length - 1], value);
    }

    public boolean has(String path) {
        JsonElement current = jsonObject;
        for (String currentPath :
                path.split("\\.")) {
            if (current.isJsonObject())
                if (current.getAsJsonObject().has(currentPath))
                    current = current.getAsJsonObject().get(currentPath);
                else
                    return false;
        }
        return true;
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

    /**
     * Set default values
     * ! Only available if the json element is a json object !
     *
     * @param key
     * @param value
     */
    private void setDefault(String key, JsonElement value) {
        if (!has(key))
            setObject(key, value);
    }

    public Map<String, JsonElement> getValues() {
        return getValues("", jsonObject);
    }

    public Map<String, JsonElement> getValues(String path, JsonObject jsonObject) {
        Map<String, JsonElement> map = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String currentPath = path + "." + entry.getKey();
            if (entry.getValue().isJsonObject())
                map.putAll(getValues(currentPath, entry.getValue().getAsJsonObject()));
            else
                map.put(currentPath, entry.getValue());
        }
        return map;
    }
}
