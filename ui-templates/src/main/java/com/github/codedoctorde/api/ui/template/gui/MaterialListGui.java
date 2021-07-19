package com.github.codedoctorde.api.ui.template.gui;

import com.github.codedoctorde.api.translations.Translation;
import com.github.codedoctorde.api.ui.item.GuiItem;
import com.github.codedoctorde.api.ui.item.StaticItem;
import com.github.codedoctorde.api.ui.template.gui.pane.list.VerticalListControls;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Consumer;

public class MaterialListGui extends ListGui {
    public MaterialListGui(Translation translation, Consumer<Material> action) {
        super(translation, 4, (gui) -> Arrays.stream(Material.values()).filter(material -> !material.isAir()).filter(material -> material.name().replace("_", " ").toLowerCase(Locale.ROOT).contains(gui.getSearchText().toLowerCase(Locale.ROOT)))
                .map(material -> new StaticItem(new ItemStack(material)) {{
                    setClickAction((event) -> action.accept(material));
                }}).toArray(GuiItem[]::new));
        setListControls(new VerticalListControls());
    }
}
