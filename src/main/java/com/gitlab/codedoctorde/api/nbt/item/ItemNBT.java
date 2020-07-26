package com.gitlab.codedoctorde.api.nbt.item;

import com.gitlab.codedoctorde.api.server.Version;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
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
        try {
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
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
    }
}
