package dev.linwood.api.gui.template.gui;

import dev.linwood.api.translation.TranslatedObject;
import dev.linwood.api.translation.Translation;
import dev.linwood.api.gui.ChestGui;

public class TranslatedChestGui extends ChestGui implements TranslatedObject {
    private final Translation translation;
    private Object[] placeholders = new Object[0];

    public TranslatedChestGui(Translation translation) {
        this(translation, 3);
    }

    public TranslatedChestGui(Translation translation, int size) {
        super("title", size);

        this.translation = translation;
    }


    public Translation getTranslation() {
        return translation;
    }

    @Override
    public String getTitle() {
        return translation.getTranslation(super.getTitle(), placeholders);
    }

    public String getUntranslatedTitle() {
        return super.getTitle();
    }

    public Object[] getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(Object... placeholders) {
        this.placeholders = placeholders;
    }
}
