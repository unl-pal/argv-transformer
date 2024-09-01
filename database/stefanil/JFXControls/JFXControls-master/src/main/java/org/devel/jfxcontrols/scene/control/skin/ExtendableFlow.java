/**
 * 
 */
package org.devel.jfxcontrols.scene.control.skin;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.IndexedCell;

import com.sun.javafx.scene.control.skin.VirtualFlow;

/**
 *
 * @param <M>
 *            The type of the item stored in the cell
 * @param <I>
 *            The type of cell used by this virtualised control (e.g. TableRow,
 *            TreeTableRow)
 * 
 * @author stefan.illgen
 */
public class ExtendableFlow<M, I extends IndexedCell<M>> extends VirtualFlow<I> {

	private ObservableList<FlowExtension<M, I>> extensions;

	private SimpleIntegerProperty visibleCellCount;

	private IntegerProperty selectedCellIndex;
	private SimpleDoubleProperty fixedCellLength;
	private SimpleDoubleProperty visibleHeight;

}
