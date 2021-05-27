package com.github.codedoctorde.api.ui.template.gui.pane.list;

import com.github.codedoctorde.api.request.ChatRequest;
import com.github.codedoctorde.api.translations.Translation;
import com.github.codedoctorde.api.ui.GuiPane;
import com.github.codedoctorde.api.ui.StaticItem;
import com.github.codedoctorde.api.ui.template.gui.ListGui;
import com.github.codedoctorde.api.ui.template.item.TranslationItem;
import com.github.codedoctorde.api.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.function.Function;

public class VerticalListControls extends GuiPane {
    private final boolean detailed;

    public VerticalListControls() {
        this(true);
    }
    public VerticalListControls(boolean detailed) {
        super(9, 2);
        this.detailed = detailed;
    }
    Function<ListGui, GuiPane> buildControlsBuilder() {
        return gui -> {
            Translation translation = gui.getTranslation();
            StaticItem previousPage = new TranslationItem(translation, new ItemStackBuilder(Material.ARROW).setDisplayName("previous").build());
            StaticItem nextPage = new TranslationItem(translation, new ItemStackBuilder(Material.ARROW).setDisplayName("next").build());
            StaticItem searchPage = new TranslationItem(translation, new ItemStackBuilder(Material.COMPASS).setDisplayName("search.title").addLore("search.description").build()){{
                setClickAction(event -> {
                    Player player = (Player) event.getWhoClicked();
                    if (event.getClick() == ClickType.LEFT) {
                        gui.hide(player);
                        player.sendMessage(translation.getTranslation("search.input"));
                        ChatRequest request = new ChatRequest(player);
                        request.setSubmitAction(gui::setSearchText);
                    } else if (event.getClick() == ClickType.RIGHT) gui.setSearchText("");
                    else if(event.getClick() == ClickType.DROP) gui.rebuild();
                });
            }};
            StaticItem placeholderPage = new StaticItem(new ItemStackBuilder(Material.IRON_BARS).build());
            fillItems(0, 0, 8, 1, placeholderPage);
            registerItem(0, 0, previousPage);
            registerItem(0, 4, searchPage);
            registerItem(8, 8, previousPage);

            if(detailed) {
                registerItem(0, 1, new TranslationItem(translation, new ItemStackBuilder(Material.BLAZE_ROD).setDisplayName("first").build()){{
                    setClickAction(event -> gui.toFirst());
                }});
                registerItem(8, 7, new TranslationItem(translation, new ItemStackBuilder(Material.BLAZE_ROD).setDisplayName("last").build()){{
                    setClickAction(event -> gui.toLast());
                }});
            }
            return this;
        };
    }
}
