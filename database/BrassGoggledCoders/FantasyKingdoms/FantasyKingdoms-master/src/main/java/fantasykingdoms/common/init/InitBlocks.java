package fantasykingdoms.common.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import boilerplate.common.baseclasses.items.BaseItemBlockWithMetadata;
import fantasykingdoms.common.blocks.BaseBlock;
import fantasykingdoms.common.blocks.BlockCustomFiniteFluid;
import fantasykingdoms.common.blocks.BlockCustomLeaves;
import fantasykingdoms.common.blocks.BlockCustomLog;
import fantasykingdoms.common.blocks.BlockDecorativeStone;
import fantasykingdoms.common.blocks.BlockFog;
import fantasykingdoms.common.blocks.BlockGemstone;
import fantasykingdoms.common.blocks.BlockGemstoneOre;
import fantasykingdoms.common.blocks.BlockInvisLight;
import fantasykingdoms.common.blocks.BlockMetal;
import fantasykingdoms.common.blocks.BlockOre;
import fantasykingdoms.common.blocks.BlockSolidFog;
import fantasykingdoms.common.blocks.FluidTreasure;
import fantasykingdoms.common.blocks.dwarven.BlockBarrel;
import fantasykingdoms.common.blocks.dwarven.BlockExplosiveBarrel;
import fantasykingdoms.common.blocks.dwarven.BlockRunicAnvil;

public class InitBlocks
{
	public static Block blockGemOre, blockGemstone;
	public static Block blockMetalOre;
	public static Block blockMetal;
	public static Block blockMarble, blockLimestone, blockShadowstone, blockOnyx, blockFlint;

	public static Block blockShadewoodLog, blockShadewoodLeaves, blockShadewoodPlanks, blockElfwoodLog, blockElfwoodLeaves, blockElfwoodPlanks,
			blockYewLog, blockYewLeaves, blockYewPlanks, blockElmLog, blockElmLeaves, blockElmPlanks, blockAshLog, blockAshLeaves, blockAshPlanks,
			blockIronwoodLog, blockIronwoodLeaves, blockIronwoodPlanks, blockDeaththornLog, blockDeaththornLeaves, blockDeaththornPlanks;

	public static Block blockBarrel;
	public static Block blockExplosiveBarrel;

	public static Block blockRunicForge;

	public static Block blockRunicAnvil;

	public static Block blockFog, blockSolidFog;

	public static Block blockInvisLight;

	public static Block blockTreasureFluid;
	public static Fluid treasureFluid;
}
