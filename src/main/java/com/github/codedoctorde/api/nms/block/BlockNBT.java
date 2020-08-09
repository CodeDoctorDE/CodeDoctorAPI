package com.github.codedoctorde.api.nms.block;

import com.github.codedoctorde.api.nms.v1_14_R1.BlockNBT_v14;
import com.github.codedoctorde.api.nms.v1_15_R1.BlockNBT_v15;
import com.github.codedoctorde.api.nms.v1_16_R1.BlockNBT_v16;
import com.github.codedoctorde.api.server.Version;
import org.bukkit.block.Block;

/**
 * @author CodeDoctorDE
 */
public class BlockNBT {
    public static String getNbt(Block block) {
        switch (Version.getVersion()) {
            case v1_16:
                return BlockNBT_v16.getNbt(block);
            case v1_15:
                return BlockNBT_v15.getNbt(block);
            case v1_14:
                return BlockNBT_v14.getNbt(block);
            default:
                return null;
        }
    }

    public static void setNbt(Block block, String nbt) {
        switch (Version.getVersion()) {
            case v1_16:
                BlockNBT_v16.setNbt(block, nbt);
                break;
            case v1_15:
                BlockNBT_v15.setNbt(block, nbt);
                break;
            case v1_14:
                BlockNBT_v14.setNbt(block, nbt);
        }
    }
}
