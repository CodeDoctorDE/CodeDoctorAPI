package dev.linwood.api.ui.template.item;

import dev.linwood.api.translation.TranslatedObject;
import dev.linwood.api.translation.Translation;
import dev.linwood.api.ui.Gui;
import dev.linwood.api.item.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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
            if (!getItemStack().getType().isAir())
                to.getTranslation().translate(itemStackBuilder, getPlaceholders());
            return itemStackBuilder.build();
        }
        return super.build(gui);
    }
}
