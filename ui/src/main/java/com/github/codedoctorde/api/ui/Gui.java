package com.github.codedoctorde.api.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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


    public void show(@NotNull Player... players){
        for (Player player : players) {
            if(hasGui(player))
                getGui(player).hide();
            playerGuis.put(player.getUniqueId(), this);
            register(player);
        }
    }
    public void hide(@NotNull Player... players){
        for (Player player : players) {
            if(playerGuis.containsKey(player.getUniqueId())) {
                playerGuis.remove(player.getUniqueId());
                unregister(player);
            }
        }
    }
    protected void register(@NotNull Player player) {

    }
    protected void unregister(@NotNull Player player){

    }
    public void reload(){
        reload(getOpenedPlayers());
    }
    public abstract void reload(@NotNull Player... players);

    public Player[] getOpenedPlayers() {
        return playerGuis.entrySet().stream().filter(uuidGuiEntry -> uuidGuiEntry.getValue().equals(this)).map(Map.Entry::getKey).map(Bukkit::getPlayer).toArray(Player[]::new);
    }

    public static Gui getGui(@NotNull Player player) {
        return playerGuis.get(player.getUniqueId());
    }
    public static boolean hasGui(@NotNull Player player){
        return playerGuis.containsKey(player.getUniqueId());
    }

    public static void hideAll(@NotNull Player... players){
        Arrays.stream(players).filter(Gui::hasGui).forEach(player -> getGui(player).hide(player));
    }

    public boolean hasCurrentGui(@NotNull final Player player) {
        if(!playerGuis.containsKey(player.getUniqueId()))
            return false;
        return playerGuis.get(player.getUniqueId()).equals(this);
    }
    public void setOpenAction(@Nullable Consumer<Player> openAction) {
        this.openAction = openAction;
    }

    public void setCloseAction(@Nullable Consumer<Player> closeAction) {
        this.closeAction = closeAction;
    }

    protected void onOpen(@NotNull Player player){
        openAction.accept(player);
    }
    protected void onClose(@NotNull Player player){
        closeAction.accept(player);
    }

    public void registerItem(int x, int y, @Nullable GuiItem item){
        guiItems[x][y] = item;
    }

    public GuiItem getItem(int x, int y){
        return guiItems[x][y];
    }

    public void unregisterItem(int x, int y){
        guiItems[x][y] = null;
    }

    public void fillItems(int startX, int startY, int endX, int endY, @Nullable GuiItem item) {
        for (int x = startX; x < endX; x++) for (int y = startY; y < endY; y++) registerItem(x, y, item);
    }

    public boolean containsItem(int x, int y){
        return guiItems[x][y] != null;
    }

    public void addItem(@NotNull GuiItem item){
        for (int x = 0; x < guiItems.length; x++)
            for (int y = 0; y < guiItems[x].length; y++)
                if (!containsItem(x, y)) {
                    registerItem(x, y, item);
                    return;
                }
    }
}
