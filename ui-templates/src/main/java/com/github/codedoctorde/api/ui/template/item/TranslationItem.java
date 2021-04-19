package com.github.codedoctorde.api.ui.template.item;

import com.github.codedoctorde.api.translations.Translation;
import com.github.codedoctorde.api.ui.StaticItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CodeDoctorDE
 */
public class TranslationItem extends StaticItem {
    private final Translation translation;
    private final List<Object> placeholders = new ArrayList<>();

    public TranslationItem(Translation translation, ItemStack itemStack) {
        super(itemStack);
        this.translation = translation;
    }

    public void registerPlaceholders(Object... placeholders){
        this.placeholders.addAll(Collections.singleton(placeholders));
    }

    @Override
    public ItemStack build() {
        ItemStack itemStack = super.build();
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(String.format(translation.getTranslation(itemMeta.getDisplayName()), placeholders));
        List<String> lore = itemMeta.getLore();
        if(lore != null)
            itemMeta.setLore(lore.stream().map(line -> String.format(translation.getTranslation(line), placeholders)).collect(Collectors.toList()));

        return itemStack;
    }
}
