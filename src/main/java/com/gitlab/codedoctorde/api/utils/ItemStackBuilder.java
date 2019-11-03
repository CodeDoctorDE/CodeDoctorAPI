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

    public ItemStackBuilder material(Material material) {
        itemStack.setType(material);
        return this;
    }

    public Material material(){
        return itemStack.getType();
    }

    public ItemStackBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }


    public ItemStackBuilder lore(List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }


    public ItemStackBuilder lore(String... lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setLore(new ArrayList<>(Arrays.asList(lore)));
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder name(String displayName) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
        return this;
    }


    public ItemStackBuilder format(Object... arguments) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(MessageFormat.format(itemMeta.getDisplayName(), arguments));
        List<String> lore = new ArrayList<>();
        for (String line :
                Objects.requireNonNull(itemMeta.getLore()))
            lore.add(MessageFormat.format(line, arguments));
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder customModelData(Integer data) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setCustomModelData(data);
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
