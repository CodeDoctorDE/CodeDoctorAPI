package com.github.codedoctorde.api.animation;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public class AnimationItemManager {
    private ItemStack itemStackTemplate;
    private AnimationItemType animationItemType;
    private List<String> changes;

    public AnimationItemManager(final AnimationItemType animationItemType, final List<String> changes, final ItemStack itemStackTemplate) {
        this.animationItemType = animationItemType;
        this.changes = changes;
        this.itemStackTemplate = itemStackTemplate;
    }

    public AnimationItemType getAnimationItemType() {
        return animationItemType;
    }

    public void setAnimationItemType(AnimationItemType animationItemType) {
        this.animationItemType = animationItemType;
    }

    public List<String> getChanges() {
        return changes;
    }

    public void setChanges(List<String> changes) {
        this.changes = changes;
    }

    public ItemStack getItemStackTemplate() {
        return itemStackTemplate;
    }

    public void setItemStackTemplate(ItemStack itemStackTemplate) {
        this.itemStackTemplate = itemStackTemplate;
    }

    private int size() {
        if (itemStackTemplate.getItemMeta() == null)
            return 0;
        ItemMeta itemMetaTemplate = itemStackTemplate.getItemMeta();
        if (!itemMetaTemplate.hasDisplayName())
            return 0;
        if (changes.size() < 2)
            return 0;
        switch (animationItemType) {
            case STRIPE:
                return itemMetaTemplate.getDisplayName().toCharArray().length * changes.size();
            case BLING:
                return changes.size();
            default:
                return 0;
        }
    }

    private @NotNull List<ItemStack> getItemStacks() {
        ItemMeta itemMetaTemplate = itemStackTemplate.getItemMeta();
        int maxSize = size();
        List<ItemStack> itemStacks = new ArrayList<>();
        if (maxSize < 1)
            return itemStacks;
        switch (animationItemType) {
            case STRIPE:
                String lastChange = changes.get(changes.size() - 1);
                for (String change : changes) {
                    assert itemMetaTemplate != null;
                    char[] charArray = itemMetaTemplate.getDisplayName().toCharArray();
                    for (int i = 0; i < charArray.length; i++) {
                        ItemStack itemStack = itemStackTemplate.clone();
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        StringBuilder displayName = new StringBuilder(lastChange);
                        for (int x = 0; x < charArray.length; x++) {
                            if (x == i)
                                displayName.append(change);
                            displayName.append(charArray[x]);
                        }
                        assert itemMeta != null;
                        itemMeta.setDisplayName(displayName.toString());
                        itemStack.setItemMeta(itemMeta);
                    }
                    lastChange = change;
                }
                break;
            case SINGLE:
            case BLING:
                break;
        }
        return itemStacks;
    }
}