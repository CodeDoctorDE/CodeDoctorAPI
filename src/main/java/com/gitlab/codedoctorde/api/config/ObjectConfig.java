package com.gitlab.codedoctorde.api.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author CodeDoctorDE
 */
public class ObjectConfig extends JsonConfig {
    private File file;

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

    public void save() {
        file.getParentFile().mkdirs();
        try {
            if (!file.exists()) file.createNewFile();
            OutputStreamWriter bw =
                    new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            bw.write(gson.toJson(jsonObject));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public File getFile() {
        return file;
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public void reload() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert br != null;
        jsonObject = gson.fromJson(br, JsonObject.class);
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFile(File file) {
        this.file = file;
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
