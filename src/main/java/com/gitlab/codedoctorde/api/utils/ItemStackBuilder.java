package com.gitlab.codedoctorde.api.utils;

import com.gitlab.codedoctorde.api.config.JsonConfigurationValue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ItemStackBuilder {
    private static Gson gson = new GsonBuilder().create();
    private ItemStack itemStack;

    public ItemStackBuilder() {
        itemStack = new ItemStack(Material.AIR);
    }

    public ItemStackBuilder(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
    }

    public ItemStackBuilder(Material material) {
        itemStack = new ItemStack(material);
    }

    public ItemStackBuilder(String material) {
        itemStack = new ItemStack(Objects.requireNonNull(Material.getMaterial(material)));
    }

    public ItemStackBuilder(final ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStackBuilder(JsonConfigurationValue value) {
        if (value != null) {
            try {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(value.getString()));
                try (BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {
                    itemStack = (ItemStack) dataInput.readObject();
                }
            } catch (Exception e) {
                e.printStackTrace();
                itemStack = new ItemStack(Material.AIR);
            }
        } else
            itemStack = new ItemStack(Material.AIR);
    }

    public ItemStackBuilder setMaterial(Material material) {
        itemStack.setType(material);
        return this;
    }

    public Material getMaterial() {
        return itemStack.getType();
    }

    public ItemStackBuilder material(Material material) {
        return setMaterial(material);
    }

    public Material material(){
        return itemStack.getType();
    }

    public ItemStackBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public int getAmount() {
        return itemStack.getAmount();
    }

    public ItemStackBuilder amount(int amount) {
        return setAmount(amount);
    }

    @Nullable
    public List<String> getLore() {
        return itemStack.getItemMeta().getLore();
    }

    public ItemStackBuilder setLore(String... lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> loreList = new ArrayList<>();
        Arrays.stream(lore).map(line -> Arrays.asList(line.split("\r\n"))).forEach(loreList::addAll);
        Objects.requireNonNull(itemMeta).setLore(loreList);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder setLore(List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder lore(List<String> lore) {
        return setLore(lore);
    }


    public ItemStackBuilder lore(String... lore) {
        return setLore(lore);
    }

    public ItemStackBuilder setDisplayName(String displayName) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public String getDisplayName() {
        return itemStack.getItemMeta().getDisplayName();
    }

    /**
     * @deprecated Replaced by {@link #displayName(String)} ()}
     */
    @Deprecated
    public ItemStackBuilder name(String displayName) {
        return setDisplayName(displayName);
    }

    public ItemStackBuilder displayName(String displayName) {
        return setDisplayName(displayName);
    }

    @Nullable
    public Integer getCustomModelData() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return (itemMeta.hasCustomModelData()) ? itemMeta.getCustomModelData() : null;
    }

    public ItemStackBuilder setCustomModelData(Integer data) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setCustomModelData(data);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder customModelData(Integer data) {
        return setCustomModelData(data);
    }


    public ItemStackBuilder format(Object... arguments) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(MessageFormat.format(itemMeta.getDisplayName(), arguments));
        List<String> formattedLore = new ArrayList<>();
        List<String> lore = itemMeta.getLore();
        Objects.requireNonNull(lore).stream().map(line -> Arrays.asList(line.split("\n"))).forEach(formattedLore::addAll);
        itemMeta.setLore(formattedLore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }


    public String serialize() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(itemStack);

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Build
    public ItemStack build() {
        return itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
