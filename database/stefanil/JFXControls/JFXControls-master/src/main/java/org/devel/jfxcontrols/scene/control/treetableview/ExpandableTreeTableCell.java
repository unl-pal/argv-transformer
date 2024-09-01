package org.devel.jfxcontrols.scene.control.treetableview;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableCell;
import javafx.scene.layout.BorderPane;

/**
 * 
 * @author stefan.illgen
 *
 * @param <S>
 *            type in tree item
 * @param <T>
 */
public class ExpandableTreeTableCell<S, T> extends TreeTableCell<S, T> {

	private BorderPane rootContainer;
	private Label label;

	// @Override
	// public void resize(double width, double height) {
	// super.resize(snapPosition(width), snapPosition(height));
	// }

}
