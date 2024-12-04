package com.temportalist.thaumicexpansion.common.item;

import cofh.api.item.IAugmentItem;
import com.google.common.collect.Sets;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;
import java.util.Set;

/**
 * @author TheTemportalist
 */
public class ItemAugment extends Item implements IAugmentItem {

	private final String modid, baseName;
	private final String[] names;
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	private final Set<String> defaultSet;

}
