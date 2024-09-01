/**
 * 
 */
package org.devel.jfxcontrols.scene.control.treetableview.skin;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.IndexedCell;

import org.devel.jfxcontrols.scene.control.treetableview.command.Adjustable;

import com.sun.javafx.scene.control.skin.VirtualContainerBase;
import com.sun.javafx.scene.control.skin.VirtualFlow;

/**
 * @author stefan.illgen
 *
 */
public class AdjustableFlow<T, I extends IndexedCell<T>> extends VirtualFlow<I>
	implements
		Adjustable<T, I> {

	private DoubleProperty absPosition;
	private IntegerProperty visibleCellCount;
	private SimpleDoubleProperty fixedCellLength;
	private final ReadOnlyDoubleWrapper maxPosition = new ReadOnlyDoubleWrapper(0);
	private final ReadOnlyDoubleWrapper visibleHeight = new ReadOnlyDoubleWrapper(0);
	private final ReadOnlyIntegerWrapper totalHeight = new ReadOnlyIntegerWrapper(0);
	private final ReadOnlyListWrapper<I> visibleCells = new ReadOnlyListWrapper<I>(FXCollections.observableArrayList(new ArrayList<I>()));
	private final ReadOnlyIntegerWrapper totalCellCount = new ReadOnlyIntegerWrapper(0);
	private int selectedIndex = -1;
	private SimpleIntegerProperty firstCellIndex;
	private ObjectProperty<I> firstVisibleCell;
	private BooleanProperty cellCountChanged;
	private int lastCellCount;
	private double selectedItemCount = -1;
	private double lastCellPosition;

	private final ChangeListener<Boolean> needsLayoutListener = (obs, oldV, newV) -> {
		System.out.println("needs layout change listener called ..");
		if (oldV && !newV && selectedIndex != -1 && getVisibleCell(selectedIndex) != null) {

			System.out.println("selectedIndex: " + selectedIndex);
			System.out.println("selectedItemCount: " + selectedItemCount);
			System.out.println("getFixedCellLength(): " + getFixedCellLength());
			System.out.println("getCellPosition(getVisibleCell(selectedIndex)): "
				+ getCellPosition(getVisibleCell(selectedIndex)));

			show(selectedIndex,
				 selectedItemCount * getFixedCellLength(),
				 getCellPosition(getVisibleCell(selectedIndex)));

			lastCellPosition = -1;
			selectedIndex = -1;
			selectedItemCount = -1;
		}
	};

	private T selectedItem;

}
