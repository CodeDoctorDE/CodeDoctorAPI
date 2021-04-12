package com.github.codedoctorde.api.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public abstract class Gui {
    private Consumer<Player> openAction;
    private Consumer<Player> closeAction;
    private final GuiItem[][] guiItems;
    private final List<UUID> openedPlayers = new ArrayList<>();

    public Gui(int height, int width){
        guiItems = new GuiItem[height][width];
        GuiListener.register();
    }

    public void show(Player... players){
        for (Player player : players) {
            openedPlayers.add(player.getUniqueId());
            register(player);
        }
    }
    public void hide(Player... players){
        for (Player player : players) {
            openedPlayers.remove(player.getUniqueId());
            unregister(player);
        }
    }
    protected void register(Player player) {

    }
    protected void unregister(Player player){

    }
    public void reload(){
        reload(getOpenedPlayers());
    }
    public abstract void reload(Player... players);

    public Player[] getOpenedPlayers() {
        return openedPlayers.stream().map(Bukkit::getPlayer).toArray(Player[]::new);
    }

    public boolean hasGui(Player player){
        return openedPlayers.contains(player.getUniqueId());
    }

    public void setOpenAction(Consumer<Player> openAction) {
        this.openAction = openAction;
    }

    public void setCloseAction(Consumer<Player> closeAction) {
        this.closeAction = closeAction;
    }

    protected void onOpen(Player player){
        openAction.accept(player);
    }
    protected void onClose(Player player){
        closeAction.accept(player);
    }

    public void registerItem(int height, int width, GuiItem item){
        guiItems[height][width] = item;
    }

    public GuiItem getItem(int height, int width){
        return guiItems[height][width];
    }

    public void unregisterItem(int height, int width){
        guiItems[height][width] = null;
    }
}
