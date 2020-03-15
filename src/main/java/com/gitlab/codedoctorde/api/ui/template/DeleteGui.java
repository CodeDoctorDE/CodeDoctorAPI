package com.gitlab.codedoctorde.api.ui.template;

import com.gitlab.codedoctorde.api.ui.Gui;
import com.gitlab.codedoctorde.api.ui.template.events.GuiDeleteEvent;
import com.gitlab.codedoctorde.api.utils.ItemStackBuilder;
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

    public Gui createGui(int index, Gui backGui) {
        return new Gui(plugin, deleteEvent.title(index), 3) {
            {
                getGuiItems().put(9 + 3, deleteEvent.yesItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("yes")), index));
                getGuiItems().put(9 + 5, deleteEvent.noItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("no")), index));
            }
        };
    }
}
