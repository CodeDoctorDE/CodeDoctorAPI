package com.github.codedoctorde.api.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public class StringUtils {
    public static List<String> wrap(String string, int letters) {
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
