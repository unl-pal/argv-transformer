/** filtered and transformed by PAClab */
package com.temportalist.thaumicexpansion.client.gui;

import org.sosy_lab.sv_benchmarks.Verifier;

/**
 * http://pastebin.com/ai8acZYP
 *
 * @author TheTemportalist
 */
public class GuiAnalyzer {

	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		// -1 released, 0 left, 1 right, 2 center
		if (mouseButton == 0 || mouseButton == 1) {
			for (int i = 0; i < Verifier.nondetInt(); i++) {
				if (Verifier.nondetBoolean()) {
					break;
				}
			}
		}
	}

	/** PACLab: suitable */
	 protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		int centerX = (Verifier.nondetInt() / 2);
		int centerY = (Verifier.nondetInt() / 2);

		// 30x11 for hexagon
		if (Verifier.nondetBoolean()) {
			int progress = Verifier.nondetInt();
		}

		int indexOffset = Verifier.nondetInt();
		for (int i = 0; i < Verifier.nondetInt(); i++) {
			int aspectI = i + indexOffset;
			if (aspectI < Verifier.nondetInt()) {
				int x = aspectSlots[i][0];
				int y = aspectSlots[i][1];
			}
		}

	}

}
