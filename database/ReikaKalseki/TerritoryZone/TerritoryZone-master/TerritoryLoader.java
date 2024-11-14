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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.UUID;

import org.apache.commons.codec.Charsets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;

import Reika.DragonAPI.Exception.InstallationException;
import Reika.DragonAPI.Exception.RegistrationException;
import Reika.DragonAPI.Exception.UserErrorException;
import Reika.DragonAPI.IO.ReikaFileReader;
import Reika.DragonAPI.Instantiable.Data.Immutable.CommutativePair;
import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;
import Reika.DragonAPI.Instantiable.Data.Maps.MultiMap;
import Reika.DragonAPI.Instantiable.IO.LuaBlock;
import Reika.DragonAPI.Instantiable.IO.LuaBlock.LuaBlockDatabase;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.TerritoryZone.Territory.Protections;
import Reika.TerritoryZone.Territory.TerritoryLuaBlock;
import Reika.TerritoryZone.Territory.TerritoryShape;
import Reika.TerritoryZone.Event.TerritoryCollisionEvent;
import Reika.TerritoryZone.Event.TerritoryRegisterEvent;
import Reika.TerritoryZone.Event.TerritoryUnregisterEvent;

public class TerritoryLoader {

	public static final TerritoryLoader instance = new TerritoryLoader();

	private final MultiMap<UUID, Territory> ownerMap = new MultiMap();
	private final Collection<Territory> territories = new ArrayList();

	private final LuaBlockDatabase territoryData = new LuaBlockDatabase();

	private final Territory exampleTerritory = new Territory("example", new WorldLocation(0, 1023, 90, -1304), 128, 0xffffff, TerritoryShape.CUBE).addOwner(UUID.fromString("504e35e4-ee36-45e0-b1d3-7ad419311644"), "SomePlayer").addOwner(UUID.fromString("759fc6d2-1868-4c90-908c-81bf9b3cd973"), "User2");

}
