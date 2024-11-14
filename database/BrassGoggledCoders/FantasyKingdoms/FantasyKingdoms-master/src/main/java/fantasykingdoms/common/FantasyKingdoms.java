package fantasykingdoms.common;

import net.minecraft.creativetab.CreativeTabs;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.common.MinecraftForge;

import fantasykingdoms.common.init.InitBlocks;
import fantasykingdoms.common.init.InitConfig;
import fantasykingdoms.common.init.InitItems;
import fantasykingdoms.common.init.InitMaterials;
import fantasykingdoms.common.init.InitRecipes;
import fantasykingdoms.common.lib.CreativeTabFantasyKingdoms;
import fantasykingdoms.common.lib.ModInfo;
import fantasykingdoms.common.lib.events.EventHandlerFML;
import fantasykingdoms.common.lib.events.EventHandlerForge;
import fantasykingdoms.common.util.OreDictionaryHandler;
import fantasykingdoms.common.worldgen.WorldGeneratorFantasyKingdoms;

@Mod(modid = ModInfo.MODID, name = ModInfo.NAME, version = ModInfo.Version, dependencies = "required-after:boilerplate; required-after:Baubles")
public class FantasyKingdoms
{
	/**
	 * Opposite to shadewood (holywood?), Decorative dwarven blocks (runic
	 * patterns, decorative single rune blocks, gilded blocks etc)
	 */
	@Mod.Instance("FantasyKingdoms")
	public static FantasyKingdoms modInstance;

	@SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.COMMON_PROXY)
	public static CommonProxy proxy;

	public static CreativeTabs tabKingdoms = new CreativeTabFantasyKingdoms(ModInfo.MODID);
}
