/**
 * 
 */
package org.devel.jfxcontrols.scene.control.skin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Region;

/**
 * @author stefan.illgen
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class FlowExtension<M, I extends IndexedCell<M>> {

	private ExtendableFlow<M, I> extensibleFlow;
	private ObjectProperty<SelectionModel<TreeItem<M>>> selectionModel;
	private SimpleDoubleProperty fixedCellSize;
	private SimpleIntegerProperty rowCount;
	private List<FlowExtension<M, I>> children;

	// Map<EventType<E>, EventHandler<E>> createTypedEventHandlers();

	// void extend(ExtensibleFlow<M, I> extensibleFlow);

}
