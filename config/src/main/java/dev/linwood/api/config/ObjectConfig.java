package dev.linwood.api.config;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

public class ObjectConfig<T> extends JsonConfig {
    private final Class<T> type;

    public ObjectConfig(Class<T> type, String filePath) {
        super(filePath);
        this.type = type;
    }

    public ObjectConfig(Class<T> type, @NotNull Gson gson, String filePath) {
        super(gson, filePath);
        this.type = type;
    }

    public T getObject() {
        return getGson().fromJson(getJsonElement(), type);
    }

    public void setObject(T object) {
        setJsonElement(getGson().toJsonTree(object));
    }
}
