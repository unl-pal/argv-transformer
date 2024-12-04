package com.temportalist.thaumicexpansion.common.lib;

import com.temportalist.thaumicexpansion.common.TEC;
import com.temportalist.thaumicexpansion.common.tile.TEThaumicAnalyzer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import thaumcraft.api.research.ScanResult;

import java.util.Set;

/**
 * @author TheTemportalist
 */
public class OperationAnalyzer implements IOperation {

	private int maxTicks, energyCost, currentTicks = -1;
	private boolean hasAdjuster = false;

}
