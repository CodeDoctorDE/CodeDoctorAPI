package com.github.codedoctorde.api.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Consumer;

public abstract class Gui {
    private Consumer<Player> openAction;
    private Consumer<Player> closeAction;
    protected final GuiItem[][] guiItems;
    private static final Map<UUID, Gui> playerGuis = new HashMap<>();

    public Gui(int width, int height){
        guiItems = new GuiItem[width][height];
        GuiListener.register();
    }

    public static Gui getGui(Player player) {
        return playerGuis.get(player.getUniqueId());
    }

    public void show(Player... players){
        for (Player player : players) {
            if(hasGui(player))
                getGui(player).hide();
            playerGuis.put(player.getUniqueId(), this);
            register(player);
        }
    }
    public void hide(Player... players){
        for (Player player : players) {
            if(playerGuis.containsKey(player.getUniqueId())) {
                playerGuis.remove(player.getUniqueId());
                unregister(player);
            }
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
        return playerGuis.entrySet().stream().filter(uuidGuiEntry -> uuidGuiEntry.getValue().equals(this)).map(Map.Entry::getKey).map(Bukkit::getPlayer).toArray(Player[]::new);
    }

    public static boolean hasGui(Player player){
        return playerGuis.containsKey(player.getUniqueId());
    }

    public static void hideAll(Player... players){
        Arrays.stream(players).filter(Gui::hasGui).forEach(player -> getGui(player).hide(player));
    }

    public boolean hasCurrentGui(final Player player) {
        if(!playerGuis.containsKey(player.getUniqueId()))
            return false;
        return playerGuis.get(player.getUniqueId()).equals(this);
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

    public void registerItem(int x, int y, GuiItem item){
        guiItems[x][y] = item;
    }

    public GuiItem getItem(int x, int y){
        return guiItems[x][y];
    }

    public void unregisterItem(int x, int y){
        guiItems[x][y] = null;
    }
}
