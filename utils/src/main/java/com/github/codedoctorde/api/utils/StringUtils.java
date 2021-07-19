package com.github.codedoctorde.api.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public class StringUtils {
    public static @NotNull List<String> wrap(@NotNull String string, int letters) {
        List<String> output = new ArrayList<>();
        output.add("");
        for (String current :
                string.split(" ")) {
            if (output.get(output.size() - 1).length() > letters)
                output.add("");
            output.set(output.size() - 1, output.get(output.size() - 1) + current);
        }
        return output;
    }
}
