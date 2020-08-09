package com.github.codedoctorde.api.nms.item;

import com.github.codedoctorde.api.server.Version;
import com.github.codedoctorde.api.nms.v1_14_R1.ItemNBT_v14;
import com.github.codedoctorde.api.nms.v1_15_R1.ItemNBT_v15;
import com.github.codedoctorde.api.nms.v1_16_R1.ItemNBT_v16;
import org.bukkit.block.Block;

/**
 * @author CodeDoctorDE
 */
public class ItemNBT {
    public static String getNbt(Block block) {
        switch (Version.getVersion()) {
            case v1_16:
                return ItemNBT_v16.getNbt(block);
            case v1_15:
                return ItemNBT_v15.getNbt(block);
            case v1_14:
                return ItemNBT_v14.getNbt(block);
            default:
                return null;
        }
    }

    public static void setNbt(Block block, String nbt) {
            switch (Version.getVersion()) {
                case v1_16:
                    ItemNBT_v16.setNbt(block, nbt);
                    break;
                case v1_15:
                    ItemNBT_v15.setNbt(block, nbt);
                    break;
                case v1_14:
                    ItemNBT_v14.setNbt(block, nbt);
            }
    }
}
