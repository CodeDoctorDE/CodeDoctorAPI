package com.github.codedoctorde.api.translations;

import java.util.Map;

/**
 * @author CodeDoctorDE
 */
public class Translation {
    private final Map<String, String> translations;

    public Translation(Map<String, String> translations) {
        this.translations = translations;
    }

    public String getTranslation(String key) {
        if(translations.containsKey(key))
            return translations.get(key);
        return key;
    }

    public boolean hasTranslation(String key) {
        return translations.containsKey(key);
    }
}
