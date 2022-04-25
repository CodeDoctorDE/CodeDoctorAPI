package dev.linwood.api.gui.template.gui;

import dev.linwood.api.translation.Translation;
import dev.linwood.api.gui.item.GuiItem;
import dev.linwood.api.gui.item.StaticItem;
import dev.linwood.api.gui.template.gui.pane.list.VerticalListControls;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Consumer;

public class MaterialListGui extends ListGui {
    public MaterialListGui(Translation translation, @NotNull Consumer<Material> action) {
        super(translation, 4, (gui) -> Arrays.stream(Material.values()).filter(material -> !material.isAir()).filter(material -> material.name().replace("_", " ").toLowerCase(Locale.ROOT).contains(gui.getSearchText().toLowerCase(Locale.ROOT)))
                .map(material -> new StaticItem(new ItemStack(material)) {{
                    setClickAction((event) -> action.accept(material));
                }}).toArray(GuiItem[]::new));
        setListControls(new VerticalListControls());
    }
}
