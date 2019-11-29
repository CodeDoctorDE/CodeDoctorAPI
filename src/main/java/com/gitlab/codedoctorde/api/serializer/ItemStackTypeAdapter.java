package com.gitlab.codedoctorde.api.serializer;

import com.gitlab.codedoctorde.api.utils.ItemStackBuilder;
import com.google.gson.*;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;

public class ItemStackTypeAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
    private Gson gson;

    public ItemStackTypeAdapter() {
        gson = new GsonBuilder().create();
    }

    @Override
    public ItemStack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        return new ItemStackBuilder(jsonElement).build();
    }

    @Override
    public JsonElement serialize(ItemStack itemStack, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(gson.toJson(itemStack.serialize()));
    }

}