package com.github.codedoctorde.api.ui.template.gui;

import com.github.codedoctorde.api.ui.Gui;
import com.github.codedoctorde.api.ui.GuiCollection;
import com.github.codedoctorde.api.ui.GuiPane;

import java.util.function.Function;

public class TabGui extends GuiCollection {
    private Function<Integer, GuiPane> tabsBuilder;

    public TabGui() {
        super();
    }

    public void setTabsBuilder(Function<Integer, GuiPane> tabsBuilder) {
        this.tabsBuilder = tabsBuilder;
    }

    public void registerGui(Gui gui) {
        super.registerGui(gui);
        if (tabsBuilder != null)
            gui.addPane(tabsBuilder.apply(getGuis().length));
    }
}
