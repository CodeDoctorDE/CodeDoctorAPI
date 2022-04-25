package dev.linwood.api.gui.template.gui;

import dev.linwood.api.translation.TranslatedObject;
import dev.linwood.api.gui.Gui;
import dev.linwood.api.gui.GuiCollection;
import dev.linwood.api.gui.GuiPane;
import org.jetbrains.annotations.NotNull;

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

    public Object @NotNull [] getPlaceholders() {
        return placeholders.toArray();
    }

    public void setPlaceholders(Object... placeholders) {
        this.placeholders.clear();
        this.placeholders.addAll(Collections.singleton(placeholders));
    }
}
