package tk.codedoctor.minecraft.serializer;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;

import java.lang.reflect.Type;

/**
 * @author CodeDoctorDE
 */
public class BlockDataTypeAdapter implements JsonSerializer<BlockData>, JsonDeserializer<BlockData> {
    private final Gson gson = new Gson();

    @Override
    public BlockData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Bukkit.createBlockData(json.getAsString());
    }

    @Override
    public JsonElement serialize(BlockData src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getAsString());
    }
}
