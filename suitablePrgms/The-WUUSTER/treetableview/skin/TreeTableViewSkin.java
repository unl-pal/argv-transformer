/**
 * 
 */
package org.devel.jfxcontrols.scene.control.treetableview.skin;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableRow;

import org.devel.jfxcontrols.scene.control.treetableview.command.Adjustable;
import org.devel.jfxcontrols.scene.control.treetableview.command.Expand;
import org.devel.jfxcontrols.scene.control.treetableview.command.Expandable;
import org.devel.jfxcontrols.scene.control.treetableview.command.MouseKeyboardEventMapper;
import org.devel.jfxcontrols.scene.control.treetableview.command.MouseKeyboardEventMapper.ExpandEventMapper;
import org.devel.jfxcontrols.scene.control.treetableview.command.MouseKeyboardEventMapper.RowAdjustEventMapper;
import org.devel.jfxcontrols.scene.control.treetableview.command.RowAdjust;

import com.sun.javafx.scene.control.skin.VirtualFlow;

/**
 * @author stefan.illgen
 *
 */
public class TreeTableViewSkin<T>
	extends
		com.sun.javafx.scene.control.skin.TreeTableViewSkin<T> implements Expandable<T> {

	private static final String ROW_ADJUST = "ROW_ADJUST";
	private static final String INPUT_MODE = "INPUT_MODE";
	private static final String EXPAND = "EXPAND";

	private AdjustableFlow<T, TreeTableRow<T>> adjustableFlow;
	private RowAdjustEventMapper<T, TreeTableRow<T>> rowAdjustEventMapper;
	private ExpandEventMapper<T, TreeTableRow<T>> expandEventMapper;
	private TreeItem<T> selected;
	private int cellCountBeforeExpand;

	@Override
	protected void layoutChildren(double x, double y, double w, double h) {

		System.out.println("adjustableFlow.getFirstVisibleCell(): "
			+ adjustableFlow.getFirstVisibleCell());
		System.out.println("needCellsRebuilt: " + needCellsRebuilt);
		System.out.println("needCellsReconfigured: " + needCellsReconfigured);
		System.out.println("needCellsRecreated: " + needCellsRecreated);

		boolean rowCountDirtyOld = false;
		int oldStart = -1;
		if (rowCountDirty && adjustableFlow != null) {
			// && getSelectionModel().getSelectedIndex() != -1) {
			System.out.println("layout");
			rowCountDirtyOld = rowCountDirty;
			oldStart = Math.toIntExact(Math.round(Math.floor((adjustableFlow.getPosition() * cellCountBeforeExpand))));
		}

		super.layoutChildren(x, y, w, h);

		if (adjustableFlow != null && rowCountDirtyOld
			&& oldStart != getSelectionModel().getSelectedIndex()) {
			System.out.println("show");
			System.out.println("getLength(selected): " + getLength(selected));
			System.out.println("getSelectionModel().getSelectedIndex(): "
				+ getSelectionModel().getSelectedIndex());
			System.out.println("oldStart: " + oldStart);
			adjustableFlow.show(getSelectionModel().getSelectedIndex(),
								getLength(selected),
								oldStart);
		}

	}

}
