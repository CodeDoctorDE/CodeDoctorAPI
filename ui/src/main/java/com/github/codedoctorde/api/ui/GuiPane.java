package com.github.codedoctorde.api.ui;

import com.github.codedoctorde.api.ui.item.GuiItem;
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

    public void registerItem(int x, int y, @Nullable GuiItem item) {
        guiItems[x][y] = item;
    }

    public GuiItem getItem(int x, int y) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight())
            return guiItems[x][y];
        return null;
    }

    public void unregisterItem(int x, int y) {
        if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight())
            guiItems[x][y] = null;
    }

    public void fillItems(int startX, int startY, int endX, int endY, @Nullable GuiItem item) {
        for (int x = startX; x < endX; x++) for (int y = startY; y < endY; y++) registerItem(x, y, item);
    }

    public boolean containsItem(int x, int y) {
        if (x >= 0 && x < getWidth() && y >= 0 && y <getHeight())
            return guiItems[x][y] != null;
        return false;
    }

    public void addItem(@NotNull GuiItem item) {
        for (int y = 0; y < getHeight(); y++)
            for (int x = 0; x < getWidth(); x++)
                if (!containsItem(x, y)) {
                    registerItem(x, y, item);
                    return;
                }
    }

    public int getItemCount() {
        int count = 0;
        for (int x = 0; x < guiItems.length; x++) {
            for (int y = 0; y < guiItems[x].length; y++) {
                if (containsItem(x, y))
                    count++;
            }
        }
        return count;
    }

    public void addPane(GuiPane pane) {
        addPane(0, 0, pane);
    }

    public void addPane(int offsetX, int offsetY, GuiPane pane) {
        for (int x = 0; x < pane.guiItems.length; x++)
            for (int y = 0; y < pane.guiItems[x].length; y++)
                if (pane.containsItem(x, y))
                    registerItem(offsetX + x, offsetY + y, pane.getItem(x, y));
    }

    public int getWidth() {
        return guiItems.length;
    }

    public int getHeight() {
        return guiItems.length > 0 ? guiItems[0].length : 0;
    }

    public GuiPane offset(int x, int y) {
        GuiPane pane = new GuiPane(getWidth() + x, getHeight() + y);
        for (int i = 0; i < getWidth(); i++)
            for (int j = 0; j < getHeight(); j++) pane.registerItem(i + x, j + y, guiItems[i][j]);
        return pane;
    }
}
