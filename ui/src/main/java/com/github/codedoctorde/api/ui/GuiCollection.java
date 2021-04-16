package com.github.codedoctorde.api.ui;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public class GuiCollection {
    private int current;
    private final List<Gui> guis = new ArrayList<>();


    public void registerGui(Gui gui) {
        guis.add(gui);
    }

    public void unregisterGui(Gui gui) {
        guis.remove(gui);
    }

    public void show(Player... players) {
        if(hasAccess())
            getGui().show(players);
    }

    public void hide(Player... players) {
        if(hasAccess())
            getGui().hide(players);
    }

    public Player[] getOpenedPlayers() {
        if(hasAccess())
            return getGui().getOpenedPlayers();
        return new Player[0];
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

    public int getCurrent() {
        return current;
    }
    public void next() {
        current++;
    }
    public void previous() {
        current--;
    }
    public void toFirst() {
        current = 0;
    }
    public void toLast() {
        current = guis.size() - 1;
    }

    public boolean isFirst() {
        return current == 0;
    }

    public boolean isLast() {
        return current == guis.size() - 1;
    }
}
