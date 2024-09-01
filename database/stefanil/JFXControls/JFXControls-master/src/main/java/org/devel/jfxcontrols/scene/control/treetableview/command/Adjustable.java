/**
 * 
 */
package org.devel.jfxcontrols.scene.control.treetableview.command;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.scene.control.IndexedCell;

/**
 * @author stefan.illgen
 *
 */
public interface Adjustable<T, I extends IndexedCell<T>> extends Receiver {

}
