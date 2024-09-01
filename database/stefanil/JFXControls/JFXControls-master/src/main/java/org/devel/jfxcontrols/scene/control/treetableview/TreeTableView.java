/**
 * 
 */
package org.devel.jfxcontrols.scene.control.treetableview;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableRow;

import org.devel.jfxcontrols.scene.control.treetableview.command.Adjustable;
import org.devel.jfxcontrols.scene.control.treetableview.command.Expand;
import org.devel.jfxcontrols.scene.control.treetableview.command.RowAdjust;

/**
 * @author stefan.illgen
 *
 */
public class TreeTableView<T, A extends Adjustable<T, TreeTableRow<T>>>
	extends
		javafx.scene.control.TreeTableView<T> {

	private ObjectProperty<RowAdjust<T, TreeTableRow<T>>> rowAdjust;

	private ObjectProperty<Expand<T, TreeTableRow<T>>> expand;

	private ObjectProperty<InputMode> inputMode;

	private ObjectProperty<TreeItem<T>> expandedTreeItem;

}
