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
    protected JsonObject jsonObject = new JsonObject();
    private Gson gson = new Gson();

    public JsonConfig(String filePath) {
        super(filePath);
    }

    public JsonConfig(Gson gson, String filePath) {
        this(filePath);
        this.gson = gson;
    }

    @Override
    protected String getData() {
        return gson.toJson(jsonObject);
    }

    @Override
    protected void read(BufferedReader reader) {
        jsonObject = gson.fromJson(reader, JsonObject.class);
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }
}
