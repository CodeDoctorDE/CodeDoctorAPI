package com.github.codedoctorde.api.ui.template.gui.pane.list;

import com.github.codedoctorde.api.request.ChatRequest;
import com.github.codedoctorde.api.ui.GuiPane;
import com.github.codedoctorde.api.ui.StaticItem;
import com.github.codedoctorde.api.ui.template.gui.ListGui;
import com.github.codedoctorde.api.ui.template.item.TranslatedItem;
import com.github.codedoctorde.api.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class ListControls extends GuiPane {
    private final boolean detailed;
    protected Consumer<InventoryClickEvent> createAction;

    public ListControls(int width, int height) {
        this(true, width, height);
    }

    public ListControls(boolean detailed, int width, int height) {
        super(width, height);
        this.detailed = detailed;
    }

    public boolean isDetailed() {
        return detailed;
    }

    public void setCreateAction(Consumer<InventoryClickEvent> createAction) {
        this.createAction = createAction;
    }

    public abstract Function<ListGui, GuiPane> buildControlsBuilder();

    protected StaticItem getPreviousItem(ListGui gui) {
        return new TranslatedItem(gui.getTranslation(), new ItemStackBuilder(Material.ARROW).setDisplayName("previous").build());
    }

    protected StaticItem getNextItem(ListGui gui) {
        return new TranslatedItem(gui.getTranslation(), new ItemStackBuilder(Material.ARROW).setDisplayName("next").build());
    }

    protected StaticItem getSearchItem(ListGui gui) {
        return new TranslatedItem(gui.getTranslation(), new ItemStackBuilder(Material.COMPASS).setDisplayName("search.title").addLore("search.description").build()){{
            setClickAction(event -> {
                Player player = (Player) event.getWhoClicked();
                if (event.getClick() == ClickType.LEFT) {
                    gui.hide(player);
                    player.sendMessage(gui.getTranslation().getTranslation("search.input"));
                    ChatRequest request = new ChatRequest(player);
                    request.setSubmitAction(gui::setSearchText);
                } else if (event.getClick() == ClickType.RIGHT) gui.setSearchText("");
                else if(event.getClick() == ClickType.DROP) gui.rebuild();
            });
        }};
    }

    protected StaticItem getFirstItem(ListGui gui) {
        return new TranslatedItem(gui.getTranslation(), new ItemStackBuilder(Material.BLAZE_ROD).setDisplayName("first").build()){{
            setClickAction(event -> gui.toFirst());
        }};
    }

    protected StaticItem getLastItem(ListGui gui) {
        return new TranslatedItem(gui.getTranslation(), new ItemStackBuilder(Material.BLAZE_ROD).setDisplayName("last").build()){{
            setClickAction(event -> gui.toLast());
        }};
    }

    protected StaticItem getCreateItem(ListGui gui) {
        return new TranslatedItem(gui.getTranslation(), new ItemStackBuilder(Material.KNOWLEDGE_BOOK).setDisplayName("create").build()){{
            setClickAction(event -> createAction.accept(event));
        }};
    }

    protected StaticItem getPlaceholderItem() {
        return new StaticItem(new ItemStackBuilder(Material.IRON_BARS).setDisplayName(" ").build());
    }

    public ListControls offset(int x, int y) {
        return (ListControls) super.offset(x, y);
    }
}
