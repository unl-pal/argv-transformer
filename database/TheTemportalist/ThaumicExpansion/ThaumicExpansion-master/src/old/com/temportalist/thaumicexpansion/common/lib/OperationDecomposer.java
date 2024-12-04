package com.temportalist.thaumicexpansion.common.lib;

import com.temportalist.thaumicexpansion.common.TEC;
import com.temportalist.thaumicexpansion.common.tile.TEThaumicAnalyzer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import java.util.Random;
import java.util.Set;

/**
 * @author TheTemportalist
 */
public class OperationDecomposer implements IOperation {

	private int maxTicks, energyCost, currentTicks = -1;
	private AspectList outputAspects = new AspectList();
	private boolean hasItemKeeper, hasTracker;
	private int machineTier;
	private double secondaryChanceMult = 1d;

}
