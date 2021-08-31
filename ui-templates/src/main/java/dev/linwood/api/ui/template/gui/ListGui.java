package dev.linwood.api.ui.template.gui;

import dev.linwood.api.translations.Translation;
import dev.linwood.api.ui.ChestGui;
import dev.linwood.api.ui.GuiCollection;
import dev.linwood.api.ui.GuiPane;
import dev.linwood.api.ui.item.GuiItem;
import dev.linwood.api.ui.template.gui.pane.list.ListControls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class ListGui extends GuiCollection {
    private Function<ListGui, GuiItem[]> itemBuilder = (gui) -> new GuiItem[0];
    private final int height;
    private final Translation translation;
    private Object[] placeholders = new Object[0];
    private Function<ListGui, GuiPane> controlsBuilder;
    private String searchText = "";
    private int controlsOffsetX = 0, controlsOffsetY = 0;

    public ListGui(Translation translation, int height, Function<ListGui, GuiItem[]> itemBuilder) {
        this.itemBuilder = itemBuilder;
        this.height = height;
        this.translation = translation;
        rebuild();
    }

    public ListGui(Translation translation, Function<ListGui, GuiItem[]> itemBuilder) {
        this(translation, 3, itemBuilder);
    }

    public void setControlsBuilder(Function<ListGui, GuiPane> controlsBuilder) {
        this.controlsBuilder = controlsBuilder;
        rebuild();
    }
    public ListGui(Translation translation, int height) {
        this.height = height;
        this.translation = translation;
        rebuild();
    }

    public ListGui(Translation translation) {
        this(translation, 3);
    }

    public void setControlsBuilder() {
        rebuild();
    }

    public void setItemBuilder(Function<ListGui, GuiItem[]> itemBuilder) {
        this.itemBuilder = itemBuilder;
    }

    public void setListControls(@NotNull ListControls controls) {
        setControlsBuilder(controls.buildControlsBuilder());
    }

    public Object[] getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(Object... placeholders) {
        this.placeholders = placeholders;
        rebuild();
    }

    public void rebuild() {
        var openedPlayers = getOpenedPlayers();
        clearGuis();
        var width = getWidth();
        GuiItem[] items = itemBuilder.apply(this);
        GuiPane controls = controlsBuilder == null ? null : controlsBuilder.apply(this);
        int controlsCount = controls == null ? 0 : controls.getItemCount();
        int freeSlots = height * 9 - controlsCount;
        int pageCount = (int) Math.ceil(items.length / (float) freeSlots);
        if (pageCount <= 0)
            pageCount = 1;

        int currentPage = 1;

        ChestGui currentGui = buildGui(currentPage, pageCount, controls);
        registerGui(currentGui);

        for (int i = 0, pageItemCount = 0; i < items.length; i++) {
            if (height * width <= pageItemCount + controlsCount) {
                currentPage++;
                pageItemCount = 0;
                currentGui = buildGui(currentPage, pageCount, controls);
                registerGui(currentGui);
            }
            currentGui.addItem(items[i]);
            pageItemCount++;
        }
        toFirst();
        show(openedPlayers);
    }

    private @NotNull TranslatedChestGui buildGui(int currentPage, int pageCount, @Nullable GuiPane controls) {
        TranslatedChestGui gui = new TranslatedChestGui(translation, height);
        var placeholders = new ArrayList<>(Arrays.asList(this.placeholders));
        placeholders.add(currentPage);
        placeholders.add(pageCount);
        gui.setPlaceholders(placeholders.toArray(Object[]::new));
        if (controls != null)
            gui.addPane(controlsOffsetX, controlsOffsetY, controls);
        return gui;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
        rebuild();
    }

    public Translation getTranslation() {
        return translation;
    }

    public void controlsOffset(int x, int y) {
        controlsOffsetX = x;
        controlsOffsetY = y;
        rebuild();
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return 9;
    }
}
