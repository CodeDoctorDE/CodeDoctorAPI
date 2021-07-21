package com.github.codedoctorde.api.ui.template.gui;

import com.github.codedoctorde.api.translations.Translation;
import com.github.codedoctorde.api.ui.item.GuiItem;
import com.github.codedoctorde.api.ui.template.item.TranslatedGuiItem;
import com.github.codedoctorde.api.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Stream;

public class MessageGui extends TranslatedChestGui {
    private GuiItem[] actions = new GuiItem[0];

    public MessageGui(Translation translation) {
        super(translation);
    }

    public MessageGui(Translation translation, int size) {
        super(translation, size);
    }

    public void addActions(GuiItem @NotNull ... actions) {
        this.actions = Stream.concat(Arrays.stream(this.actions), Arrays.stream(actions)).toArray(GuiItem[]::new);
    }

    public GuiItem[] getActions() {
        return actions;
    }

    public void setActions(GuiItem... actions) {
        this.actions = actions;
    }

    @Override
    public void buildInventory(@NotNull Inventory inventory) {
        int lastSlot = getHeight() * 9;
        Translation t = getTranslation();
        var placeholders = getPlaceholders();
        registerItem(4, 0, new TranslatedGuiItem(new ItemStackBuilder(Material.OAK_SIGN).setDisplayName("message").setLore("description").build()) {{
            setPlaceholders(placeholders);
        }});
        for (int i = 0; i < actions.length && i < (getHeight() - 2) * 9; i++) {
            var loc = lastSlot - i - 1;
            var x = loc % 9;
            var y = loc / 9;
            registerItem(x, y, actions[i]);
            super.buildInventory(inventory);
        }
    }
}
