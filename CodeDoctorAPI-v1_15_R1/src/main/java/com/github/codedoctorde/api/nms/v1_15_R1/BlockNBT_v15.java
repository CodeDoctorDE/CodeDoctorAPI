package com.github.codedoctorde.api.nms.v1_15_R1;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.v1_15_R1.BlockPosition;
import net.minecraft.server.v1_15_R1.MojangsonParser;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import net.minecraft.server.v1_15_R1.TileEntity;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;

/**
 * @author CodeDoctorDE
 */
public class BlockNBT_v15 {
    public static String getNbt(Block block) {
        CraftWorld ws = (CraftWorld) block.getWorld();
        NBTTagCompound ntc = null;
        TileEntity te = ws.getHandle().getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
        assert te != null;
        return te.save(new NBTTagCompound()).toString();
    }

    public static void setNbt(Block block, String nbt) {
        CraftWorld ws = (CraftWorld) block.getWorld();
        NBTTagCompound ntc = null;
        BlockPosition position = new BlockPosition(block.getX(), block.getY(), block.getZ());
        TileEntity te = ws.getHandle().getTileEntity(position);
        assert te != null;
        try {
            te.load(MojangsonParser.parse(nbt));
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        te.update();
        ws.getHandle().setTileEntity(position, te);
        block.getState().update(true);
    }
}