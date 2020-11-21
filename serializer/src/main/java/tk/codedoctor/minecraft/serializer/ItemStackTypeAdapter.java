package tk.codedoctor.minecraft.serializer;

import tk.codedoctor.minecraft.utils.ItemStackBuilder;
import com.google.gson.*;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;

/**
 * @author CodeDoctorDE
 */
public class ItemStackTypeAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {
    private final Gson gson;

    public ItemStackTypeAdapter() {
        gson = new GsonBuilder().create();
    }

    @Override
    public ItemStack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        return new ItemStackBuilder(jsonElement).build();
    }

    @Override
    public JsonElement serialize(ItemStack itemStack, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(new ItemStackBuilder(itemStack).serialize());
    }

}