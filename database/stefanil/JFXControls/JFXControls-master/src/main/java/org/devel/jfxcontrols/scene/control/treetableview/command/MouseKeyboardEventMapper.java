package org.devel.jfxcontrols.scene.control.treetableview.command;

import java.util.HashMap;
import java.util.Map;

import javafx.event.EventType;
import javafx.scene.control.Control;
import javafx.scene.control.IndexedCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import org.devel.jfxcontrols.scene.control.treetableview.command.RowAdjust.Action;

public class MouseKeyboardEventMapper {

	/**
	 * 
	 * @author stefan.illgen
	 *
	 * @param <T>
	 * @param <I>
	 */
	public static class RowAdjustEventMapper<T, I extends IndexedCell<T>>
		extends
			EventMapper<RowAdjust<T, I>, RowAdjust.Action, Adjustable<T, I>> {

		public Map<EventType<MouseEvent>, RowAdjust.Action> mouseFilter = new HashMap<EventType<MouseEvent>, RowAdjust.Action>() {
			private static final long serialVersionUID = -9005856313771120088L;
			{
				put(MouseEvent.MOUSE_PRESSED, RowAdjust.Action.INIT);
				put(MouseEvent.MOUSE_DRAGGED, RowAdjust.Action.DRAG);
				put(MouseEvent.MOUSE_RELEASED, RowAdjust.Action.ADJUST_AFTER_DRAG);
				// put(MouseEvent.MOUSE_MOVED, RowAdjust.Action.CONSUME);
				// put(MouseEvent.MOUSE_ENTERED, RowAdjust.Action.CONSUME);
				// put(MouseEvent.MOUSE_EXITED, RowAdjust.Action.CONSUME);
				// put(MouseEvent.MOUSE_ENTERED_TARGET,
				// RowAdjust.Action.CONSUME);
				// put(MouseEvent.MOUSE_EXITED_TARGET,
				// RowAdjust.Action.CONSUME);
			}
		};

		public Map<EventType<MouseEvent>, RowAdjust.Action> mouseHandler = new HashMap<EventType<MouseEvent>, RowAdjust.Action>() {
			private static final long serialVersionUID = -9005856313771120088L;
			{

			}
		};

		public Map<EventType<ScrollEvent>, Expand.Action> scrollFilter = new HashMap<EventType<ScrollEvent>, Expand.Action>() {
			private static final long serialVersionUID = -5431997953022846187L;
			{
				// put(ScrollEvent.ANY, Expand.Action.CONSUME);
			}
		};

		public Map<EventType<KeyEvent>, Expand.Action> keyFilter = new HashMap<EventType<KeyEvent>, Expand.Action>() {
			private static final long serialVersionUID = 660491684221683820L;
			{
				// put(KeyEvent.ANY, Expand.Action.CONSUME);
			}
		};

	}

	/**
	 * 
	 * @author stefan.illgen
	 *
	 * @param <T>
	 * @param <A>
	 */
	public static class ExpandEventMapper<T, A extends IndexedCell<T>>
		extends
			EventMapper<Expand<T, A>, Expand.Action, Expandable<T>> {

		public Map<EventType<MouseEvent>, Expand.Action> mouseFilter = new HashMap<EventType<MouseEvent>, Expand.Action>() {
			private static final long serialVersionUID = -9005856313771120088L;
			{
				put(MouseEvent.MOUSE_PRESSED, Expand.Action.NONE);
				put(MouseEvent.MOUSE_DRAGGED, Expand.Action.NONE);
				put(MouseEvent.MOUSE_RELEASED, Expand.Action.NONE);
				// must be defined as filter so the selection model can be used
				// (i.e. to access the cell index)
				put(MouseEvent.MOUSE_CLICKED, Expand.Action.EXPAND);
				put(MouseEvent.MOUSE_MOVED, Expand.Action.CONSUME);
				put(MouseEvent.MOUSE_ENTERED, Expand.Action.CONSUME);
				put(MouseEvent.MOUSE_EXITED, Expand.Action.CONSUME);
				put(MouseEvent.MOUSE_ENTERED_TARGET, Expand.Action.CONSUME);
				put(MouseEvent.MOUSE_EXITED_TARGET, Expand.Action.CONSUME);
			}
		};

		public Map<EventType<ScrollEvent>, Expand.Action> scrollFilter = new HashMap<EventType<ScrollEvent>, Expand.Action>() {
			private static final long serialVersionUID = -5431997953022846187L;
			{
				put(ScrollEvent.ANY, Expand.Action.CONSUME);
			}
		};

		public Map<EventType<KeyEvent>, Expand.Action> keyFilter = new HashMap<EventType<KeyEvent>, Expand.Action>() {
			private static final long serialVersionUID = 660491684221683820L;
			{
				put(KeyEvent.ANY, Expand.Action.CONSUME);
			}
		};

		public Map<EventType<MouseEvent>, Expand.Action> mouseHandler = new HashMap<EventType<MouseEvent>, Expand.Action>() {
			private static final long serialVersionUID = -9005856313771120088L;
			{
				// put(MouseEvent.MOUSE_PRESSED, Expand.Action.NONE);
				// put(MouseEvent.MOUSE_DRAGGED, Expand.Action.NONE);
				// put(MouseEvent.MOUSE_RELEASED, Expand.Action.NONE);
				// put(MouseEvent.MOUSE_MOVED, Expand.Action.NONE);
				// put(MouseEvent.MOUSE_ENTERED, Expand.Action.NONE);
				// put(MouseEvent.MOUSE_EXITED, Expand.Action.NONE);
				// put(MouseEvent.MOUSE_ENTERED_TARGET, Expand.Action.NONE);
				// put(MouseEvent.MOUSE_EXITED_TARGET, Expand.Action.NONE);
			}
		};

		public Map<EventType<ScrollEvent>, Expand.Action> scrollHandler = new HashMap<EventType<ScrollEvent>, Expand.Action>() {
			private static final long serialVersionUID = -5431997953022846187L;
			{
				// put(ScrollEvent.ANY, Expand.Action.NONE);
			}
		};

		public Map<EventType<KeyEvent>, Expand.Action> keyHandler = new HashMap<EventType<KeyEvent>, Expand.Action>() {
			private static final long serialVersionUID = 660491684221683820L;
			{
				// put(KeyEvent.ANY, Expand.Action.NONE);
			}
		};

	}

}
