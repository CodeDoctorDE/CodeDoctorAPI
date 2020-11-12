package com.github.codedoctorde.api.nms.block;

import com.github.codedoctorde.api.server.Version;
import org.bukkit.block.Block;

/**
 * @author CodeDoctorDE
 */
public class BlockNBT {
    public static String getNbt(Block block) {
        switch (Version.getVersion()) {
            case v1_16:
                return com.github.codedoctorde.api.nms.v1_16_R3.BlockNBT.getNbt(block);
            case v1_15:
                return com.github.codedoctorde.api.nms.v1_15_R1.BlockNBT.getNbt(block);
            case v1_14:
                return com.github.codedoctorde.api.nms.v1_14_R1.BlockNBT.getNbt(block);
            default:
                return null;
        }
    }

    public static void setNbt(Block block, String nbt) {
        switch (Version.getVersion()) {
            case v1_16:
                com.github.codedoctorde.api.nms.v1_16_R3.BlockNBT.setNbt(block, nbt);
                break;
            case v1_15:
                com.github.codedoctorde.api.nms.v1_15_R1.BlockNBT.setNbt(block, nbt);
                break;
            case v1_14:
                com.github.codedoctorde.api.nms.v1_14_R1.BlockNBT.setNbt(block, nbt);
        }
    }
}
