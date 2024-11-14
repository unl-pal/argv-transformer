package fantasykingdoms.common.blocks.dwarven;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import fantasykingdoms.common.blocks.BaseContainerBlock;
import fantasykingdoms.common.tiles.TileBarrel;

public class BlockBarrel extends BaseContainerBlock
{
	@SideOnly(Side.CLIENT)
	private IIcon iconTop, iconBottom;

}
