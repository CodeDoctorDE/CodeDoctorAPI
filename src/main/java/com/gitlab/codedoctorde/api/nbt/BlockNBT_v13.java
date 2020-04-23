package com.gitlab.codedoctorde.api.nbt;

import com.google.gson.JsonObject;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.TileEntity;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;

/**
 * @author CodeDoctorDE
 */
public class BlockNBT_v13 {
    public static String getNbt(Block block) {
        CraftWorld ws = (CraftWorld) block.getWorld();
        NBTTagCompound ntc = null;
        TileEntity te = (TileEntity) ws.getBlockAt(block.getX(), block.getY(), block.getZ());
        NBTTagCompound nbtTagCompound = te.persistentDataContainer.toTagCompound();
        return nbtTagCompound.asString();
    }

    public static void setNbt(Block block, JsonObject nbt) {
        CraftWorld ws = (CraftWorld) block.getWorld();
        NBTTagCompound ntc = null;
        TileEntity te = (TileEntity) ws.getBlockAt(block.getX(), block.getY(), block.getZ());
        te.persistentDataContainer.putAll(new NBTTagCompound());
    }
}
