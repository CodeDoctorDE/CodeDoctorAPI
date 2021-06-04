package com.github.codedoctorde.api.ui.template.gui;

import com.github.codedoctorde.api.translations.Translation;
import com.github.codedoctorde.api.ui.GuiItem;
import org.bukkit.inventory.Inventory;

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

    public void setItems(GuiItem... actions) {
        this.actions = actions;
    }

    public void addItems(GuiItem... actions){
        this.actions = Stream.concat(Arrays.stream(this.actions), Arrays.stream(actions)).toArray(GuiItem[]::new);
    }

    public GuiItem[] getActions() {
        return actions;
    }

    @Override
    public int getHeight() {
        return (int) Math.ceil(super.getHeight() * (double) actions.length);
    }

    public int getDefaultHeight() {
        return super.getHeight();
    }

    @Override
    public void buildInventory(Inventory inventory) {
        super.buildInventory(inventory);
        int lastSlot = getHeight() * 9;
        for (int i = 0; i < actions.length; i++) inventory.setItem(lastSlot - i - 1, actions[i].build(this));
    }
}
