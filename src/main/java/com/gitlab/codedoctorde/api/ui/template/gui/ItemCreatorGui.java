package com.gitlab.codedoctorde.api.ui.template.gui;

import com.gitlab.codedoctorde.api.request.ChatRequest;
import com.gitlab.codedoctorde.api.request.ChatRequestEvent;
import com.gitlab.codedoctorde.api.server.Version;
import com.gitlab.codedoctorde.api.ui.Gui;
import com.gitlab.codedoctorde.api.ui.GuiItem;
import com.gitlab.codedoctorde.api.ui.GuiItemEvent;
import com.gitlab.codedoctorde.api.ui.template.item.InputItem;
import com.gitlab.codedoctorde.api.ui.template.item.ValueItem;
import com.gitlab.codedoctorde.api.ui.template.gui.events.ItemCreatorSubmitEvent;
import com.gitlab.codedoctorde.api.ui.template.item.events.InputItemEvent;
import com.gitlab.codedoctorde.api.ui.template.item.events.ValueItemEvent;
import com.gitlab.codedoctorde.api.utils.ItemStackBuilder;
import com.google.gson.JsonObject;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class ItemCreatorGui {

    private final JavaPlugin plugin;
    private final ItemStackBuilder itemStackBuilder;
    private final ItemCreatorSubmitEvent submitEvent;
    private GuiItem previewGuiItem;

    public ItemCreatorGui(JavaPlugin plugin, ItemStackBuilder itemStackBuilder, ItemCreatorSubmitEvent submitEvent) {
        this.itemStackBuilder = itemStackBuilder;
        this.plugin = plugin;
        this.submitEvent = submitEvent;
    }

    public ItemCreatorGui(JavaPlugin plugin, ItemStack itemStack, ItemCreatorSubmitEvent submitEvent) {
        this.plugin = plugin;
        this.itemStackBuilder = new ItemStackBuilder(itemStack);
        this.submitEvent = submitEvent;
    }

    public void rebuildItemStack(Gui gui){
        previewGuiItem.setItemStack(itemStackBuilder.build());
        gui.reload(4);
    }

    public Gui createGui(Gui backGui, JsonObject guiTranslation) {
        previewGuiItem = new GuiItem(itemStackBuilder.build(), new GuiItemEvent() {

            @Override
            public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                if (event.getCursor() != null)
                    if (event.getCursor().getType() != Material.AIR) {
                        itemStackBuilder.setItemStack(event.getCursor().clone());
                        event.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
                        rebuildItemStack(gui);
                    } else
                        event.getWhoClicked().getInventory().addItem(itemStackBuilder.build());
                else
                    event.getWhoClicked().getInventory().addItem(itemStackBuilder.build());
            }
        });
        return new Gui(plugin, guiTranslation.get("title").getAsString(), 5) {{
            GuiItem placeholder = new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("placeholder")).build());
            getGuiItems().put(0, new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("back")).build(), new GuiItemEvent() {
                @Override
                public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                    backGui.open((Player) event.getWhoClicked());
                }
            }));
            getGuiItems().put(1, placeholder);
            getGuiItems().put(2, placeholder);
            getGuiItems().put(3, placeholder);
            getGuiItems().put(4, previewGuiItem);
            getGuiItems().put(5, placeholder);
            getGuiItems().put(6, new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("nbt")), new GuiItemEvent() {
                @Override
                public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                    
                }
            }));
            getGuiItems().put(7, placeholder);
            getGuiItems().put(8, new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("submit")).build(), new GuiItemEvent() {
                @Override
                public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                    event.getWhoClicked().sendMessage(guiTranslation.getAsJsonObject("submit").get("success").getAsString());
                    submitEvent.onEvent(itemStackBuilder.build());
                }
            }));
            getGuiItems().put(9, new InputItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("displayname")).build(), new InputItemEvent() {
                @Override
                public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event, InputItem inputItem) {
                    switch (event.getClick()) {
                        case LEFT:
                            gui.close((Player) event.getWhoClicked());
                            event.getWhoClicked().sendMessage(guiTranslation.getAsJsonObject("displayname").get("message").getAsString());
                            new ChatRequest(plugin, (Player) event.getWhoClicked(), new ChatRequestEvent() {
                                @Override
                                public void onEvent(Player player, String output) {
                                    output = ChatColor.translateAlternateColorCodes('&', output);
                                    itemStackBuilder.displayName(output);
                                    player.sendMessage(MessageFormat.format(guiTranslation.getAsJsonObject("displayname").get("success").getAsString(), output));
                                    inputItem.setFormat(itemStackBuilder.getDisplayName());
                                    guiItem.setItemStack(inputItem.getFormattedItemStack());
                                    rebuildItemStack(gui);
                                    gui.open(player);
                                }

                                @Override
                                public void onCancel(Player player) {
                                    player.sendMessage(guiTranslation.getAsJsonObject("displayname").get("cancel").getAsString());
                                    gui.open(player);
                                }
                            });
                            break;
                        case RIGHT:
                            itemStackBuilder.displayName(null);
                            event.getWhoClicked().sendMessage(guiTranslation.getAsJsonObject("displayname").get("remove").getAsString());
                            inputItem.setFormat(itemStackBuilder.getDisplayName());
                            guiItem.setItemStack(inputItem.getFormattedItemStack());
                            rebuildItemStack(gui);
                            break;
                    }
                }
            }).setFormat(itemStackBuilder.getDisplayName()).build());
            getGuiItems().put(10, new InputItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("lore")).build(), new InputItemEvent() {
                @Override
                public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event, InputItem inputItem) {
                    List<String> lore = itemStackBuilder.getLore();
                    if (lore == null)
                        lore = new ArrayList<>();
                    switch (event.getClick()) {
                        case LEFT:
                            gui.close((Player) event.getWhoClicked());
                            event.getWhoClicked().sendMessage(guiTranslation.getAsJsonObject("lore").get("message").getAsString());
                            new ChatRequest(plugin, (Player) event.getWhoClicked(), new ChatRequestEvent() {
                                @Override
                                public void onEvent(Player player, String output) {
                                    output = ChatColor.translateAlternateColorCodes('&', output);
                                    List<String> lore = itemStackBuilder.getLore();
                                    if (lore == null)
                                        lore = new ArrayList<>();
                                    lore.add(output);
                                    itemStackBuilder.lore(lore);

                                    player.sendMessage(MessageFormat.format(guiTranslation.getAsJsonObject("lore").get("success").getAsString(), output));
                                    gui.open(player);
                                    rebuildItemStack(gui);
                                }

                                @Override
                                public void onCancel(Player player) {
                                    player.sendMessage(guiTranslation.getAsJsonObject("lore").get("cancel").getAsString());
                                    gui.open(player);
                                }
                            });
                            break;
                        case SHIFT_LEFT:
                            lore.add(" ");
                            event.getWhoClicked().sendMessage(guiTranslation.getAsJsonObject("lore").get("empty").getAsString());
                            break;
                        case RIGHT:
                            if (!lore.isEmpty())
                                lore.remove(lore.size() - 1);
                            event.getWhoClicked().sendMessage(guiTranslation.getAsJsonObject("lore").get("remove").getAsString());
                            break;
                        case SHIFT_RIGHT:
                            lore.clear();
                        case DROP:
                            if (lore.isEmpty())
                                event.getWhoClicked().sendMessage(guiTranslation.getAsJsonObject("lore").get("null").getAsString());
                            else
                                event.getWhoClicked().sendMessage(MessageFormat.format(guiTranslation.getAsJsonObject("lore").get("get").getAsString(),
                                        String.join(guiTranslation.getAsJsonObject("lore").get("delimiter").getAsString(), lore)));
                    }
                    itemStackBuilder.setLore(lore);
                    inputItem.setFormat(String.join("\n", itemStackBuilder.getLore()));
                    if (event.getClick() != ClickType.LEFT)
                        gui.reload();
                }
            }).setFormat(String.join("\n", itemStackBuilder.getLore())).build());
            getGuiItems().put(11, new ValueItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("amount")), itemStackBuilder.getAmount(), 1, new ValueItemEvent() {
                @Override
                public boolean onEvent(float value, Player player, ValueItem valueItem) {
                    if(value < 1)
                    return false;
                    itemStackBuilder.setAmount((int) value);
                    return true;
                }
            }).build());
            getGuiItems().put(12, new GuiItem((Version.getVersion().isBiggerThan(Version.v1_13)) ?
                    ((itemStackBuilder.getCustomModelData() != null) ? new ItemStackBuilder(guiTranslation.getAsJsonObject("custommodeldata").getAsJsonObject("yes")).format(itemStackBuilder.getCustomModelData()).build() :
                            new ItemStackBuilder(guiTranslation.getAsJsonObject("custommodeldata").getAsJsonObject("no")).build()) : new ItemStackBuilder(guiTranslation.getAsJsonObject("custommodeldata").getAsJsonObject("unavailable")).build(), new GuiItemEvent() {
                @Override
                public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                    if (!Version.getVersion().isBiggerThan(Version.v1_13))
                        return;
                    if (itemStackBuilder.getCustomModelData() != null) {
                        Integer customModelData = itemStackBuilder.getCustomModelData();
                        switch (event.getClick()) {
                            case LEFT:
                                customModelData++;
                                break;
                            case RIGHT:
                                customModelData--;
                                break;
                            case SHIFT_LEFT:
                                customModelData += 5;
                                break;
                            case SHIFT_RIGHT:
                                customModelData -= 5;
                                break;
                            case DROP:
                                customModelData = null;
                                break;
                        }
                        itemStackBuilder.setCustomModelData(customModelData);
                        event.getWhoClicked().sendMessage(MessageFormat.format(guiTranslation.getAsJsonObject("custommodeldata").get("success").getAsString(), customModelData));
                    } else {
                        itemStackBuilder.setCustomModelData(0);
                        event.getWhoClicked().sendMessage(MessageFormat.format(guiTranslation.getAsJsonObject("custommodeldata").get("success").getAsString(), itemStackBuilder.getCustomModelData()));
                    }
                    guiItem.setItemStack(itemStackBuilder.getCustomModelData() != null ? new ItemStackBuilder(guiTranslation.getAsJsonObject("custommodeldata").getAsJsonObject("yes")).format(itemStackBuilder.getCustomModelData()).build() :
                            new ItemStackBuilder(guiTranslation.getAsJsonObject("custommodeldata").getAsJsonObject("no")).build());
                    gui.reload();
                }
            }));
            getGuiItems().put(13, new GuiItem(guiTranslation.getAsJsonObject("unbreakable").getAsJsonObject(itemStackBuilder.isUnbreakable() ? "unbreakable" : "breakable"), new GuiItemEvent() {
                @Override
                public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                    itemStackBuilder.setUnbreakable(!itemStackBuilder.isUnbreakable());
                    guiItem.setItemStack(new ItemStackBuilder(guiTranslation.getAsJsonObject("unbreakable").getAsJsonObject(itemStackBuilder.isUnbreakable() ? "unbreakable" : "breakable")).build());
                    gui.reload();
                }
            }));
        }};
    }
}
