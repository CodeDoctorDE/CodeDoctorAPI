package com.github.codedoctorde.api.ui.template.gui;

import com.github.codedoctorde.api.ui.ChestGui;
import com.github.codedoctorde.api.ui.template.gui.events.GuiDeleteEvent;
import com.github.codedoctorde.api.utils.ItemStackBuilder;
import com.google.gson.JsonObject;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author CodeDoctorDE
 */
public class DeleteGui {
    private final JsonObject guiTranslation;
    private final JavaPlugin plugin;
    private final GuiDeleteEvent deleteEvent;

    public DeleteGui(JavaPlugin plugin, JsonObject guiTranslation, GuiDeleteEvent deleteEvent) {
        this.plugin = plugin;
        this.guiTranslation = guiTranslation;
        this.deleteEvent = deleteEvent;
    }

    public ChestGui createGui(int index) {
        return new ChestGui(plugin, deleteEvent.title(index), 3) {
            {
                getGuiItems().put(9 + 3, deleteEvent.yesItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("yes")), index));
                getGuiItems().put(9 + 5, deleteEvent.noItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("no")), index));
            }
        };
    }
}