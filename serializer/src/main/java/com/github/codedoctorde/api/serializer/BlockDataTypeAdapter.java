package com.github.codedoctorde.api.serializer;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

/**
 * @author CodeDoctorDE
 */
public class BlockDataTypeAdapter implements JsonSerializer<BlockData>, JsonDeserializer<BlockData> {
    private final Gson gson = new Gson();

    @Override
    public @NotNull BlockData deserialize(@NotNull JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Bukkit.createBlockData(json.getAsString());
    }

    @Override
    public @NotNull JsonElement serialize(@NotNull BlockData src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getAsString());
    }
}
