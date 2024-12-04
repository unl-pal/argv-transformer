package com.temportalist.thaumicexpansion.common.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyReceiver;
import cofh.api.tileentity.*;
import cofh.api.transport.IItemDuct;
import cofh.core.network.ITileInfoPacketHandler;
import cofh.core.network.PacketCoFHBase;
import cofh.core.network.PacketHandler;
import cofh.core.network.PacketTileInfo;
import cofh.lib.util.helpers.BlockHelper;
import cofh.lib.util.helpers.EnergyHelper;
import cofh.lib.util.position.IRotateableTile;
import cofh.thermalexpansion.block.machine.BlockMachine;
import cofh.thermalexpansion.block.machine.TileMachineBase;
import cofh.thermalexpansion.util.Utils;
import com.temportalist.thaumicexpansion.api.common.tile.IOperation;
import com.temportalist.thaumicexpansion.api.common.tile.IOperator;
import com.temportalist.thaumicexpansion.common.TEC;
import com.temportalist.thaumicexpansion.common.block.BlockThaumicAnalyzer;
import com.temportalist.thaumicexpansion.common.lib.*;
import com.temportalist.thaumicexpansion.common.packet.PacketTileSync;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.api.research.ScanResult;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.lib.network.playerdata.PacketAspectPool;
import thaumcraft.common.lib.research.PlayerKnowledge;
import thaumcraft.common.lib.research.ResearchManager;
import thaumcraft.common.lib.research.ScanManager;

import java.util.*;

/**
 * @author TheTemportalist
 */
public class TEThaumicAnalyzer extends TileMachineBase implements ISidedInventory,
		IOperator,
		ITileInfoPacketHandler,
		IEnergyReceiver, IEnergyConnection,
		IAugmentable, IRedstoneControl,
		IRotateableTile, IReconfigurableFacing, IReconfigurableSides, ISidedTexture,
		IAspectContainer, IEssentiaTransport {

	public static final int hexagonProgressSteps = 13;

	public final int INPUT_MAIN = 1, INPUT_CAPACITOR = 0, OUTPUT_MAIN = 2;

	private HashMap<EnumAugmentTA, Integer> augmentList = new HashMap<EnumAugmentTA, Integer>();
	private AspectList aspects = new AspectList();
	private int currentColumnOffset = 0;

	private IOperation currentOperation = null;

	EnumSideTA[] sideTypes = new EnumSideTA[6];

}
