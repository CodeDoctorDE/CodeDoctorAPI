package dev.linwood.api.gui.template.item;

import dev.linwood.api.translation.TranslatedObject;
import dev.linwood.api.translation.Translation;
import dev.linwood.api.gui.Gui;
import dev.linwood.api.gui.item.StaticItem;
import dev.linwood.api.item.ItemStackBuilder;
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
        if (translation != null && !getItemStack().getType().isAir())
            getTranslation().translate(itemStackBuilder, getPlaceholders());
        return itemStackBuilder.build();
    }

    public Translation getTranslation() {
        return translation;
    }
}
