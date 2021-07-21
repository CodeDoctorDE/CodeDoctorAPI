package com.github.codedoctorde.api.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;

/**
 * @author CodeDoctorDE
 */
public class JsonConfig extends FileConfig {
    protected JsonObject jsonObject = new JsonObject();
    private Gson gson = new GsonBuilder().create();

    public JsonConfig(String filePath) {
        super(filePath);
    }

    public JsonConfig(@NotNull Gson gson, String filePath) {
        super(filePath);
        this.gson = gson;
        reload();
    }

    @Override
    protected String getData() {
        return gson.toJson(getJsonObject());
    }

    @Override
    protected void read(@NotNull BufferedReader reader) {
        setJsonObject(gson.fromJson(reader, JsonObject.class));
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
