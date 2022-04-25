package dev.linwood.api.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;

/**
 * @author CodeDoctorDE
 */
public class JsonConfig extends FileConfig {
    protected JsonElement jsonElement = new JsonObject();
    private final Gson gson;

    public JsonConfig(String filePath) {
        super(filePath);
        gson = new Gson();
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

    public JsonElement getJsonElement() {
        return jsonElement;
    }

    @Deprecated
    public JsonObject getJsonObject() {
        return jsonElement.getAsJsonObject();
    }

    @Deprecated
    public void setJsonObject(JsonObject jsonObject) {
        this.jsonElement = jsonObject;
    }

    public void setJsonElement(JsonElement jsonElement) {
        this.jsonElement = jsonElement;
    }
}
