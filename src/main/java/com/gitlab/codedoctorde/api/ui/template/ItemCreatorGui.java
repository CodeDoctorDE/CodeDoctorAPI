package com.gitlab.codedoctorde.api.ui.template;

import com.gitlab.codedoctorde.api.request.ChatRequest;
import com.gitlab.codedoctorde.api.request.ChatRequestEvent;
import com.gitlab.codedoctorde.api.server.Version;
import com.gitlab.codedoctorde.api.ui.Gui;
import com.gitlab.codedoctorde.api.ui.GuiItem;
import com.gitlab.codedoctorde.api.ui.GuiItemEvent;
import com.gitlab.codedoctorde.api.utils.ItemStackBuilder;
import com.gitlab.codedoctorde.itemmods.main.ItemCreatorSubmitEvent;
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
import java.util.Objects;

public class ItemCreatorGui {

    private final JavaPlugin plugin;
    private ItemStackBuilder itemStackBuilder;
    private ItemCreatorSubmitEvent submitEvent;

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

    public Gui createGui(Gui backGui, JsonObject guiTranslation) {
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
            getGuiItems().put(4, new GuiItem(itemStackBuilder.build(), new GuiItemEvent() {

                @Override
                public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                    event.getWhoClicked().getInventory().addItem(Objects.requireNonNull(event.getCursor()));
                    if (event.getCursor() != null)
                        if (!event.getCursor().getType().isAir()) {
                            System.out.println("test");
                            itemStackBuilder.setItemStack(event.getCursor().clone());
                            event.setCurrentItem(new ItemStack(Material.AIR));
                            createGui(backGui, guiTranslation).open((Player) event.getWhoClicked());
                        } else
                            event.getWhoClicked().getInventory().addItem(itemStackBuilder.build());
                    else
                        event.getWhoClicked().getInventory().addItem(itemStackBuilder.build());
                }
            }));
            getGuiItems().put(5, placeholder);
            getGuiItems().put(6, placeholder);
            getGuiItems().put(7, placeholder);
            getGuiItems().put(8, new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("submit")).build(), new GuiItemEvent() {
                @Override
                public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                    event.getWhoClicked().sendMessage(guiTranslation.getAsJsonObject("submit").get("success").getAsString());
                    submitEvent.onEvent(itemStackBuilder.build());
                }
            }));
            getGuiItems().put(9, new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("displayname")).format(itemStackBuilder.getDisplayName()).build(), new GuiItemEvent() {
                @Override
                public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
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
                                    createGui(backGui, guiTranslation).open(player);
                                }

                                @Override
                                public void onCancel(Player player) {
                                    player.sendMessage(guiTranslation.getAsJsonObject("displayname").get("cancel").getAsString());
                                    createGui(backGui, guiTranslation).open(player);
                                }
                            });
                            break;
                        case RIGHT:
                            itemStackBuilder.displayName(null);
                            event.getWhoClicked().sendMessage(guiTranslation.getAsJsonObject("displayname").get("remove").getAsString());
                            createGui(backGui, guiTranslation).open((Player) event.getWhoClicked());
                            break;
                    }
                }
            }));
            getGuiItems().put(10, new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("lore")).build(), new GuiItemEvent() {
                @Override
                public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
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
                                    createGui(backGui, guiTranslation).open(player);
                                }

                                @Override
                                public void onCancel(Player player) {
                                    player.sendMessage(guiTranslation.getAsJsonObject("lore").get("cancel").getAsString());
                                    createGui(backGui, guiTranslation).open(player);
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
                    if (event.getClick() != ClickType.LEFT)
                        gui.reload();
                }
            }));
            getGuiItems().put(11, new GuiItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("amount")).format(itemStackBuilder.getAmount()).build(), new GuiItemEvent() {
                @Override
                public void onEvent(Gui gui, GuiItem guiItem, InventoryClickEvent event) {
                    int amount = itemStackBuilder.getAmount();
                    switch (event.getClick()) {
                        case LEFT:
                            amount++;
                            break;
                        case RIGHT:
                            amount--;
                            break;
                        case SHIFT_LEFT:
                            amount += 5;
                            break;
                        case SHIFT_RIGHT:
                            amount -= 5;
                            break;
                        case DROP:
                            amount = 1;
                            break;
                    }
                    itemStackBuilder.setAmount(amount);
                    event.getWhoClicked().sendMessage(MessageFormat.format(guiTranslation.getAsJsonObject("amount").get("success").getAsString(), amount));
                    guiItem.setItemStack(new ItemStackBuilder(guiTranslation.getAsJsonObject("amount")).format(itemStackBuilder.getAmount()).build());
                    gui.reload();
                }
            }));
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
        }};
    }
}
