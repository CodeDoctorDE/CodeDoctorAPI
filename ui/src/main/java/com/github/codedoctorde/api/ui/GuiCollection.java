package com.github.codedoctorde.api.ui;

import com.github.codedoctorde.api.ui.item.GuiItem;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author CodeDoctorDE
 */
public class GuiCollection extends Gui {
    protected final List<Gui> guis = new ArrayList<>();
    private int current;

    public GuiCollection() {
        super(0, 0);
    }

    public void registerGui(Gui gui) {
        guis.add(gui);
    }

    public void unregisterGui(Gui gui) {
        guis.remove(gui);
    }

    public void clearGuis() {
        guis.clear();
    }

    @Override
    public void show(Player... players) {
        if (hasAccess())
            getGui().show(players);
    }

    @Override
    public void hide(Player... players) {
        if (hasAccess())
            getGui().hide(players);
    }

    @Override
    public void reload(@NotNull Player... players) {
        if (hasAccess())
            getGui().reload(players);
    }

    @Override
    public Player[] getOpenedPlayers() {
        if (hasAccess())
            return getGui().getOpenedPlayers();
        return new Player[0];
    }

    @Override
    public boolean hasCurrentGui(@NotNull Player player) {
        if (hasAccess())
            return super.hasCurrentGui(player);
        return false;
    }

    public Gui[] getGuis() {
        return guis.toArray(new Gui[0]);
    }

    public Gui getGui() {
        return guis.get(current);
    }

    public boolean hasGui(Gui gui) {
        return guis.contains(gui);
    }

    public boolean hasAccess() {
        return current >= 0 && current < guis.size();
    }

    public void setOpenAction(@Nullable Consumer<Player> openAction) {
        if (hasAccess())
            getGui().setOpenAction(openAction);
    }

    public void setCloseAction(@Nullable Consumer<Player> closeAction) {
        if (hasAccess())
            getGui().setCloseAction(closeAction);
    }

    public int getCurrent() {
        return current;
    }

    public void next() {
        Player[] players = getOpenedPlayers();
        current++;
        show(players);
    }

    public void previous() {
        Player[] players = getOpenedPlayers();
        current--;
        show(players);
    }

    public void toFirst() {
        Player[] players = getOpenedPlayers();
        current = 0;
        show(players);
    }

    public void toLast() {
        Player[] players = getOpenedPlayers();
        current = guis.size() - 1;
        show(players);
    }

    @Override
    public int getHeight() {
        return getGui().getHeight();
    }

    @Override
    public int getWidth() {
        return getGui().getWidth();
    }

    @Override
    public void registerItem(int x, int y, GuiItem item) {
        if (hasAccess())
            getGui().registerItem(x, y, item);
    }

    @Override
    public void unregisterItem(int x, int y) {
        if (hasAccess())
            getGui().unregisterItem(x, y);
    }

    public boolean isFirst() {
        return current == 0;
    }

    public boolean isLast() {
        return current == guis.size() - 1;
    }
}
