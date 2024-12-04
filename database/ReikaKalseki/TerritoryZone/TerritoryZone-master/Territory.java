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

import java.awt.Polygon;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import Reika.DragonAPI.Instantiable.Data.Immutable.BlockBox;
import Reika.DragonAPI.Instantiable.Data.Immutable.WorldLocation;
import Reika.DragonAPI.Instantiable.IO.LuaBlock;
import Reika.DragonAPI.Instantiable.IO.LuaBlock.LuaBlockDatabase;
import Reika.DragonAPI.Libraries.ReikaNBTHelper;
import Reika.DragonAPI.Libraries.ReikaNBTHelper.NBTTypes;
import Reika.DragonAPI.Libraries.ReikaPlayerAPI;
import Reika.DragonAPI.Libraries.IO.ReikaChatHelper;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaMathLibrary;
import Reika.DragonAPI.Libraries.MathSci.ReikaVectorHelper;
import Reika.TerritoryZone.Event.TerritoryCreateEvent;

public final class Territory {

	public final String id;
	public final WorldLocation origin;
	public final int radius;
	public final int color;
	private final Collection<Owner> owners = new HashSet();
	public final TerritoryShape shape;
	private final EnumSet<Protections> logging = ReikaJavaLibrary.getConditionalEnumSet(Protections.class, p -> p.logByDefault());
	private final EnumSet<Protections> chat = ReikaJavaLibrary.getConditionalEnumSet(Protections.class, p -> p.chatByDefault());
	private final EnumSet<Protections> protection = ReikaJavaLibrary.getConditionalEnumSet(Protections.class, p -> p.protectByDefault());

	//public Territory(int dim, int x, int y, int z, int r, String name, UUID uid, TerritoryShape sh) {
	//	this(new WorldLocation(dim, x, y, z), r, name, uid, sh);
	//}

	public static enum TerritoryShape {
		CUBE("Cubic Zone"),
		PRISM("Full-height square perimeter"),
		SPHERE("Spherical Zone"),
		CYLINDER("Full-height circular perimeter");

		public final String desc;

		public static final TerritoryShape[] list = values();
	}

	public static final class Owner {

		public final String name;
		public final UUID id;

		}

	public static enum Protections {
		public final String desc;

		public static Protections[] list = values();

		}

	public static class TerritoryLuaBlock extends LuaBlock {

	}

}
