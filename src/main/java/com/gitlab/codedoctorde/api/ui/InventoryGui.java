package com.gitlab.codedoctorde.api.ui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class InventoryGui extends Gui {
    private HashMap<Player, ItemStack[]> playerItemsHashMap = new HashMap<>();

    public InventoryGui(JavaPlugin javaPlugin) {
        super(javaPlugin);
    }

    @Override
    public void open(Player... players) {
        for (Player player :
                players) {
            playerItemsHashMap.put(player, player.getInventory().getContents());
            player.closeInventory();
            playerGuiHashMap.put(player, this);
            final Inventory inventory = guiPages.get(index).build();
            player.getInventory().clear();
            player.getInventory().setContents(inventory.getContents());
            startTick(player);
            getCurrentGuiPage().raiseInventoryOpenEvent(this, player);
        }
    }

    @Override
    public void close(Player... players) {
        for (Player player :
                players) {
            if (playerGuiHashMap.containsKey(player)) {
                playerGuiHashMap.get(player).getCurrentGuiPage().raiseInventoryCloseEvent(this, player);
                stopTick(player);
                playerGuiHashMap.remove(player);
            }
            player.getInventory().clear();
            if (playerItemsHashMap.containsKey(player))
                player.getInventory().setContents(playerItemsHashMap.get(player));
        }
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        super.onInventoryClick(event);
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {

    }

    @Override
    public void onInventoryDrag(InventoryDragEvent event) {
        super.onInventoryDrag(event);
    }
}
