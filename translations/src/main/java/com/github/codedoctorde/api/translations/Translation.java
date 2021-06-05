package com.github.codedoctorde.api.translations;

import com.github.codedoctorde.api.utils.ItemStackBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CodeDoctorDE
 */
public class Translation {
    private final Map<String, String> translations;

    public Translation() {
        this(new HashMap<>());
    }

    public Translation(Map<String, String> translations) {
        this.translations = translations;
    }

    public String getTranslation(String key, Object... placeholders) {
        if (translations.containsKey(key))
            return String.format(translations.get(key), placeholders);
        return key;
    }

    public boolean hasTranslation(String key) {
        return translations.containsKey(key);
    }

    public Translation subTranslation(String namespace) {
        Map<String, String> map = new HashMap<>();
        translations.forEach((key, value) -> {
            if (key.startsWith(namespace + "."))
                map.put(key.substring((namespace + ".").length()), value);
        });
        return new Translation(map);
    }

    public void translate(ItemStackBuilder itemStackBuilder, Object... placeholders) {
        itemStackBuilder.setDisplayName(getTranslation(itemStackBuilder.getDisplayName(), placeholders));
        itemStackBuilder.setLore(getTranslation(String.join("\n", itemStackBuilder.getLore()), placeholders));
    }
}
