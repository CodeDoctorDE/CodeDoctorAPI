package com.gitlab.codedoctorde.api.serializer;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;

import java.lang.reflect.Type;

public class BlockDataTypeAdapter implements JsonSerializer<BlockData>, JsonDeserializer<BlockData> {
    private Gson gson = new Gson();

    @Override
    public BlockData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Bukkit.createBlockData(json.getAsString());
    }

    @Override
    public JsonElement serialize(BlockData src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(gson.toJson(src.getAsString()));
    }
}
