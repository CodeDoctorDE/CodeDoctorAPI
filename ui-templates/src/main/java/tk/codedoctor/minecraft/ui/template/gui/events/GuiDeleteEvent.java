package tk.codedoctor.minecraft.ui.template.gui.events;

import tk.codedoctor.minecraft.ui.GuiItem;
import tk.codedoctor.minecraft.utils.ItemStackBuilder;

public interface GuiDeleteEvent {

    String title(int index);

    GuiItem yesItem(ItemStackBuilder itemStack, int index);

    GuiItem noItem(ItemStackBuilder itemStack, int index);
}
