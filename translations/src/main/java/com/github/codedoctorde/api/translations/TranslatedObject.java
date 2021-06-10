package com.github.codedoctorde.api.translations;

public interface TranslatedObject {
    Translation getTranslation();
    Object[] getPlaceholders();
    void setPlaceholders(Object... placeholders);
}
