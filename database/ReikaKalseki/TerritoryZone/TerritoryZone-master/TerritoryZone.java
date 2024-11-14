/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.TerritoryZone;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.Random;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;

import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.DragonOptions;
import Reika.DragonAPI.Auxiliary.Trackers.CommandableUpdateChecker;
import Reika.DragonAPI.Auxiliary.Trackers.PlayerHandler;
import Reika.DragonAPI.Base.DragonAPIMod;
import Reika.DragonAPI.Base.DragonAPIMod.LoadProfiler.LoadPhase;
import Reika.DragonAPI.Instantiable.IO.ControlledConfig;
import Reika.DragonAPI.Instantiable.IO.ModLogger;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;
import Reika.TerritoryZone.Command.PlotTerritoriesCommand;
import Reika.TerritoryZone.Command.ReloadTerritoriesCommand;
import Reika.TerritoryZone.Command.TerritoryTeleportCommand;
import Reika.TerritoryZone.Event.TerritoryLoggingEvent;
import Reika.TerritoryZone.Event.TerritoryReloadedEvent;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.relauncher.Side;


@Mod( modid = "TerritoryZone", name="TerritoryZone", version = "v@MAJOR_VERSION@@MINOR_VERSION@", certificateFingerprint = "@GET_FINGERPRINT@", dependencies="required-after:DragonAPI", acceptableRemoteVersions="*")

public class TerritoryZone extends DragonAPIMod {

	public static final String packetChannel = "TerritoryData";

	static final Random rand = new Random();

	public static final String currentVersion = "v@MAJOR_VERSION@@MINOR_VERSION@";

	@Instance("TerritoryZone")
	public static TerritoryZone instance = new TerritoryZone();

	public static final ControlledConfig config = new ControlledConfig(instance, TerritoryOptions.optionList, null);

	public static ModLogger logger;

	//private String version;

	@SidedProxy(clientSide="Reika.TerritoryZone.TerritoryClient", serverSide="Reika.TerritoryZone.TerritoryCommon")
	public static TerritoryCommon proxy;
}
