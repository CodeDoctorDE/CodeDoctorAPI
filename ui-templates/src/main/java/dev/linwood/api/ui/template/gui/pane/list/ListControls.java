package dev.linwood.api.ui.template.gui.pane.list;

import dev.linwood.api.request.ChatRequest;
import dev.linwood.api.ui.GuiPane;
import dev.linwood.api.ui.item.StaticItem;
import dev.linwood.api.ui.template.gui.ListGui;
import dev.linwood.api.ui.template.item.TranslatedItem;
import dev.linwood.api.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class ListControls {
    private final boolean detailed;
    protected Consumer<InventoryClickEvent> backAction;
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

    protected @NotNull StaticItem getPreviousItem(@NotNull ListGui gui) {
        return new TranslatedItem(gui.getTranslation(), new ItemStackBuilder(Material.ARROW).setDisplayName("previous").build()) {{
            setClickAction(event -> gui.previous());
        }};
    }

    protected @NotNull StaticItem getNextItem(@NotNull ListGui gui) {
        return new TranslatedItem(gui.getTranslation(), new ItemStackBuilder(Material.ARROW).setDisplayName("next").build()) {{
            setClickAction(event -> gui.next());
        }};
    }

    protected @NotNull StaticItem getSearchItem(@NotNull ListGui gui) {
        return new TranslatedItem(gui.getTranslation(), new ItemStackBuilder(Material.COMPASS).setDisplayName("search.title").addLore("search.description").build()) {{
            setRenderAction(event -> setPlaceholders(gui.getSearchText()));
            setClickAction(event -> {
                Player player = (Player) event.getWhoClicked();
                if (event.getClick() == ClickType.LEFT) {
                    gui.hide(player);
                    player.sendMessage(gui.getTranslation().getTranslation("search.message"));
                    ChatRequest request = new ChatRequest(player);
                    request.setSubmitAction(searchText -> {
                        gui.setSearchText(searchText);
                        gui.show(player);
                    });
                } else if (event.getClick() == ClickType.RIGHT) {
                    gui.setSearchText("");
                } else if (event.getClick() == ClickType.DROP) {
                    if (backAction != null) backAction.accept(event);
                    else gui.hide(player);
                }
            });
        }};
    }

    protected @NotNull StaticItem getFirstItem(@NotNull ListGui gui) {
        return new TranslatedItem(gui.getTranslation(), new ItemStackBuilder(Material.BLAZE_ROD).setDisplayName("first").build()) {{
            setClickAction(event -> gui.toFirst());
        }};
    }

    protected @NotNull StaticItem getLastItem(@NotNull ListGui gui) {
        return new TranslatedItem(gui.getTranslation(), new ItemStackBuilder(Material.BLAZE_ROD).setDisplayName("last").build()) {{
            setClickAction(event -> gui.toLast());
        }};
    }

    protected @NotNull StaticItem getCreateItem(@NotNull ListGui gui) {
        return new TranslatedItem(gui.getTranslation(), new ItemStackBuilder(Material.KNOWLEDGE_BOOK).setDisplayName("create.title").setLore("create.description").build()) {{
            setClickAction(event -> createAction.accept(event));
        }};
    }

    protected @NotNull StaticItem getPlaceholderItem() {
        return new StaticItem(new ItemStackBuilder(Material.IRON_BARS).setDisplayName(" ").build());
    }

    public void offset(int x, int y) {
        offsetX = x;
        offsetY = y;
    }
}
