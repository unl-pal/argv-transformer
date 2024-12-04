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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import net.minecraft.client.Minecraft;

import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TerritoryCache {

	public static final TerritoryCache instance = new TerritoryCache();

	private final Collection<Territory> zones = new ArrayList();

}
