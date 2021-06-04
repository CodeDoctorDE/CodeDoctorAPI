package com.github.codedoctorde.api.ui.template.item;

import com.github.codedoctorde.api.translations.TranslatedObject;
import com.github.codedoctorde.api.translations.Translation;
import com.github.codedoctorde.api.ui.Gui;
import com.github.codedoctorde.api.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Collectors;

/**
 * An translated item which use the translation from the parent gui
 *
 * @author CodeDoctorDE
 */
public class TranslatedGuiItem extends TranslatedItem {
    public TranslatedGuiItem() {
        this(new ItemStack(Material.AIR));
    }

    public TranslatedGuiItem(ItemStack itemStack) {
        super(new Translation(), itemStack);
    }

    @Override
    public ItemStack build(Gui gui) {
        if (gui instanceof TranslatedObject) {
            renderAction.accept(gui);
            TranslatedObject to = (TranslatedObject) gui;
            ItemStackBuilder itemStackBuilder = new ItemStackBuilder(getItemStack());
            itemStackBuilder.setDisplayName(to.getTranslation().getTranslation(itemStackBuilder.getDisplayName()));
            itemStackBuilder.setLore(itemStackBuilder.getLore().stream().map(s -> to.getTranslation().getTranslation(s, getPlaceholders())).collect(Collectors.toList()));
            return itemStackBuilder.build();
        }
        return super.build(gui);
    }
}
