package org.devel.jfxcontrols.scene.control;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableView;

import java.util.function.Predicate;

/**
 * A customized {@link TableView} wrapping the original items of the table view into a {@link FilteredList}. Therefore
 * it offers the client to register {@link Predicate}s while each of them gets conjugated to define the filter out
 * specific rows.
 *
 * <p>
 * Such a {@link Predicate} is typically used to evaluate the current content of an associated filter text given by the
 * column header of a {@link FilterableTableColumn}. An example on how to register it is given below:
 *
 * <pre>
 * {@code
 * fttvTable.addFilterPredicate((Person s) -> ftcColumnX.getCellData(s).contains(ftcColumnX.getFilterText().trim()),
 *     ftcColumnX.filterTextProperty());}</pre>
 *
 * The example defines a predicate which checks the containment of the trimmed filter text of the column on each row.
 * If the filter text is not contained inside the column's cell data the row gets filtered out and vice versa. You
 * also have to pass a {@link ReadOnlyStringProperty} (i.e. the observable filter text) to enable the
 * {@link FilterableTableView} to recheck the predicate on filter text changes.
 * </p>
 *
 * <p>
 * Furthermore the {@link FilterableTableView} will automatically prevent filtering if the filter text is empty. Thus
 * if no column serves a filter text (i.e. all column's filter text is empty) all rows will get visible.
 * </p>
 *
 * @see TableView
 */
public class FilterableTableView<S> extends TableView<S> {

  private final ObservableList<ObjectBinding<Predicate<S>>> filterPredicates = FXCollections.observableArrayList();

  private FilteredList<S> filteredItems;
}
