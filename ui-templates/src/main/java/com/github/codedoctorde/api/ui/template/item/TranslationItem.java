package com.github.codedoctorde.api.ui.template.item;

import com.github.codedoctorde.api.translations.Translation;
import com.github.codedoctorde.api.ui.StaticItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        ItemStack itemStack = super.build();
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(translation.getTranslation(itemMeta.getDisplayName()));
        List<String> lore = itemMeta.getLore();
        if(lore != null)
            itemMeta.setLore(lore.stream().map(translation::getTranslation).collect(Collectors.toList()));

        return itemStack;
    }
}
