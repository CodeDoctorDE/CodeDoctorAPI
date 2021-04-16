package com.github.codedoctorde.api.config;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author CodeDoctorDE
 */
public abstract class FileConfig {
    private File file;

    public FileConfig(final File file) {
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
    }

    protected abstract String getData();
    protected abstract void read(BufferedReader reader);

    public void save() {
        file.getParentFile().mkdirs();
        try {
            if (!file.exists()) file.createNewFile();
            OutputStreamWriter bw =
                    new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            bw.write(getData());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        read(br);
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
