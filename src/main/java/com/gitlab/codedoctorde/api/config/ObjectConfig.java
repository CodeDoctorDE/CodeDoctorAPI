package com.gitlab.codedoctorde.api.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author CodeDoctorDE
 */
public class ObjectConfig {
    private File file;
    private Gson gson = new Gson();
    private JsonObject jsonObject;

    public ObjectConfig(final Gson gson, final File file) {
        this.gson = gson;
        this.file = file;
        file.getParentFile().mkdirs();
        try {
            if (file.exists()) {
                try {
                    reload();
                    save();
                } catch (Exception ignored) {
                }
            } else
                file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (jsonObject == null)
            jsonObject = new JsonObject();
    }

    public ObjectConfig(final File file) {
        this.file = file;
        file.getParentFile().mkdirs();
        try {
            if (file.exists()) {
                try {
                    reload();
                    save();
                } catch (Exception ignored) {
                }
            } else
                file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (jsonObject == null)
            jsonObject = new JsonObject();
    }

    public void save() throws IOException {
        file.getParentFile().mkdirs();
        if (!file.exists())
            file.createNewFile();
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
            bw.write(gson.toJson(jsonObject));
            //code
        }

    }
    public File getFile() {
        return file;
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public void reload() throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        jsonObject = gson.fromJson(br, JsonObject.class);
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public void setDefault(JsonObject defaultJsonObject) {
        for (Map.Entry<String, JsonElement> entry :
                defaultJsonObject.entrySet())
            setDefault(entry.getKey(), entry.getValue());
    }

    public void setDefault(String key, JsonElement value) {
        setDefault(new String[0], key, value);
    }

    private void setDefault(String[] path, String key, JsonElement value) {
        JsonObject currentObject = jsonObject;
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

    /*public void importValueSections(Object value,String name,String... keys){
        JsonConfigurationSection section = getSection(keys);
        section.importValue(name,value);
    }
    public void importValuesSections(HashMap<List<String>,Object> defaultValues){
        for (List<String> key:
                defaultValues.keySet()) {
            String name = key.get(key.size()-1);
            List<String> path = new ArrayList<>(key);
            path.remove(path.size()-1);
            importValueSections(defaultValues.get(key), name, path.toArray(new String[0]));
        }
    }*/
}
