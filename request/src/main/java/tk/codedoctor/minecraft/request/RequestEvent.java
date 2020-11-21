package tk.codedoctor.minecraft.request;

import org.bukkit.entity.Player;

public interface RequestEvent<T> {
    void onEvent(final Player player, final T output);
    void onCancel(final Player player);
}
