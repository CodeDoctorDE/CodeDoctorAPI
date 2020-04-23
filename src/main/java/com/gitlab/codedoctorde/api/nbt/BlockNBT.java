package com.gitlab.codedoctorde.api.nbt;

import com.gitlab.codedoctorde.api.server.Version;
import org.bukkit.block.Block;

/**
 * @author CodeDoctorDE
 */
public class BlockNBT {
    public String getNbt(Block block) {
        switch (Version.getVersion()) {
            case v1_15:
                return BlockNBT_v15.getNbt(block);
            case v1_14:
                return BlockNBT_v14.getNbt(block);
            default:
                return null;
        }
    }
}
