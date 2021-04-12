package com.github.codedoctorde.api.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.IntStream;

public class ChestGui extends Gui {;
    private final String title;
    private final HashMap<Integer, StaticItem> guiItems = new HashMap<>();


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
        super(size, 9);
        this.title = title;
    }

    @Override
    public void reload(Player... players) {

    }
}
