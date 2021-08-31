package dev.linwood.api.ui;

import dev.linwood.api.ui.item.GuiItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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

    public ChestGui(String title, int height) {
        super(9, height);
        this.title = title;
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
        Inventory inventory = Bukkit.createInventory(player, getHeight() * 9, getTitle());
        buildInventory(inventory);
        player.openInventory(inventory);
        for (GuiItem[] row : guiItems)
            for (GuiItem guiItem : row) if (guiItem != null) guiItem.onOpen(player);
    }

    @Override
    protected void unregister(@NotNull Player player) {
        player.closeInventory();
        for (GuiItem[] row : guiItems) for (GuiItem guiItem : row) if (guiItem != null) guiItem.onClose(player);
    }

    public void buildInventory(@NotNull Inventory inventory) {
        for (int x = 0; x < guiItems.length; x++) {
            GuiItem[] row = guiItems[x];
            for (int y = 0; y < row.length; y++) {
                GuiItem item = row[y];
                if (item != null)
                    inventory.setItem(x + y * 9, item.build(this));
            }
        }
    }

    public String getTitle() {
        return title;
    }
}
