package com.github.codedoctorde.api.ui.template.gui;

import com.github.codedoctorde.api.translations.TranslatedObject;
import com.github.codedoctorde.api.ui.Gui;
import com.github.codedoctorde.api.ui.GuiCollection;
import com.github.codedoctorde.api.ui.GuiPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class TabGui extends GuiCollection {
    private final List<Object> placeholders = new ArrayList<>();
    private Function<Integer, GuiPane> tabsBuilder;

    public TabGui() {
        super();
    }

    public void setTabsBuilder(Function<Integer, GuiPane> tabsBuilder) {
        this.tabsBuilder = tabsBuilder;
    }

    public void registerGui(Gui gui) {
        if (gui instanceof TranslatedObject)
            ((TranslatedObject) gui).setPlaceholders(placeholders);
        if (tabsBuilder != null)
            gui.addPane(tabsBuilder.apply(getGuis().length));
        super.registerGui(gui);
    }

    public Object[] getPlaceholders() {
        return placeholders.toArray();
    }

    public void setPlaceholders(Object... placeholders) {
        this.placeholders.clear();
        this.placeholders.addAll(Collections.singleton(placeholders));
    }
}
