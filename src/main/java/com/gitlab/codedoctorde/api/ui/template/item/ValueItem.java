package com.gitlab.codedoctorde.api.ui.template.item;

import com.gitlab.codedoctorde.api.ui.Gui;
import com.gitlab.codedoctorde.api.ui.GuiItem;
import com.gitlab.codedoctorde.api.ui.GuiItemEvent;
import com.gitlab.codedoctorde.api.ui.template.item.events.InputItemEvent;
import com.gitlab.codedoctorde.api.ui.template.item.events.ValueItemEvent;
import com.gitlab.codedoctorde.api.utils.ItemStackBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author CodeDoctorDE
 */
public class ValueItem extends InputItem {
    private final float defaultValue;
    private final ValueItemEvent itemEvent;
    private float fastSkip = 5;
    private float skip = 1;
    private float value;

    public ValueItem(ItemStack itemStack, float value, float defaultValue, ValueItemEvent itemEvent) {
        super(itemStack, new InputItemEvent() {
            @Override
            public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event, InputItem inputItem) {
                itemEvent.onEvent(value, (Player) event.getWhoClicked(), (ValueItem) inputItem);
            }

            @Override
            public void onTick(Gui gui, GuiItem guiItem, Player player, InputItem inputItem) {
                itemEvent.onEvent(value, player, (ValueItem) inputItem);
            }
        });
        this.value = value;
        this.defaultValue = defaultValue;
        this.itemEvent = itemEvent;
    }

    public ValueItem(ItemStackBuilder itemStackBuilder, float value, float defaultValue, ValueItemEvent itemEvent) {
        this(itemStackBuilder.build(), value, defaultValue, itemEvent);
    }

    public ValueItem setSkip(float skip) {
        this.skip = skip;
        return this;
    }

    public float getSkip() {
        return skip;
    }

    public ValueItem setFastSkip(float fastSkip) {
        this.fastSkip = fastSkip;
        return this;
    }

    public float getFastSkip() {
        return fastSkip;
    }

    public GuiItem build() {
        final ValueItem valueItem = this;
        return new GuiItem(getFormattedItemStack(), new GuiItemEvent() {
            @Override
            public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                float current = value;
                switch (event.getClick()) {
                    case LEFT:
                        current+= skip;
                        break;
                    case RIGHT:
                        current-= skip;
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
                if (itemEvent.onEvent(current, (Player) event.getWhoClicked(), valueItem)) {
                    value = current;
                    guiItem.setItemStack(getFormattedItemStack());
                    Gui.reload((Player)event.getWhoClicked());
                }
            }
        });
    }
}
