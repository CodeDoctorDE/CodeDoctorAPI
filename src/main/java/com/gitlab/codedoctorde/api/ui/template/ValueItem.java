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
    private final float defaultValue;
    private final ValueItemEvent itemEvent;
    private Object[] format = new Object[0];
    private float fastSkip = 5;
    private float skip = 1;
    private float value;

    public ValueItem(ItemStack itemStack, float value, float defaultValue, ValueItemEvent itemEvent) {
        this.itemStack = itemStack;
        this.value = value;
        this.defaultValue = defaultValue;
        this.itemEvent = itemEvent;
    }

    public ValueItem(ItemStackBuilder itemStackBuilder, float value, float defaultValue, ValueItemEvent itemEvent) {
        this.itemStack = itemStackBuilder.build();
        this.value = value;
        this.defaultValue = defaultValue;
        this.itemEvent = itemEvent;
    }

    public ValueItem setFormat(Object[] format) {
        this.format = format;
        return this;
    }

    public Object[] getFormat() {
        return format;
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
        return new GuiItem(new ItemStackBuilder(itemStack).format(value, format), new GuiItemEvent() {
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
                if (itemEvent.onEvent(current, (Player) event.getWhoClicked())) {
                    value = current;
                    gui.reload();
                }
            }
        });
    }
}
