package com.github.codedoctorde.api.ui;

import com.github.codedoctorde.api.ui.item.GuiItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryGui extends Gui {
    private final Map<UUID, ItemStack[]> savedPlayerInventories = new HashMap<>();

    public InventoryGui() {
        super(9, 4);
    }

    @Override
    public void reload(Player... players) {
        for (Player player : players) {
            if (hasGui(player)) {
                Inventory inventory = player.getOpenInventory().getTopInventory();
                buildInventory(inventory);
            }
        }
    }

    @Override
    protected void register(@NotNull Player player) {
        PlayerInventory inventory = player.getInventory();
        savedPlayerInventories.put(player.getUniqueId(), inventory.getContents());
        buildInventory(inventory);
        for (GuiItem[] row : guiItems)
            for (GuiItem guiItem : row) guiItem.onOpen(player);
    }

    @Override
    protected void unregister(@NotNull Player player) {
        player.closeInventory();
        for (GuiItem[] row : guiItems) for (GuiItem guiItem : row) guiItem.onClose(player);
        if(!savedPlayerInventories.containsKey(player.getUniqueId()))
            return;
        player.getInventory().setContents(savedPlayerInventories.get(player.getUniqueId()));
        savedPlayerInventories.remove(player.getUniqueId());
    }

    public void buildInventory(Inventory inventory) {
        inventory.clear();
        for (int x = 0; x < guiItems.length; x++) {
            GuiItem[] row = guiItems[x];
            for (int y = 0; y < row.length; y++) {
                GuiItem item = row[y];
                inventory.setItem(x + y * 9, item.build(this));
            }
        }
    }
}
