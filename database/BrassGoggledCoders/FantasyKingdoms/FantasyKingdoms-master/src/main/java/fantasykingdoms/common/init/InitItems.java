package fantasykingdoms.common.init;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.potion.Potion;

import cpw.mods.fml.common.registry.GameRegistry;

import boilerplate.common.baseclasses.items.tools.BaseAxe;
import boilerplate.common.baseclasses.items.tools.BaseHoe;
import boilerplate.common.baseclasses.items.tools.BasePickaxe;
import boilerplate.common.baseclasses.items.tools.BaseShovel;
import boilerplate.common.baseclasses.items.tools.BaseSword;
import boilerplate.common.utils.helpers.RegistryHelper;
import fantasykingdoms.common.FantasyKingdoms;
import fantasykingdoms.common.items.BaseItem;
import fantasykingdoms.common.items.ItemDeflectionBelt;
import fantasykingdoms.common.items.ItemGem;
import fantasykingdoms.common.items.ItemIngot;
import fantasykingdoms.common.items.ItemNormalArmor;
import fantasykingdoms.common.items.ItemNugget;
import fantasykingdoms.common.items.ItemPotionBelt;
import fantasykingdoms.common.items.ItemPotionRing;
import fantasykingdoms.common.items.ItemStygiumRing;
import fantasykingdoms.common.items.ItemWizardsKey;
import fantasykingdoms.common.items.dwarven.ItemEmptyTankard;
import fantasykingdoms.common.items.dwarven.ItemRune;
import fantasykingdoms.common.items.dwarven.ItemTankard;
import fantasykingdoms.common.items.tools.ItemBattleaxe;
import fantasykingdoms.common.items.tools.ItemCrossbow;
import fantasykingdoms.common.items.tools.ItemDagger;
import fantasykingdoms.common.items.tools.ItemLongbow;
import fantasykingdoms.common.items.tools.ItemMace;
import fantasykingdoms.common.items.tools.ItemRuneblade;
import fantasykingdoms.common.items.tools.ItemShortbow;
import fantasykingdoms.common.lib.ModInfo;

/**
 * Created by Toby on 20/02/2015.
 */
public class InitItems
{
	public static Item itemGem;
	public static Item itemIngot;
	public static Item itemNugget;

	public static Item itemEmptyTankard;
	public static Item itemFullTankard;

	public static Item itemPlateHelmet, itemPlateChestplate, itemPlateLeggings, itemPlateBoots;
	// Tunic
	public static Item itemChainmailHelmet, itemChainmailChestplate, itemChainmailLeggings, itemChainmailBoots;
	// Crowned Helmet | Golden, with Red Cloak
	public static Item itemKingHelmet, itemKingChestplate, itemKingLeggings, itemKingBoots;
	//
	public static Item itemHellfireHelmet, itemHellfireChestplate, itemHellfireLeggings, itemHellfireBoots;

	public static Item itemMythrilChainHelmet, itemMythrilChainChestplate, itemMythrilChainLeggings, itemMythrilChainBoots;

	public static Item itemMythrilPlateHelmet, itemMythrilPlateChestplate, itemMythrilPlateLeggings, itemMythrilPlateBoots;

	public static Item itemDragonleatherHelmet, itemDragonleatherChestplate, itemDragonleatherLeggings, itemDragonleatherBoots;

	public static Item itemDragonscaleHelmet, itemDragonscaleChestplate, itemDragonscaleLeggings, itemDragonscaleBoots;

	public static Item itemJewelledSword, itemJewelledPickaxe, itemJewelledAxe, itemJewelledShovel, itemJewelledHoe;

	public static Item itemSilverSword, itemSilverPickaxe, itemSilverAxe, itemSilverShovel, itemSilverHoe;

	public static Item itemMythrilSword, itemMythrilPickaxe, itemMythrilAxe, itemMythrilShovel, itemMythrilHoe;

	public static Item itemDragonboneSword, itemDragonbonePickaxe, itemDragonboneAxe, itemDragonboneShovel, itemDragonboneHoe;

	public static Item itemLongbow, itemShortbow, itemCrossbow, itemIronwoodCrossbow, itemElfwoodShortbow, itemElfwoodLongbow;

	public static Item itemStoneWarhammer, itemIronWarhammer, itemGoldWarhammer, itemDiamondWarhammer, itemJewelledWarhammer, itemSilverWarhammer,
			itemMithrilWarhammer;

	public static Item itemStoneBattleaxe, itemIronBattleaxe, itemGoldBattleaxe, itemDiamondBattleaxe, itemJewelledBattleaxe, itemSilverBattleaxe,
			itemMithrilBattleaxe;

	public static Item itemStoneMace, itemIronMace, itemGoldMace, itemDiamondMace, itemJewelledMace, itemSilverMace, itemMithrilMace;

	public static Item itemStoneDagger, itemIronDagger, itemGoldDagger, itemDiamondDagger, itemJewelledDagger, itemSilverDagger, itemMithrilDagger;

	public static Item itemStoneShortsword, itemIronShortsword, itemGoldShortsword, itemDiamondShortsword, itemJewelledShortsword,
			itemSilverShortsword, itemMithrilShortsword;

	public static Item itemStoneLongsword, itemIronLongsword, itemGoldLongsword, itemDiamondLongsword, itemJewelledLongsword, itemSilverLongsword,
			itemMithrilLongsword;

	public static Item itemStoneBroadsword, itemIronBroadsword, itemGoldBroadsword, itemDiamondBroadsword, itemJewelledBroadsword,
			itemSilverBroadsword, itemMithrilBroadsword;

	public static Item itemStoneGreatsword, itemIronGreatsword, itemGoldGreatsword, itemDiamondGreatsword, itemJewelledGreatsword,
			itemSilverGreatsword, itemMithrilGreatsword;

	public static Item itemRuneblade;

	public static Item itemStygiumRing;

	public static Item itemWizardsKey;

	public static Item itemBlankRune, itemRune;

	public static Item itemSpeedBelt, itemDeflectionBelt, itemOneRing;
}
