package com.github.codedoctorde.api.ui.template.item;

import com.github.codedoctorde.api.translations.TranslatedObject;
import com.github.codedoctorde.api.translations.Translation;
import com.github.codedoctorde.api.ui.Gui;
import com.github.codedoctorde.api.ui.item.StaticItem;
import com.github.codedoctorde.api.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author CodeDoctorDE
 */
public class TranslatedItem extends StaticItem implements TranslatedObject {
    private final Translation translation;

    public TranslatedItem(Translation translation) {
        this(translation, new ItemStack(Material.AIR));
    }

    public TranslatedItem(Translation translation, ItemStack itemStack) {
        super(itemStack);
        this.translation = translation;
    }

    @Override
    public ItemStack build(Gui gui) {
        renderAction.accept(gui);
        ItemStackBuilder itemStackBuilder = new ItemStackBuilder(getItemStack());
        getTranslation().translate(itemStackBuilder, getPlaceholders());
        return itemStackBuilder.build();
    }

    public Translation getTranslation() {
        return translation;
    }
}
