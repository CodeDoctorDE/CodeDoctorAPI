package com.github.codedoctorde.api.ui.template.gui;

import com.github.codedoctorde.api.request.ChatRequest;
import com.github.codedoctorde.api.translations.Translation;
import com.github.codedoctorde.api.ui.StaticItem;
import com.github.codedoctorde.api.ui.template.item.InputItem;
import com.github.codedoctorde.api.ui.template.item.TranslationItem;
import com.github.codedoctorde.api.ui.template.item.ValueItem;
import com.github.codedoctorde.api.ui.template.item.events.InputItemEvent;
import com.github.codedoctorde.api.ui.template.item.events.ValueItemEvent;
import com.github.codedoctorde.api.server.Version;
import com.github.codedoctorde.api.ui.ChestGui;
import com.github.codedoctorde.api.utils.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ItemCreatorGui extends ChestGui {
    private StaticItem previewStaticItem;
    private ItemStackBuilder itemStackBuilder;
    private Consumer<ItemStack> onSubmit;
    private Runnable onCancel;

    public void setOnCancel(Runnable onCancel) {
        this.onCancel = onCancel;
    }

    public void setOnSubmit(Consumer<ItemStack> onSubmit) {
        this.onSubmit = onSubmit;
    }

    public ItemCreatorGui(ItemStack itemStack, Translation translation, String namespace) {
        super(translation.getTranslation(namespace + ".title"), 5);
        itemStackBuilder = new ItemStackBuilder(itemStack);
        previewStaticItem = new StaticItem(itemStackBuilder.build());
        previewStaticItem.setClickAction(event -> {
            if (event.getCursor() != null)
                if (event.getCursor().getType() != Material.AIR) {
                    itemStackBuilder.setItemStack(event.getCursor().clone());
                    event.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
                } else
                    event.getWhoClicked().getInventory().addItem(itemStackBuilder.build());
            else
                event.getWhoClicked().getInventory().addItem(itemStackBuilder.build());
        });
        StaticItem placeholder = new StaticItem(new ItemStackBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(" ").build());

        StaticItem backButton = new TranslationItem(translation, new ItemStackBuilder(Material.BARRIER).setDisplayName("back").build());
        backButton.setClickAction(event -> {
            if(onCancel == null)
                hide();
            onCancel.run();
        });
        registerItem(0, 0, backButton);

        fillItems(1, 0, 7, 0, placeholder);

        registerItem(4, 0, previewStaticItem);
        registerItem(6, 0, new TranslationItem(translation, new ItemStackBuilder(Material.PAPER).setDisplayName("paper").build()));
        registerItem(8, 0, new TranslationItem(translation, new ItemStackBuilder(Material.GREEN_DYE).setDisplayName("submit").build()));
        addItem(new TranslationItem(translation, new ItemStackBuilder(Material.NAME_TAG).setDisplayName("displayname.name").setLore("displayname.lore").build()){{
            registerPlaceholders(itemStackBuilder.getDisplayName());
            setClickAction(event -> {
                switch (event.getClick()) {
                    case LEFT:
                        hide((Player) event.getWhoClicked());
                        event.getWhoClicked().sendMessage(translation.getTranslation("displayname.message"));
                        new ChatRequest(plugin, (Player) event.getWhoClicked(), new RequestEvent<String>() {
                            @Override
                            public void onEvent(Player player, String output) {
                                output = ChatColor.translateAlternateColorCodes('&', output);
                                itemStackBuilder.displayName(output);
                                player.sendMessage(MessageFormat.format(guiTranslation.getAsJsonObject("displayname").get("success").getAsString(), output));
                                inputItem.setFormat(itemStackBuilder.getDisplayName());
                                staticItem.setItemStack(inputItem.getFormattedItemStack());
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
                        staticItem.setItemStack(inputItem.getFormattedItemStack());
                        rebuildItemStack(gui);
                        break;
                }
            });
        }});



        return new ChestGui(plugin, guiTranslation.get("title").getAsString(), 5) {{
            getGuiItems().put(0, new StaticItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("back")).build(), new GuiItemEvent() {
                @Override
                public void onEvent(ChestGui gui, StaticItem staticItem, InventoryClickEvent event) {
                    creatorEvent.onCancel((Player) event.getWhoClicked());
                }
            }));
            getGuiItems().put(1, placeholder);
            getGuiItems().put(2, placeholder);
            getGuiItems().put(3, placeholder);
            getGuiItems().put(4, previewStaticItem);
            getGuiItems().put(5, placeholder);
            getGuiItems().put(6, new StaticItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("nbt")), new GuiItemEvent() {
                @Override
                public void onEvent(ChestGui gui, StaticItem staticItem, InventoryClickEvent event) {
                    
                }
            }));
            getGuiItems().put(7, placeholder);
            getGuiItems().put(8, new StaticItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("submit")).build(), new GuiItemEvent() {
                @Override
                public void onEvent(ChestGui gui, StaticItem staticItem, InventoryClickEvent event) {
                    event.getWhoClicked().sendMessage(guiTranslation.getAsJsonObject("submit").get("success").getAsString());
                    creatorEvent.onEvent((Player) event.getWhoClicked(), itemStackBuilder.build());
                }
            }));
            getGuiItems().put(9, new InputItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("displayname")).build(), new InputItemEvent() {
                @Override
                public void onEvent(ChestGui gui, StaticItem staticItem, InventoryClickEvent event, InputItem inputItem) {
                    switch (event.getClick()) {
                        case LEFT:
                            gui.close((Player) event.getWhoClicked());
                            event.getWhoClicked().sendMessage(guiTranslation.getAsJsonObject("displayname").get("message").getAsString());
                            new ChatRequest(plugin, (Player) event.getWhoClicked(), new RequestEvent<String>() {
                                @Override
                                public void onEvent(Player player, String output) {
                                    output = ChatColor.translateAlternateColorCodes('&', output);
                                    itemStackBuilder.displayName(output);
                                    player.sendMessage(MessageFormat.format(guiTranslation.getAsJsonObject("displayname").get("success").getAsString(), output));
                                    inputItem.setFormat(itemStackBuilder.getDisplayName());
                                    staticItem.setItemStack(inputItem.getFormattedItemStack());
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
                            staticItem.setItemStack(inputItem.getFormattedItemStack());
                            rebuildItemStack(gui);
                            break;
                    }
                }
            }).setFormat(itemStackBuilder.getDisplayName()).build());
            getGuiItems().put(10, new InputItem(new ItemStackBuilder(guiTranslation.getAsJsonObject("lore")).build(), new InputItemEvent() {
                @Override
                public void onEvent(ChestGui gui, StaticItem staticItem, InventoryClickEvent event, InputItem inputItem) {
                    List<String> lore = itemStackBuilder.getLore();
                    if (lore == null)
                        lore = new ArrayList<>();
                    switch (event.getClick()) {
                        case LEFT:
                            gui.close((Player) event.getWhoClicked());
                            event.getWhoClicked().sendMessage(guiTranslation.getAsJsonObject("lore").get("message").getAsString());
                            new ChatRequest(plugin, (Player) event.getWhoClicked(), new RequestEvent<String>() {
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
                    staticItem.setItemStack(inputItem.getFormattedItemStack());
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
            getGuiItems().put(12, new StaticItem((Version.getVersion().isBiggerThan(Version.v1_13)) ?
                    ((itemStackBuilder.getCustomModelData() != null) ? new ItemStackBuilder(guiTranslation.getAsJsonObject("custommodeldata").getAsJsonObject("yes")).format(itemStackBuilder.getCustomModelData()).build() :
                            new ItemStackBuilder(guiTranslation.getAsJsonObject("custommodeldata").getAsJsonObject("no")).build()) : new ItemStackBuilder(guiTranslation.getAsJsonObject("custommodeldata").getAsJsonObject("unavailable")).build(), new GuiItemEvent() {
                @Override
                public void onEvent(ChestGui gui, StaticItem staticItem, InventoryClickEvent event) {
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
                    staticItem.setItemStack(itemStackBuilder.getCustomModelData() != null ? new ItemStackBuilder(guiTranslation.getAsJsonObject("custommodeldata").getAsJsonObject("yes")).format(itemStackBuilder.getCustomModelData()).build() :
                            new ItemStackBuilder(guiTranslation.getAsJsonObject("custommodeldata").getAsJsonObject("no")).build());
                    gui.reload();
                }
            }));
            getGuiItems().put(13, new StaticItem(guiTranslation.getAsJsonObject("unbreakable").getAsJsonObject(itemStackBuilder.isUnbreakable() ? "unbreakable" : "breakable"), new GuiItemEvent() {
                @Override
                public void onEvent(ChestGui gui, StaticItem staticItem, InventoryClickEvent event) {
                    itemStackBuilder.setUnbreakable(!itemStackBuilder.isUnbreakable());
                    staticItem.setItemStack(new ItemStackBuilder(guiTranslation.getAsJsonObject("unbreakable").getAsJsonObject(itemStackBuilder.isUnbreakable() ? "unbreakable" : "breakable")).build());
                    gui.reload();
                }
            }));
        }};
    }
}
