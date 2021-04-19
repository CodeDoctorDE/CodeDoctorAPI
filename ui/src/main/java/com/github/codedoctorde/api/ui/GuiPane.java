package com.github.codedoctorde.api.ui;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author CodeDoctorDE
 */
public class GuiPane {
    protected final GuiItem[][] guiItems;

    public GuiPane(int width, int height) {
        guiItems = new GuiItem[width][height];
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

    public int getItemCount(){
        int count = 0;
        for (int x = 0; x < guiItems.length; x++) {
            for (int y = 0; y < guiItems[x].length; y++) {
                if(containsItem(x, y))
                    count++;
            }
        }
        return count;
    }

    public void addPane(GuiPane pane){
        for (int x = 0; x < pane.guiItems.length; x++)
            for (int y = 0; y < pane.guiItems[x].length; y++)
                if (pane.containsItem(x, y))
                    addItem(pane.getItem(x, y));
    }
}
