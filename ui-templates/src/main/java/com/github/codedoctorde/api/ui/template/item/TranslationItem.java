package com.github.codedoctorde.api.ui.template.item;

import com.github.codedoctorde.api.translations.Translation;
import com.github.codedoctorde.api.ui.StaticItem;
import com.github.codedoctorde.api.utils.ItemStackBuilder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CodeDoctorDE
 */
public class TranslationItem extends StaticItem {
    private final Translation translation;

    public TranslationItem(Translation translation, ItemStack itemStack) {
        super(itemStack);
        this.translation = translation;
    }

    @Override
    public ItemStack build() {
        ItemStackBuilder itemStackBuilder = new ItemStackBuilder(getItemStack());
        itemStackBuilder.setLore(itemStackBuilder.getLore().stream().map(s -> translation.getTranslation(s, getPlaceholders())).collect(Collectors.toList()));
        return itemStackBuilder.build();
    }
}
