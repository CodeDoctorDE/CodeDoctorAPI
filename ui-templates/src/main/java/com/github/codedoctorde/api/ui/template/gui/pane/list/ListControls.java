package com.github.codedoctorde.api.ui.template.gui.pane.list;

import com.github.codedoctorde.api.request.ChatRequest;
import com.github.codedoctorde.api.ui.GuiPane;
import com.github.codedoctorde.api.ui.item.StaticItem;
import com.github.codedoctorde.api.ui.template.gui.ListGui;
import com.github.codedoctorde.api.ui.template.item.TranslatedItem;
import com.github.codedoctorde.api.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class ListControls {
    private final boolean detailed;
    protected Consumer<InventoryClickEvent> backAction = (event) -> {
    };
    protected Consumer<InventoryClickEvent> createAction;
    private int offsetX, offsetY;

    public ListControls(boolean detailed) {
        this.detailed = detailed;
    }

    public ListControls() {
        this(false);
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public boolean isDetailed() {
        return detailed;
    }

    public void setCreateAction(Consumer<InventoryClickEvent> createAction) {
        this.createAction = createAction;
    }

    public void setBackAction(Consumer<InventoryClickEvent> backAction) {
        this.backAction = backAction;
    }

    public abstract Function<ListGui, GuiPane> buildControlsBuilder();

    protected StaticItem getPreviousItem(ListGui gui) {
        return new TranslatedItem(gui.getTranslation(), new ItemStackBuilder(Material.ARROW).setDisplayName("previous").build()){{
            setClickAction(event -> gui.previous());
        }};
    }

    protected StaticItem getNextItem(ListGui gui) {
        return new TranslatedItem(gui.getTranslation(), new ItemStackBuilder(Material.ARROW).setDisplayName("next").build()){{
            setClickAction(event -> gui.next());
        }};
    }

    protected StaticItem getSearchItem(ListGui gui) {
        return new TranslatedItem(gui.getTranslation(), new ItemStackBuilder(Material.COMPASS).setDisplayName("search.title").addLore("search.description").build()) {{
            setClickAction(event -> {
                Player player = (Player) event.getWhoClicked();
                if (event.getClick() == ClickType.LEFT) {
                    gui.hide(player);
                    player.sendMessage(gui.getTranslation().getTranslation("search.input"));
                    ChatRequest request = new ChatRequest(player);
                    request.setSubmitAction(searchText -> {
                        player.sendMessage(gui.getTranslation().getTranslation("search.success", searchText));
                        gui.setSearchText(searchText);
                        gui.show(player);
                    });
                } else if (event.getClick() == ClickType.RIGHT) gui.setSearchText("");
                else if (event.getClick() == ClickType.DROP) {
                    if (backAction != null) backAction.accept(event);
                    else gui.hide(player);
                }
            });
        }};
    }

    protected StaticItem getFirstItem(ListGui gui) {
        return new TranslatedItem(gui.getTranslation(), new ItemStackBuilder(Material.BLAZE_ROD).setDisplayName("first").build()) {{
            setClickAction(event -> gui.toFirst());
        }};
    }

    protected StaticItem getLastItem(ListGui gui) {
        return new TranslatedItem(gui.getTranslation(), new ItemStackBuilder(Material.BLAZE_ROD).setDisplayName("last").build()) {{
            setClickAction(event -> gui.toLast());
        }};
    }

    protected StaticItem getCreateItem(ListGui gui) {
        return new TranslatedItem(gui.getTranslation(), new ItemStackBuilder(Material.KNOWLEDGE_BOOK).setDisplayName("create.title").setLore("create.description").build()) {{
            setClickAction(event -> createAction.accept(event));
        }};
    }

    protected StaticItem getPlaceholderItem() {
        return new StaticItem(new ItemStackBuilder(Material.IRON_BARS).setDisplayName(" ").build());
    }

    public void offset(int x, int y) {
        offsetX = x;
        offsetY = y;
    }
}
