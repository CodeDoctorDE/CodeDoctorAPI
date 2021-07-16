package com.github.codedoctorde.api.utils;

import com.google.common.collect.Multimap;
import com.google.gson.*;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author CodeDoctorDE
 */
public class ItemStackBuilder {
    private static final Gson gson = new GsonBuilder().create();
    protected ItemStack itemStack;

    public ItemStackBuilder() {
        itemStack = new ItemStack(Material.AIR);
    }

    public ItemStackBuilder(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
    }

    public ItemStackBuilder(Material material) {
        itemStack = new ItemStack(material);
    }

    public ItemStackBuilder(String skullId) {
        this(Material.PLAYER_HEAD);
        setSkullId(skullId);
    }
    public ItemStackBuilder(UUID uuid) {
        this(Material.PLAYER_HEAD);
        setOwner(uuid);
    }

    public OfflinePlayer getOwningPlayer(){
        return ((SkullMeta) Objects.requireNonNull(itemStack.getItemMeta())).getOwningPlayer();
    }

    public UUID getOwner(){
        return getOwningPlayer().getUniqueId();
    }

    public ItemStackBuilder setOwningPlayer(OfflinePlayer player) {
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        assert meta != null;
        meta.setOwningPlayer(player);
        itemStack.setItemMeta(meta);
        return this;
    }
    public ItemStackBuilder setOwner(UUID uuid) {
        return setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
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
        setLore(lore.toArray(String[]::new));
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

    public String getLocalizedName() {
        return Objects.requireNonNull(itemStack.getItemMeta()).getLocalizedName();
    }

    public ItemStackBuilder setLocalizedName(String localizedName) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setLocalizedName(localizedName);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder localizedName(String localizedName) {
        return setLocalizedName(localizedName);
    }

    public String getDisplayName() {
        return Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName();
    }

    public ItemStackBuilder setDisplayName(String displayName) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null)
            return null;
        itemMeta.setDisplayName(displayName);
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
        assert itemMeta != null;
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
        assert itemMeta != null;
        return itemMeta.hasItemFlag(itemFlag);
    }

    public Set<ItemFlag> getItemFlags() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
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
        assert itemMeta != null;


        return (itemMeta.getAttributeModifiers() != null) && Objects.requireNonNull(itemMeta.getAttributeModifiers()).containsKey(attribute);
    }

    public ItemStackBuilder setSkullId(String skullId){
        itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta)itemStack.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", skullId));
        try {
            assert headMeta != null;
            Method mtd = headMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            mtd.setAccessible(true);
            mtd.invoke(headMeta, profile);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            ex.printStackTrace();
        }
        itemStack.setItemMeta(headMeta);
        return this;
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
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
        if(itemStack.getItemMeta() == null)
            return this;
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


    @Deprecated
    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStackBuilder addEnchant(Enchantment enchantment, int level) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.addEnchant(enchantment, level, true);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder removeEnchant(Enchantment enchantment) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.removeEnchant(enchantment);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public boolean hasEnchant(Enchantment enchantment) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        return itemMeta.hasEnchant(enchantment);
    }

    public boolean isEnchanted() {
        return hasEnchant(Enchantment.DURABILITY);
    }

    public ItemStackBuilder setEnchanted(boolean enchanted) {
        if (enchanted) {
            addEnchant(Enchantment.DURABILITY, 1);
            addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            removeEnchant(Enchantment.DURABILITY);
            removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        return this;
    }

}
