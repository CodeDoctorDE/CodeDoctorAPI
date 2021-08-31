package dev.linwood.api.request;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author CodeDoctorDE
 */
public class ChatRequest extends Request<String> {
    public ChatRequest(final @NotNull Player player) {
        super(player);
    }
}
