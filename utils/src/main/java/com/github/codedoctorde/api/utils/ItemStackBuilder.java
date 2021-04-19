package com.github.codedoctorde.api.utils;

import com.google.common.collect.Multimap;
import com.google.gson.*;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * @author CodeDoctorDE
 */
public class ItemStackBuilder {
    private static final Gson gson = new GsonBuilder().create();
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
        this.itemStack = itemStack.clone();
    }

    public ItemStackBuilder(JsonObject value) {
        if (value != null) {
            itemStack = new ItemStack(Material.valueOf(value.get("material").getAsString()));
            displayName(value.get("name").getAsString())
                    .lore(gson.fromJson(value.get("lore").getAsJsonArray(), String[].class))
                    .amount((value.get("amount") == null || !value.get("amount").isJsonNull()) ? 1 : value.get("amount").getAsInt());
        } else
            itemStack = new ItemStack(Material.AIR);
    }

    public ItemStackBuilder(JsonElement value) {
        if (value != null) {
            try {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(value.getAsString()));
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

    public static ItemStackBuilder deserialize(String value) {
        if (value != null) {
            try {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(value));
                try (BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {
                    return new ItemStackBuilder((ItemStack) dataInput.readObject());
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new ItemStackBuilder();
            }
        } else
            return new ItemStackBuilder();
    }

    public Material getMaterial() {
        return itemStack.getType();
    }

    public ItemStackBuilder setMaterial(Material material) {
        itemStack.setType(material);
        return this;
    }

    public ItemStackBuilder material(Material material) {
        return setMaterial(material);
    }

    public Material material() {
        return itemStack.getType();
    }

    public int getAmount() {
        return itemStack.getAmount();
    }

    public ItemStackBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemStackBuilder amount(int amount) {
        return setAmount(amount);
    }

    public List<String> getLore() {
        return (itemStack.getItemMeta().getLore() == null) ? new ArrayList<>() : itemStack.getItemMeta().getLore();
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

    public ItemStackBuilder addLore(String... lore) {
        List<String> currentLore = new ArrayList<>(getLore());
        Collections.addAll(currentLore, lore);
        return setLore(currentLore);
    }

    public ItemStackBuilder addLore(JsonArray jsonArray) {
        for (JsonElement element :
                jsonArray)
            addLore(element.getAsString());
        return this;
    }

    public String getDisplayName() {
        return Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName();
    }

    public ItemStackBuilder setDisplayName(String displayName) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
        return this;
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

    public int getDamage() {
        return ((Damageable) itemStack).getDamage();
    }

    public ItemStackBuilder setDamage(int damage) {
        ((Damageable) itemStack).setDamage(damage);
        return this;
    }

    public ItemStackBuilder damage(int damage) {
        return setDamage(damage);
    }

    public ItemStackBuilder addItemFlags(ItemFlag... itemFlags) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).addItemFlags(itemFlags);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder removeItemFlags(ItemFlag... itemFlags) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).removeItemFlags(itemFlags);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public boolean hasItemFlag(ItemFlag itemFlag) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.hasItemFlag(itemFlag);
    }

    public Set<ItemFlag> getItemFlags() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getItemFlags();
    }

    public ItemStackBuilder addAttributeModifier(Attribute attribute, AttributeModifier attributeModifier) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).addAttributeModifier(attribute, attributeModifier);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder removeAttributeModifier(Attribute attribute) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).removeAttributeModifier(attribute);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public boolean hasAttributeModifier(Attribute attribute) {
        ItemMeta itemMeta = itemStack.getItemMeta();


        return (itemMeta.getAttributeModifiers() != null) && Objects.requireNonNull(itemMeta.getAttributeModifiers()).containsKey(attribute);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        return itemMeta.getAttributeModifiers();
    }

    public Collection<AttributeModifier> getAttributeModifier(Attribute attribute) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        return Objects.requireNonNull(itemMeta.getAttributeModifiers()).get(attribute);
    }
    public boolean isUnbreakable(){
        return Objects.requireNonNull(itemStack.getItemMeta()).isUnbreakable();
    }
    public ItemStackBuilder setUnbreakable(boolean unbreakable){
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setUnbreakable(unbreakable);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder format(Object... arguments) {
        displayName(String.format(getDisplayName(), arguments));
        List<String> formattedLore = new ArrayList<>();
        getLore().stream().map(line -> Arrays.asList(String.format(line, arguments).split("\n"))).forEach(formattedLore::addAll);
        setLore(formattedLore);
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

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

}
