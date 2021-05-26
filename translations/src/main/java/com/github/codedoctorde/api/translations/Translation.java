package com.github.codedoctorde.api.translations;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CodeDoctorDE
 */
public class Translation {
    private final Map<String, String> translations;

    public Translation(Map<String, String> translations) {
        this.translations = translations;
    }

    public String getTranslation(String key, Object... placeholder) {
        if(translations.containsKey(key))
            return String.format(translations.get(key), placeholder);
        return key;
    }

    public boolean hasTranslation(String key) {
        return translations.containsKey(key);
    }

    public Translation subTranslation(String namespace){
        Map<String, String> map = new HashMap<>();
        translations.forEach((key, value) -> {
            if (key.startsWith(namespace + "."))
                map.put(key.substring((namespace + ".").length()), value);
        });
        return new Translation(map);
    }
}
