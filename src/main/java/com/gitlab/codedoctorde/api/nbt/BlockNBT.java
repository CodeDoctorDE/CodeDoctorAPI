package com.gitlab.codedoctorde.api.nbt;

import com.gitlab.codedoctorde.api.server.Version;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
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
        try {
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
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
    }
}
