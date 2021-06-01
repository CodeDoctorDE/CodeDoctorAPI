package com.github.codedoctorde.api.ui.template.gui;

import com.github.codedoctorde.api.translations.Translation;
import com.github.codedoctorde.api.ui.ChestGui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TranslatedChestGui extends ChestGui {
    private final Translation translation;
    private List<Object> placeholders = new ArrayList<>();

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
    public String getTitle(){
        return translation.getTranslation(super.getTitle(), placeholders);
    }

    public String getUntranslatedTitle() {
        return super.getTitle();
    }

    public void setPlaceholders(Object... placeholders){
        this.placeholders = Arrays.asList(placeholders);
    }

    public Object[] getPlaceholders() {
        return placeholders.toArray();
    }
}
