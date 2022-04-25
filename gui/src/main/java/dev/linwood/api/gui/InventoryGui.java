package dev.linwood.api.gui;

import dev.linwood.api.gui.item.GuiItem;
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
    public void reload(Player @NotNull ... players) {
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
            for (GuiItem guiItem : row) if (guiItem != null) guiItem.onOpen(player);
    }

    @Override
    protected void unregister(@NotNull Player player) {
        player.closeInventory();
        for (GuiItem[] row : guiItems) for (GuiItem guiItem : row) if (guiItem != null) guiItem.onClose(player);
        if (!savedPlayerInventories.containsKey(player.getUniqueId()))
            return;
        player.getInventory().setContents(savedPlayerInventories.get(player.getUniqueId()));
        savedPlayerInventories.remove(player.getUniqueId());
        for (GuiItem[] row : guiItems)
            for (GuiItem guiItem : row) if (guiItem != null) guiItem.onClose(player);
    }

    public void buildInventory(@NotNull Inventory inventory) {
        inventory.clear();
        for (int x = 0; x < guiItems.length; x++) {
            GuiItem[] row = guiItems[x];
            for (int y = 0; y < row.length; y++) {
                GuiItem item = row[y];
                if (item != null)
                    inventory.setItem(x + y * 9, item.build(this));
            }
        }
    }
}
