package com.temportalist.thaumicexpansion.common;

import cofh.lib.util.ArrayHashList;
import cofh.lib.util.helpers.AugmentHelper;
import cofh.thermalexpansion.block.simple.BlockFrame;
import cofh.thermalexpansion.item.TEItems;
import cofh.thermalexpansion.util.crafting.RecipeMachineUpgrade;
import cofh.thermalexpansion.util.crafting.TECraftingHandler;
import com.temportalist.thaumicexpansion.common.block.BlockThaumicAnalyzer;
import com.temportalist.thaumicexpansion.common.item.ItemAugment;
import com.temportalist.thaumicexpansion.common.lib.Pair;
import com.temportalist.thaumicexpansion.common.packet.PacketRecieveAspect;
import com.temportalist.thaumicexpansion.common.packet.PacketTileSync;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.research.ScanResult;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.lib.research.PlayerKnowledge;
import thaumcraft.common.lib.research.ScanManager;

import java.io.File;
import java.util.*;

/**
 * @author TheTemportalist
 */
/*
@Mod(modid = TEC.MODID, name = "Thaumic Expansion", version = "1.0",
		dependencies = "required-after:Thaumcraft@[4.2,);" +
				"required-after:CoFHCore@[1.7.10R3.0.0RC7,);" +
				"required-after:ThermalFoundation@[1.7.10R1.0.0RC7,);" +
				"required-after:ThermalExpansion@[1.7.10R4.0.0RC7,);"
)
*/
public class TEC {

	public static final String MODID = "thaumicexpansion";

	//@Mod.Instance(TEC.MODID)
	//public static TEC instance;

	/*
	@SidedProxy(
			clientSide = "com.temportalist.thaumicexpansion.client.ProxyClient",
			serverSide = "com.temportalist.thaumicexpansion.common.ProxyCommon"
	)
	public static ProxyCommon proxy;
	*/

	public BlockThaumicAnalyzer thaumicAnalyzer;
	public Item playerTracker, decomposerUpgrade, itemKeeper, thaumicAdjuster;

	public static final HashMap<Aspect, Integer> aspectTiers = new HashMap<Aspect, Integer>();
	/**
	 * Aspect Tier -> [Energy taken, Time taken]
	 */
	public static final HashMap<Integer, int[]> decompositionStats = new HashMap<Integer, int[]>();
	/**
	 * Key -> machine tier
	 * Value ->
	 * Key -> aspect complexity
	 * Value -> primary check for type of aspect
	 */
	public static final double[][] complexityTierChance = new double[][] {
			new double[] { 1, .5, .25 },
			new double[] { 1, .7, .45 },
			new double[] { 1, .9, .65 },
			new double[] { 1, .9, .85 }
	};
	/**
	 * Key -> machine tier
	 * Value -> second check for each aspect
	 */
	public static final double[] tieredChance = new double[] { .4, .6, .8, 1 };

	public static ItemStack[] machines = new ItemStack[4];

	public static int maxEnergyStorage = 8000;
	public static boolean consumeItems = true;

	public static final List<UUID> onlinePlayers = new ArrayList<UUID>();
	// todo: flaw: both of these 2 need to be saved to disk
	public static final HashMap<UUID, String> idToUsername = new HashMap<UUID, String>();
	private static final HashMap<UUID, List<Pair<ScanResult, Pair<Double, Double>>>> aspectBuffer =
			new HashMap<UUID, List<Pair<ScanResult, Pair<Double, Double>>>>();

	private static class TECData extends WorldSavedData {

	}

}
