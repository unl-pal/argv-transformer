/**
 * 
 */
package org.devel.jfxcontrols.scene.control;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

/**
 * @author stefan.illgen
 *
 */
public class FixedTreeTableView<T> extends TreeTableView<T> {

	/*
	 * Number of visible cells at one time. Splitted cells on top and bottom are
	 * counted as one cell.
	 */
	private IntegerProperty visibleCellCountProperty;

}
