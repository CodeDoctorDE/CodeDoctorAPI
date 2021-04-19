package com.github.codedoctorde.api.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class ChestGui extends Gui {
    private final String title;


    public ChestGui() {
        this("", 3);
    }
    public ChestGui(String title) {
        this(title, 3);
    }
    public ChestGui(int size) {
        this("", size);
    }

    public ChestGui(String title, int size) {
        super(9, size);
        this.title = title;
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
        Inventory inventory = Bukkit.createInventory(player, InventoryType.CHEST, title);
        buildInventory(inventory);
        for (GuiItem[] row : guiItems)
            for (GuiItem guiItem : row) guiItem.onOpen(player);
    }

    @Override
    protected void unregister(@NotNull Player player) {
        player.closeInventory();
        for (GuiItem[] row : guiItems) for (GuiItem guiItem : row) guiItem.onClose(player);
    }

    public void buildInventory(Inventory inventory){
        for (int x = 0; x < guiItems.length; x++) {
            GuiItem[] row = guiItems[x];
            for (int y = 0; y < row.length; y++) {
                GuiItem item = row[y];
                inventory.setItem(x + y * 9, item.build());
            }
        }
    }

    public String getTitle() {
        return title;
    }
}
