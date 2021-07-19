package com.github.codedoctorde.api.ui.template.gui;

import com.github.codedoctorde.api.translations.Translation;
import com.github.codedoctorde.api.ui.item.GuiItem;
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
    public int getHeight() {
        return super.getHeight() + (int) Math.ceil((double) actions.length / getWidth());
    }

    public int getDefaultHeight() {
        return super.getHeight();
    }

    @Override
    public void buildInventory(@NotNull Inventory inventory) {
        super.buildInventory(inventory);
        int lastSlot = getHeight() * 9;
        Translation t = getTranslation();
        inventory.setItem(4, new ItemStackBuilder(Material.OAK_SIGN).setDisplayName(t.getTranslation("title")).setLore(t.getTranslation("description")).build());
        for (int i = 0; i < actions.length; i++) inventory.setItem(lastSlot - i - 1, actions[i].build(this));
    }
}
