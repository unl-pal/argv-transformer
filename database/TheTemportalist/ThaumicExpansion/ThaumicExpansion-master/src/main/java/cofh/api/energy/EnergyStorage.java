package cofh.api.energy;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Reference implementation of {@link IEnergyStorage}. Use/extend this or implement your own.
 * 
 * @author King Lemming
 * 
 */
public class EnergyStorage implements IEnergyStorage {

	protected int energy;
	protected int capacity;
	protected int maxReceive;
	protected int maxExtract;

}
