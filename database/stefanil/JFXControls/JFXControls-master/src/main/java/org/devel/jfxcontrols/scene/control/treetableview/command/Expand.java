/**
 * 
 */
package org.devel.jfxcontrols.scene.control.treetableview.command;

import javafx.animation.Transition;
import javafx.scene.control.IndexedCell;

/**
 * 
 * @author stefan.illgen
 *
 * @param <T>
 *            Shared Type of Expandable and Adjustable
 * @param <E>
 *            Expandable
 * @param <A>
 *            Adjustable
 */
public class Expand<T, A extends IndexedCell<T>> extends Transition
	implements
		Command<Expand.Action, Expandable<T>> {

	public enum Action implements Command.Action<Expand.Action> {
		CONSUME, EXPAND, NONE;

		private double y;
		private boolean animate;
		private Object source;
		private Object target;
		private int count;

		private Expandable<T> expandable;
	private RowAdjust<T, A> rowAdjust;

}
