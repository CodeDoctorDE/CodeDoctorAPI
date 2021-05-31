package com.github.codedoctorde.api.ui.template.gui;

import com.github.codedoctorde.api.translations.Translation;
import com.github.codedoctorde.api.ui.ChestGui;

public class TranslationChestGui extends ChestGui {
    private final Translation translation;

    public TranslationChestGui(Translation translation, String title) {
        this(translation, title, 3);
    }
    public TranslationChestGui(Translation translation, String title, int size) {
        super(title, size);

        this.translation = translation;
    }


    public Translation getTranslation() {
        return translation;
    }

    @Override
    public String getTitle(){
        return translation.getTranslation(super.getTitle());
    }

    public String getUntranslatedTitle() {
        return super.getTitle();
    }
}
