package tk.codedoctor.minecraft.ui.template.gui;

import tk.codedoctor.minecraft.ui.Gui;
import tk.codedoctor.minecraft.ui.template.gui.events.GuiDeleteEvent;
import tk.codedoctor.minecraft.utils.ItemStackBuilder;
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

    public Gui createGui(int index) {
        return new Gui(plugin, deleteEvent.title(index), 3) {
            {
                getGuiItems().put(9 + 3, deleteEvent.yesItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("yes")), index));
                getGuiItems().put(9 + 5, deleteEvent.noItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("no")), index));
            }
        };
    }
}
