package dev.linwood.api.item;

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
import org.jetbrains.annotations.NotNull;
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

    public ItemStackBuilder(@NotNull Material material, int amount) {
        itemStack = new ItemStack(material, amount);
    }

    public ItemStackBuilder(@NotNull Material material) {
        itemStack = new ItemStack(material);
    }

    public ItemStackBuilder(String skullId) {
        this(Material.PLAYER_HEAD);
        setSkullId(skullId);
    }

    public ItemStackBuilder(@NotNull UUID uuid) {
        this(Material.PLAYER_HEAD);
        setOwner(uuid);
    }

    public ItemStackBuilder(final @NotNull ItemStack itemStack) {
        this.itemStack = itemStack.clone();
    }

    public ItemStackBuilder(@Nullable JsonObject value) {
        if (value != null) {
            itemStack = new ItemStack(Material.valueOf(value.get("material").getAsString()));
            displayName(value.get("name").getAsString())
                    .lore(gson.fromJson(value.get("lore").getAsJsonArray(), String[].class))
                    .amount((value.get("amount") == null || !value.get("amount").isJsonNull()) ? 1 : value.get("amount").getAsInt());
        } else
            itemStack = new ItemStack(Material.AIR);
    }

    public ItemStackBuilder(@Nullable JsonElement value) {
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

    public static ItemStackBuilder placeholder() {
        return new ItemStackBuilder(Material.BLACK_STAINED_GLASS_PANE).displayName(" ");
    }

    public static @NotNull ItemStackBuilder deserialize(@Nullable String value) {
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

    public @Nullable OfflinePlayer getOwningPlayer() {
        return ((SkullMeta) Objects.requireNonNull(itemStack.getItemMeta())).getOwningPlayer();
    }

    public @NotNull ItemStackBuilder setOwningPlayer(OfflinePlayer player) {
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        assert meta != null;
        meta.setOwningPlayer(player);
        itemStack.setItemMeta(meta);
        return this;
    }

    public @Nullable UUID getOwner() {
        var p = getOwningPlayer();
        if (p != null) return p.getUniqueId();
        return null;
    }

    public @NotNull ItemStackBuilder setOwner(@NotNull UUID uuid) {
        return setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
    }

    public @NotNull Material getMaterial() {
        return itemStack.getType();
    }

    public @NotNull ItemStackBuilder setMaterial(@NotNull Material material) {
        itemStack.setType(material);
        return this;
    }

    public @NotNull ItemStackBuilder material(@NotNull Material material) {
        return setMaterial(material);
    }

    public @NotNull Material material() {
        return itemStack.getType();
    }

    public int getAmount() {
        return itemStack.getAmount();
    }

    public @NotNull ItemStackBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public @NotNull ItemStackBuilder amount(int amount) {
        return setAmount(amount);
    }

    public @NotNull List<String> getLore() {
        return (Objects.requireNonNull(itemStack.getItemMeta()).getLore() == null) ? new ArrayList<>() : Objects.requireNonNull(itemStack.getItemMeta().getLore());
    }

    public @NotNull ItemStackBuilder setLore(String @NotNull ... lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> loreList = new ArrayList<>();
        Arrays.stream(lore).map(line -> Arrays.asList(line.split("\n"))).forEach(loreList::addAll);
        Objects.requireNonNull(itemMeta).setLore(loreList);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public @NotNull ItemStackBuilder setLore(@NotNull List<String> lore) {
        return setLore(lore.toArray(String[]::new));
    }

    public @NotNull ItemStackBuilder lore(@NotNull List<String> lore) {
        return setLore(lore);
    }


    public @NotNull ItemStackBuilder lore(String... lore) {
        return setLore(lore);
    }

    public @NotNull ItemStackBuilder addLore(String... lore) {
        List<String> currentLore = new ArrayList<>(getLore());
        Collections.addAll(currentLore, lore);
        return setLore(currentLore);
    }

    public @NotNull ItemStackBuilder addLore(@NotNull JsonArray jsonArray) {
        for (JsonElement element :
                jsonArray)
            addLore(element.getAsString());
        return this;
    }

    public @NotNull String getLocalizedName() {
        return Objects.requireNonNull(itemStack.getItemMeta()).getLocalizedName();
    }

    public @NotNull ItemStackBuilder setLocalizedName(String localizedName) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setLocalizedName(localizedName);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public @NotNull ItemStackBuilder localizedName(String localizedName) {
        return setLocalizedName(localizedName);
    }

    public @NotNull String getDisplayName() {
        return Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName();
    }

    public @NotNull ItemStackBuilder setDisplayName(String displayName) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null)
            return this;
        itemMeta.setDisplayName(displayName);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * @param displayName The current name of the item
     * @deprecated Replaced by {@link #displayName(String)}
     * @return The current instance of the builder
     */
    @Deprecated
    public @NotNull ItemStackBuilder name(String displayName) {
        return setDisplayName(displayName);
    }

    public @NotNull ItemStackBuilder displayName(String displayName) {
        return setDisplayName(displayName);
    }

    @Nullable
    public Integer getCustomModelData() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        return (itemMeta.hasCustomModelData()) ? itemMeta.getCustomModelData() : null;
    }

    public @NotNull ItemStackBuilder setCustomModelData(Integer data) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).setCustomModelData(data);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public @NotNull ItemStackBuilder customModelData(Integer data) {
        return setCustomModelData(data);
    }

    public int getDamage() {
        return ((Damageable) itemStack).getDamage();
    }

    public @NotNull ItemStackBuilder setDamage(int damage) {
        ((Damageable) itemStack).setDamage(damage);
        return this;
    }

    public @NotNull ItemStackBuilder damage(int damage) {
        return setDamage(damage);
    }

    public @NotNull ItemStackBuilder addItemFlags(ItemFlag... itemFlags) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).addItemFlags(itemFlags);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public @NotNull ItemStackBuilder removeItemFlags(ItemFlag... itemFlags) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).removeItemFlags(itemFlags);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public boolean hasItemFlag(@NotNull ItemFlag itemFlag) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        return itemMeta.hasItemFlag(itemFlag);
    }

    public @NotNull Set<ItemFlag> getItemFlags() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        return itemMeta.getItemFlags();
    }

    public @NotNull ItemStackBuilder addAttributeModifier(@NotNull Attribute attribute, @NotNull AttributeModifier attributeModifier) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Objects.requireNonNull(itemMeta).addAttributeModifier(attribute, attributeModifier);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public @NotNull ItemStackBuilder removeAttributeModifier(@NotNull Attribute attribute) {
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

    public @NotNull ItemStackBuilder setSkullId(String skullId) {
        itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) itemStack.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", skullId));
        try {
            assert headMeta != null;
            Method mtd = headMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            mtd.setAccessible(true);
            mtd.invoke(headMeta, profile);
        } catch (@NotNull IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            ex.printStackTrace();
        }
        itemStack.setItemMeta(headMeta);
        return this;
    }

    public @Nullable Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        return itemMeta.getAttributeModifiers();
    }

    public Collection<AttributeModifier> getAttributeModifier(Attribute attribute) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        return Objects.requireNonNull(itemMeta.getAttributeModifiers()).get(attribute);
    }

    public boolean isUnbreakable() {
        return Objects.requireNonNull(itemStack.getItemMeta()).isUnbreakable();
    }

    public @NotNull ItemStackBuilder setUnbreakable(boolean unbreakable) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setUnbreakable(unbreakable);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public @NotNull ItemStackBuilder format(Object... arguments) {
        if (itemStack.getItemMeta() == null)
            return this;
        displayName(String.format(getDisplayName(), arguments));
        List<String> formattedLore = new ArrayList<>();
        getLore().stream().map(line -> Arrays.asList(String.format(line, arguments).split("\n"))).forEach(formattedLore::addAll);
        setLore(formattedLore);
        return this;
    }

    public @Nullable String serialize() {
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

    public @NotNull ItemStackBuilder addEnchant(@NotNull Enchantment enchantment, int level) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.addEnchant(enchantment, level, true);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public @NotNull ItemStackBuilder removeEnchant(@NotNull Enchantment enchantment) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.removeEnchant(enchantment);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public boolean hasEnchant(@NotNull Enchantment enchantment) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        return itemMeta.hasEnchant(enchantment);
    }

    public boolean isEnchanted() {
        return hasEnchant(Enchantment.DURABILITY);
    }

    public @NotNull ItemStackBuilder setEnchanted(boolean enchanted) {
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
