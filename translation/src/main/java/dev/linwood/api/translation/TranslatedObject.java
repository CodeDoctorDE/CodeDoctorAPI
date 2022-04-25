package dev.linwood.api.translation;

public interface TranslatedObject {
    Translation getTranslation();

    Object[] getPlaceholders();

    void setPlaceholders(Object... placeholders);
}
