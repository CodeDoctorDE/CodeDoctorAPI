package dev.linwood.api.ui.template.item;

import dev.linwood.api.ui.item.StaticItem;
import dev.linwood.api.util.NumberManager;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Consumer;

public class NumberItem extends StaticItem {
    private final @NotNull NumberManager manager;
    private Consumer<Float> changeAction;

    public NumberItem(ItemStack itemStack) {
        this(itemStack, 1);
    }

    public NumberItem(ItemStack itemStack, int defaultValue) {
        super(itemStack);
        manager = new NumberManager(defaultValue);
        setClickAction(event -> {
            manager.handleEvent(event);
            if (changeAction != null)
                changeAction.accept(getValue());
        });
    }

    public @NotNull NumberManager getManager() {
        return manager;
    }

    public float getValue() {
        return manager.getValue();
    }

    public void setChangeAction(Consumer<Float> changeAction) {
        this.changeAction = changeAction;
    }

    @Override
    public Object @NotNull [] getPlaceholders() {
        Object[] defaultPlaceholders = super.getPlaceholders();
        int N = defaultPlaceholders.length;
        Object[] placeholders = Arrays.copyOf(defaultPlaceholders, N + 6);
        placeholders[N] = manager.getValue();
        placeholders[N + 1] = -manager.getFastSteps();
        placeholders[N + 2] = -manager.getSteps();
        placeholders[N + 3] = manager.getValue();
        placeholders[N + 4] = manager.getSteps();
        placeholders[N + 5] = manager.getDefaultValue();
        return placeholders;
    }

    public Object[] getDefaultPlaceholders() {
        return super.getPlaceholders();
    }
}
