package org.devel.jfxcontrols.scene.control.skin;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;

import org.devel.jfxcontrols.scene.control.skin.animation.SingularExpander;

/**
 * @author stefan.illgen
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FlowTreeExpander<M, I extends IndexedCell<M>> extends
		FlowExtension<M, I> {

	// private TreeItem<M> expandedItem;
	private ObjectProperty<TreeItem<M>> root;
	private SimpleBooleanProperty singular;
	private SingularExpander<M, I> expander;

	private ObjectProperty<TreeItem<M>> expandedItem;
	private SimpleObjectProperty<FlowAdjuster<M, I>> adjuster;

	private boolean dragging;
	private TreeItemHolder treeItemHolder;

	/**
	 * <p>
	 * Represents the number of tree nodes presently able to be visible in the
	 * TreeTableView. This is essentially the count of all expanded tree items,
	 * and their children.
	 *
	 * <p>
	 * For example, if just the root node is visible, the expandedItemCount will
	 * be one. If the root had three children and the root was expanded, the
	 * value will be four.
	 */
	private IntegerProperty expandedItemCount = new SimpleIntegerProperty(this,
			"expandedItemCount", 0);

	private DoubleProperty visibleSize;

}
