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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

import Reika.DragonAPI.Auxiliary.Trackers.PlayerHandler.PlayerTracker;
import Reika.DragonAPI.Instantiable.IO.PacketTarget;
import Reika.DragonAPI.Instantiable.IO.PacketTarget.PlayerTarget;
import Reika.DragonAPI.Libraries.IO.ReikaPacketHelper;

public class TerritoryDispatcher implements PlayerTracker {

	public static final TerritoryDispatcher instance = new TerritoryDispatcher();

	public static enum PacketInfo {
		TERRITORY(),
		CLEAR(),
		VERIFY();

		public static final PacketInfo[] list = values();
	}

}
