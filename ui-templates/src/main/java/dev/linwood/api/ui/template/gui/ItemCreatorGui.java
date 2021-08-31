package dev.linwood.api.ui.template.gui;

import dev.linwood.api.request.ChatRequest;
import dev.linwood.api.request.ValueRequest;
import dev.linwood.api.server.Version;
import dev.linwood.api.translations.Translation;
import dev.linwood.api.ui.ChestGui;
import dev.linwood.api.ui.item.StaticItem;
import dev.linwood.api.ui.template.item.TranslatedItem;
import dev.linwood.api.utils.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ItemCreatorGui extends ChestGui {
    private final @NotNull StaticItem previewStaticItem;
    private final @NotNull ItemStackBuilder itemStackBuilder;
    private Consumer<ItemStack> submitAction = (item) -> {
    };
    private Runnable cancelAction;

    public ItemCreatorGui(@NotNull ItemStack itemStack, @NotNull Translation translation) {
        super(translation.getTranslation("title"), 5);
        itemStackBuilder = new ItemStackBuilder(itemStack);
        previewStaticItem = new StaticItem(new ItemStackBuilder().build());
        previewStaticItem.setRenderAction((gui) -> {
            previewStaticItem.setItemStack(itemStackBuilder.build());
            reloadAll();
        });
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

        StaticItem backButton = new TranslatedItem(translation, new ItemStackBuilder(Material.BARRIER).setDisplayName("back").build());
        backButton.setClickAction(event -> {
            if (cancelAction == null)
                hide();
            cancelAction.run();
        });
        registerItem(0, 0, backButton);

        fillItems(1, 0, 7, 0, placeholder);

        registerItem(4, 0, previewStaticItem);
        registerItem(6, 0, new TranslatedItem(translation, new ItemStackBuilder(Material.PAPER).setDisplayName("nbt").build()) {{
            setClickAction(event -> {
                hide((Player) event.getWhoClicked());
                var request = new ChatRequest((Player) event.getWhoClicked());
                request.setSubmitAction(s -> {
                    show((Player) event.getWhoClicked());
                });
                request.setCancelAction(() -> show((Player) event.getWhoClicked()));
            });
        }});
        registerItem(8, 0, new TranslatedItem(translation, new ItemStackBuilder(Material.GREEN_DYE).setDisplayName("submit").build()) {{
            setClickAction(event -> submitAction.accept(itemStackBuilder.build()));
        }});
        addItem(new TranslatedItem(translation, new ItemStackBuilder(Material.NAME_TAG).setDisplayName("displayname.name").setLore("displayname.lore").build()) {{
            setRenderAction((gui) -> {
                        setPlaceholders(itemStackBuilder.getDisplayName());
                        reloadAll();
                    }
            );
            setClickAction(event -> {
                switch (event.getClick()) {
                    case LEFT:
                        Player player = (Player) event.getWhoClicked();
                        hide(player);
                        event.getWhoClicked().sendMessage(translation.getTranslation("displayname.message"));
                        ChatRequest request = new ChatRequest((Player) event.getWhoClicked());
                        request.setSubmitAction(output -> {
                            output = ChatColor.translateAlternateColorCodes('&', output);
                            itemStackBuilder.displayName(output);
                            player.sendMessage(translation.getTranslation("displayname.success", output));
                            reloadAll();
                            show(player);
                        });
                        request.setCancelAction(() -> {
                            player.sendMessage(translation.getTranslation("displayname.cancel"));
                            show(player);
                        });
                        break;
                    case RIGHT:
                        itemStackBuilder.displayName(null);
                        event.getWhoClicked().sendMessage(translation.getTranslation("displayname.remove"));
                        reloadAll();
                        break;
                }
            });
        }});
        addItem(new TranslatedItem(translation, new ItemStackBuilder(Material.BOOK).setDisplayName("lore.name").setLore("lore.lore").build()) {{
            setRenderAction((gui) -> {
                setPlaceholders(String.join("\n", itemStackBuilder.getLore()));
                reloadAll();
            });
            setClickAction(event -> {
                List<String> lore = itemStackBuilder.getLore();
                Player player = (Player) event.getWhoClicked();
                if (lore == null)
                    lore = new ArrayList<>();
                switch (event.getClick()) {
                    case LEFT:
                        hide(player);
                        event.getWhoClicked().sendMessage(translation.getTranslation("lore.message"));
                        ChatRequest request = new ChatRequest((Player) event.getWhoClicked());
                        List<String> finalLore = lore;
                        request.setSubmitAction(output -> {
                            output = ChatColor.translateAlternateColorCodes('&', output);
                            finalLore.add(output);
                            itemStackBuilder.lore(finalLore);

                            player.sendMessage(translation.getTranslation("lore.success", output));
                            show(player);
                            reloadAll();
                        });
                        request.setCancelAction(() -> {
                            player.sendMessage(translation.getTranslation("lore.cancel"));
                            show(player);
                        });
                        break;
                    case SHIFT_LEFT:
                        lore.add(" ");
                        event.getWhoClicked().sendMessage(translation.getTranslation("lore.empty"));
                        break;
                    case RIGHT:
                        if (!lore.isEmpty())
                            lore.remove(lore.size() - 1);
                        event.getWhoClicked().sendMessage(translation.getTranslation("lore.remove"));
                        break;
                    case SHIFT_RIGHT:
                        lore.clear();
                    case DROP:
                        if (lore.isEmpty())
                            event.getWhoClicked().sendMessage(translation.getTranslation("lore.null"));
                        else
                            event.getWhoClicked().sendMessage(translation.getTranslation("lore.get",
                                    String.join(translation.getTranslation("lore.delimiter"), lore)));
                }
                if (event.getClick() != ClickType.LEFT) {
                    itemStackBuilder.setLore(lore);
                    reloadAll();
                }
            });
        }});
        addItem(new TranslatedItem(translation, new ItemStackBuilder(Material.IRON_NUGGET).setDisplayName("amount.name").setLore("amount.lore").build()) {{
            setRenderAction((gui) -> {
                setPlaceholders(itemStackBuilder.getAmount());
                reloadAll();
            });
            setClickAction((event) -> {
                Player player = (Player) event.getWhoClicked();
                ValueRequest request = new ValueRequest(player, itemStackBuilder.getAmount());
                request.setSubmitAction(value -> {
                    itemStackBuilder.setAmount(Math.round(value));
                    reloadAll();
                });
            });
        }});
        ItemStackBuilder customModelDataItem = new ItemStackBuilder(Material.GOLD_INGOT);
        customModelDataItem.setDisplayName("custommodeldata.name");
        if (Version.getVersion().isBiggerThan(Version.v1_13))
            if (itemStackBuilder.getCustomModelData() != null)
                customModelDataItem.setLore("custommodeldata.lore");
            else
                customModelDataItem.setLore("custommodeldata.no");
        else
            customModelDataItem.setLore("custommodeldata.unavailable");
        addItem(new TranslatedItem(translation, customModelDataItem.build()) {{
            setClickAction(event -> {
                Player player = (Player) event.getWhoClicked();
                if (!Version.getVersion().isBiggerThan(Version.v1_13))
                    return;
                if (itemStackBuilder.getCustomModelData() != null) {
                    if (event.getClick() == ClickType.MIDDLE) {
                        itemStackBuilder.setCustomModelData(null);
                        reloadAll();
                    } else {
                        ValueRequest request = new ValueRequest(player, itemStackBuilder.getAmount());
                        request.setSubmitAction(value -> {
                            itemStackBuilder.setCustomModelData(Math.round(value));
                            reloadAll();
                        });
                    }
                } else {
                    itemStackBuilder.setCustomModelData(0);
                    reloadAll();
                }
            });
        }});
        addItem(new TranslatedItem(translation, new ItemStackBuilder(Material.BEDROCK).setDisplayName("unbreakable.name").setLore("unbreakable.lore").build()) {{
            setRenderAction((gui) -> {
                setPlaceholders("unbreakable." + (itemStackBuilder.isUnbreakable() ? "yes" : "no"));
                reloadAll();
            });
            setClickAction(event -> {
                itemStackBuilder.setUnbreakable(!itemStackBuilder.isUnbreakable());
                reloadAll();
            });
        }});
    }

    public void setCancelAction(Runnable cancelAction) {
        this.cancelAction = cancelAction;
    }

    public void setSubmitAction(Consumer<ItemStack> submitAction) {
        this.submitAction = submitAction;
    }
}
