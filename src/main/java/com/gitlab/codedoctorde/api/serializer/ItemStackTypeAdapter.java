package com.gitlab.codedoctorde.api.serializer;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.Map;

public class ItemStackTypeAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
    private Gson gson;

    public ItemStackTypeAdapter() {
        gson = new GsonBuilder().create();
    }

    @Override
    public ItemStack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        Map<String, Object> map = gson.fromJson(jsonElement, new TypeToken<Map<String, Object>>() {
        }.getType());
        return ItemStack.deserialize(map);
    }

    @Override
    public JsonElement serialize(ItemStack itemStack, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(gson.toJson(itemStack.serialize()));
    }

}