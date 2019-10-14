package com.gitlab.codedoctorde.api.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonConfiguration extends JsonConfigurationSection {
    private File file;

    public JsonConfiguration(final File file) {
        this.file = file;
        file.getParentFile().mkdirs();
        try {
            if (file.exists()) {
                try {
                    FileReader fileReader = new FileReader(file);
                    Gson gson = new Gson();
                    set(gson.fromJson(fileReader, JsonObject.class));
                    save();
                } catch (Exception ignored) {
                }
            } else
                file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() throws IOException {
        file.getParentFile().mkdirs();
        if (!file.exists())
            file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(getElement().toString());
        fileWriter.close();
    }

    public File getFile() {
        return file;
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
