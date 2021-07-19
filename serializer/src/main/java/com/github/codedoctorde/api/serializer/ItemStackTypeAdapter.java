package com.github.codedoctorde.api.serializer;

import com.github.codedoctorde.api.utils.ItemStackBuilder;
import com.google.gson.*;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

/**
 * @author CodeDoctorDE
 */
public class ItemStackTypeAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
    private final @NotNull Gson gson;

    public ItemStackTypeAdapter() {
        gson = new GsonBuilder().create();
    }

    @Override
    public ItemStack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        return new ItemStackBuilder(jsonElement).build();
    }

    @Override
    public @Nullable JsonElement serialize(@NotNull ItemStack itemStack, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(new ItemStackBuilder(itemStack).serialize());
    }

}