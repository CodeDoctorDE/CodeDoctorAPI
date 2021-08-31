package dev.linwood.api.config;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author CodeDoctorDE
 */
public abstract class FileConfig {
    private final String filePath;

    public FileConfig(final String filePath) {
        this.filePath = filePath;
        try {
            Files.createDirectories(getParentDirectory());
            if (!Files.exists(getPath()))
                Files.createFile(getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract String getData();

    protected abstract void read(BufferedReader reader);

    public Path getParentDirectory() {
        return getPath().getParent();
    }

    public @NotNull Path getPath() {
        return Paths.get(filePath);
    }

    public String getFilePath() {
        return filePath;
    }

    public void save() {
        try {
            Files.createDirectories(getParentDirectory());
            if (!Files.exists(getPath())) Files.createFile(getPath());
            OutputStreamWriter bw =
                    new OutputStreamWriter(Files.newOutputStream(getPath()), StandardCharsets.UTF_8);
            bw.write(getData());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        try {
            var br = Files.newBufferedReader(getPath(), StandardCharsets.UTF_8);
            read(br);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
