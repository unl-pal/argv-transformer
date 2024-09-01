package org.devel.jfxcontrols.scene.control;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableCell;
import javafx.scene.layout.BorderPane;

public class TTTVCell extends TreeTableCell<String, String> {

	private BorderPane rootContainer;
	private Label label;

	// private void expandTreeItem() {
	// // access tree item
	// TreeTableRow<String> row = (TreeTableRow<String>) getParent();
	// TreeItem<String> treeItem = row.getTreeItem();
	// treeItem.setExpanded(!treeItem.isExpanded());
	// }

}
