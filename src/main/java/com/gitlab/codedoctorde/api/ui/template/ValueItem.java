package com.gitlab.codedoctorde.api.ui.template;

import com.gitlab.codedoctorde.api.ui.Gui;
import com.gitlab.codedoctorde.api.ui.GuiItem;
import com.gitlab.codedoctorde.api.ui.GuiItemEvent;
import com.gitlab.codedoctorde.api.ui.template.events.ValueItemEvent;
import com.gitlab.codedoctorde.api.utils.ItemStackBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author CodeDoctorDE
 */
public class ValueItem {
    private final ItemStack itemStack;
    private final int defaultValue;
    private final ValueItemEvent itemEvent;
    private int fastSkip = 5;
    private int value;

    public ValueItem(ItemStack itemStack, int value, int defaultValue, ValueItemEvent itemEvent) {
        this.itemStack = itemStack;
        this.value = value;
        this.defaultValue = defaultValue;
        this.itemEvent = itemEvent;
    }

    public ValueItem(ItemStackBuilder itemStackBuilder, int value, int defaultValue, ValueItemEvent itemEvent) {
        this.itemStack = itemStackBuilder.build();
        this.value = value;
        this.defaultValue = defaultValue;
        this.itemEvent = itemEvent;
    }

    public ValueItem(ItemStack itemStack, int value, int defaultValue, int fastSkip, ValueItemEvent itemEvent) {
        this.itemStack = itemStack;
        this.value = value;
        this.defaultValue = defaultValue;
        this.itemEvent = itemEvent;
        this.fastSkip = fastSkip;
    }

    public ValueItem(ItemStackBuilder itemStackBuilder, int value, int defaultValue, int fastSkip, ValueItemEvent itemEvent) {
        this.itemStack = itemStackBuilder.build();
        this.value = value;
        this.defaultValue = defaultValue;
        this.itemEvent = itemEvent;
        this.fastSkip = fastSkip;
    }

    public GuiItem build() {
        return new GuiItem(itemStack, new GuiItemEvent() {
            @Override
            public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                int current = value;
                switch (event.getClick()) {
                    case LEFT:
                        current++;
                        break;
                    case RIGHT:
                        current--;
                        break;
                    case SHIFT_LEFT:
                        current += fastSkip;
                        break;
                    case SHIFT_RIGHT:
                        current -= fastSkip;
                        break;
                    case DROP:
                        current = defaultValue;
                }
                if (itemEvent.onEvent(value, (Player) event.getWhoClicked()))
                    value = current;
            }
        });
    }
}
