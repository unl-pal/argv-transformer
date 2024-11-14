package com.temportalist.thaumicexpansion.common.block;

import cofh.lib.util.helpers.AugmentHelper;
import cofh.thermalexpansion.block.machine.BlockMachine;
import com.temportalist.thaumicexpansion.common.TEC;
import com.temportalist.thaumicexpansion.common.item.ItemBlockTA;
import com.temportalist.thaumicexpansion.common.lib.EnumSideTA;
import com.temportalist.thaumicexpansion.common.tile.TEThaumicAnalyzer;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

/**
 * @author TheTemportalist
 */
public class BlockThaumicAnalyzer extends BlockMachine {

	private final String modid, name;

	/**
	 * Tiers -> 4
	 * Faces:
	 * 0 -> bottom
	 * 1 -> top
	 * 2 -> front
	 * 3 -> sides
	 */
	@SideOnly(Side.CLIENT)
	private IIcon[][] icons;

	/*
	@Override
	public boolean canDismantle(EntityPlayer player, World world, int x, int y, int z) {
		return true;
	}

	@Override
	public ArrayList<ItemStack> dismantleBlock(EntityPlayer player, World world, int x, int y,
			int z, boolean paramBoolean) {
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		drops.add(this.getPickBlock(null, world, x, y, z, player));
		TEThaumicAnalyzer tile = (TEThaumicAnalyzer) world.getTileEntity(x, y, z);
		if (tile != null) {
			for (int slot = 0; slot < tile.getSizeInventory(); slot++)
				if (tile.getStackInSlot(slot) != null)
					drops.add(tile.getStackInSlot(slot));
		}
		world.setBlockToAir(x, y, z);
		return drops;
	}
	*/

}
