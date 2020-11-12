package com.github.codedoctorde.api.nms.item;

import com.github.codedoctorde.api.server.Version;
import org.bukkit.block.Block;

/**
 * @author CodeDoctorDE
 */
public class ItemNBT {
    public static String getNbt(Block block) {
        switch (Version.getVersion()) {
            case v1_16:
                return com.github.codedoctorde.api.nms.v1_16_R3.ItemNBT.getNbt(block);
            case v1_15:
                return com.github.codedoctorde.api.nms.v1_15_R1.ItemNBT.getNbt(block);
            case v1_14:
                return com.github.codedoctorde.api.nms.v1_14_R1.ItemNBT.getNbt(block);
            default:
                return null;
        }
    }

    public static void setNbt(Block block, String nbt) {
            switch (Version.getVersion()) {
                case v1_16:
                    com.github.codedoctorde.api.nms.v1_16_R3.ItemNBT.setNbt(block, nbt);
                    break;
                case v1_15:
                    com.github.codedoctorde.api.nms.v1_15_R1.ItemNBT.setNbt(block, nbt);
                    break;
                case v1_14:
                    com.github.codedoctorde.api.nms.v1_14_R1.ItemNBT.setNbt(block, nbt);
            }
    }
}
