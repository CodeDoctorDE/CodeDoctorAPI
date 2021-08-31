package dev.linwood.api.ui;

import dev.linwood.api.ui.item.GuiItem;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * @author CodeDoctorDE
 */
public class GuiListener implements Listener {

    public static void register() {
        GuiListener instance = new GuiListener();
        if (instance.isRegistered())
            return;
        Bukkit.getPluginManager().registerEvents(instance,
                JavaPlugin.getProvidingPlugin(instance.getClass()));
    }

    public boolean isRegistered() {
        return HandlerList.getRegisteredListeners(JavaPlugin.getProvidingPlugin(getClass())).stream().anyMatch(registeredListener -> registeredListener.getListener() instanceof GuiListener);
    }

    @EventHandler
    public void onChestGuiItemClicked(@NotNull InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Gui gui = Gui.getGui((Player) event.getWhoClicked());
        if (gui == null)
            return;
        event.setCancelled(true);
        if (event.getClickedInventory() instanceof PlayerInventory)
            return;
        int slot = event.getSlot();
        int x = slot % 9;
        int y = slot / 9;
        GuiItem item = gui.getItem(x, y);
        if (item != null) {
            if (!gui.isSilent())
                ((Player) event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(), Sound.UI_BUTTON_CLICK, 0.5f, 1.0f);
            item.onClick(event);
        }
    }

    @EventHandler
    public void onPlayerQuit(@NotNull PlayerQuitEvent event) {
        var gui = Gui.getGui(event.getPlayer());
        if (gui != null) gui.hide(event.getPlayer());
    }

    @EventHandler
    public void onChestGuiClosed(@NotNull InventoryCloseEvent event) {
        var p = (Player) event.getPlayer();
        var gui = Gui.getGui(p);
        if (gui != null) {
            Gui.playerGuis.remove(p.getUniqueId());
            gui.unregister(p);
        }
    }
}
