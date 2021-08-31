package dev.linwood.api.translations;

public interface TranslatedObject {
    Translation getTranslation();

    Object[] getPlaceholders();

    void setPlaceholders(Object... placeholders);
}
