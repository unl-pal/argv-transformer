package com.temportalist.thaumicexpansion.client.gui;

import cofh.core.network.PacketCoFHBase;
import cofh.core.network.PacketHandler;
import cofh.core.network.PacketTileInfo;
import cofh.lib.gui.element.ElementButton;
import cofh.lib.gui.element.ElementEnergyStored;
import cofh.lib.gui.element.TabBase;
import cofh.thermalexpansion.gui.client.GuiAugmentableBase;
import com.temportalist.thaumicexpansion.common.TEC;
import com.temportalist.thaumicexpansion.common.inventory.ContainerThaumicAnalyzer;
import com.temportalist.thaumicexpansion.common.tile.TEThaumicAnalyzer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.client.lib.UtilsFX;

/**
 * http://pastebin.com/ai8acZYP
 *
 * @author TheTemportalist
 */
public class GuiAnalyzer extends GuiAugmentableBase {

	private static final ResourceLocation
			background = new ResourceLocation(TEC.MODID, "textures/gui/thaumicAnalyzer.png"),
			hexagon = new ResourceLocation(TEC.MODID, "textures/gui/progress.png");
	private static final int hexagonHeight = TEThaumicAnalyzer.hexagonProgressSteps * 54;

	private TabBase redstoneTab, configTab;

	private int[][] aspectSlots;

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		// -1 released, 0 left, 1 right, 2 center
		if (mouseButton == 0 || mouseButton == 1) {
			for (int i = 0; i < this.aspectSlots.length; i++) {
				if (this.isMouseOverArea(mouseX, mouseY, this.aspectSlots[i])) {
					//FMLLog.info((i + this.getAspectOffset()) + "");
					PacketCoFHBase packet = new PacketTileInfo(
							this.container().tile
					).addString("ADDASPECT").addInt(
							i + this.getAspectOffset()
					).addBool(mouseButton == 0);
					PacketHandler.sendToServer(packet);
					PacketHandler.sendToAll(packet);
					break;
				}
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

		int centerX = (this.width / 2);
		int centerY = (this.height / 2);

		// 30x11 for hexagon
		if (this.container().tile.isProcessing()) {
			int progress = this.container().tile.getProgress();
			this.bindTexture(GuiAnalyzer.hexagon);
			Gui.func_146110_a(
					centerX - 62, centerY - 77, 0, progress * 54, 64, 54,
					64, GuiAnalyzer.hexagonHeight
			);
		}

		int indexOffset = this.getAspectOffset();
		AspectList aspectList = this.container().tile.getAspects();
		Aspect[] aspects = aspectList.getAspectsSorted();
		for (int i = 0; i < this.aspectSlots.length; i++) {
			int aspectI = i + indexOffset;
			if (aspectI < aspects.length) {
				int x = this.aspectSlots[i][0];
				int y = this.aspectSlots[i][1];
				UtilsFX.drawTag(x, y,
						aspects[aspectI], aspectList.getAmount(aspects[aspectI]),
						0, this.zLevel
				);
			}
		}

	}

}
