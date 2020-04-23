package com.gitlab.codedoctorde.api.nbt;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.MojangsonParser;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.TileEntity;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;

import java.util.Objects;

/**
 * @author CodeDoctorDE
 */
public class BlockNBT_v14 {
    public static String getNbt(Block block) {
        CraftWorld ws = (CraftWorld) block.getWorld();
        NBTTagCompound ntc = null;
        TileEntity te = ws.getHandle().getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
        NBTTagCompound nbtTagCompound = Objects.requireNonNull(te).persistentDataContainer.toTagCompound();
        return te.save(new NBTTagCompound()).toString();
    }

    public static void setNbt(Block block, String nbt) throws CommandSyntaxException {
        CraftWorld ws = (CraftWorld) block.getWorld();
        NBTTagCompound ntc = null;
        BlockPosition position = new BlockPosition(block.getX(), block.getY(), block.getZ());
        TileEntity te = ws.getHandle().getTileEntity(position);
        assert te != null;
        te.load(MojangsonParser.parse(nbt));
        te.update();
        ws.getHandle().setTileEntity(position, te);
        block.getState().update(true);
    }
}
