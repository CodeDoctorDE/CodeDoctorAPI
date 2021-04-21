package com.github.codedoctorde.api.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Objects;
import java.util.UUID;

/**
 * @author CodeDoctorDE
 */
public class ItemSkullBuilder extends ItemStackBuilder {
    public ItemSkullBuilder() {
        super(Material.PLAYER_HEAD);
    }
    public ItemSkullBuilder(UUID uuid) {
        super(Material.PLAYER_HEAD);
        setOwner(uuid);
    }

    public OfflinePlayer getOwningPlayer(){
        return ((SkullMeta) Objects.requireNonNull(itemStack.getItemMeta())).getOwningPlayer();
    }

    public UUID getOwner(){
        return getOwningPlayer().getUniqueId();
    }

    public ItemSkullBuilder setOwningPlayer(OfflinePlayer player) {
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        assert meta != null;
        meta.setOwningPlayer(player);
        itemStack.setItemMeta(meta);
        return this;
    }
    public ItemSkullBuilder setOwner(UUID uuid) {
        return setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
    }
}
