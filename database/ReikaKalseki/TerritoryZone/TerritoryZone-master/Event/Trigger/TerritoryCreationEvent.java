/*******************************************************************************
 * @author Reika Kalseki
 *
 * Copyright 2017
 *
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.TerritoryZone.Event.Trigger;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import Reika.TerritoryZone.Territory.TerritoryShape;

import cpw.mods.fml.common.eventhandler.Event;


public abstract class TerritoryCreationEvent extends Event {

	public final World world;
	public final String id;
	public final EntityPlayer player;

	public static class CreateTwoPoints extends TerritoryCreationEvent {

		public final int x1;
		public final int y1;
		public final int z1;
		public final int x2;
		public final int y2;
		public final int z2;

	}

	public static class CreateDirect extends TerritoryCreationEvent {

		public final int x;
		public final int y;
		public final int z;
		public final int radius;
		public final TerritoryShape shape;

	}

}
