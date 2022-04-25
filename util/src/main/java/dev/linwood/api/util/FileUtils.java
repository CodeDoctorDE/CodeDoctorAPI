package dev.linwood.api.util;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class FileUtils {
    public static @NotNull String getFileName(@NotNull Path path) {
        var pathName = path.toString();
        pathName = pathName.replace("\\", "/");
        var pos = pathName.lastIndexOf('.');
        if (pos > 0) return pathName.substring(0, pos);
        return "";
    }
}
