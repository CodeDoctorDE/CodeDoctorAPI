package com.gitlab.codedoctorde.api.nbt;

import com.google.gson.JsonObject;
import net.minecraft.server.v1_15_R1.BlockPosition;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import net.minecraft.server.v1_15_R1.TileEntity;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;

import java.util.Objects;

/**
 * @author CodeDoctorDE
 */
public class BlockNBT_v15 {
    public static String getNbt(Block block) {
        CraftWorld ws = (CraftWorld) block.getWorld();
        NBTTagCompound ntc = null;
        TileEntity te = ws.getHandle().getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
        NBTTagCompound nbtTagCompound = Objects.requireNonNull(te).persistentDataContainer.toTagCompound();
        return te.save(new NBTTagCompound()).toString();
    }

    public static void setNbt(Block block, JsonObject nbt) {
        CraftWorld ws = (CraftWorld) block.getWorld();
        NBTTagCompound ntc = null;
        TileEntity te = (TileEntity) ws.getBlockAt(block.getX(), block.getY(), block.getZ());
        te.persistentDataContainer.putAll(new NBTTagCompound());
    }
}
