/**
 * 
 */
package org.devel.jfxcontrols.scene.control.treetableview.command;

import java.util.Map;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * 
 * @author stefan.illgen
 *
 * @param <C>
 * @param <A>
 * @param <R>
 */
@SuppressWarnings({
	"unchecked", "rawtypes"
})
public abstract class EventMapper<C extends Command<A, R>, A extends Command.Action<A>, R extends Receiver> {

	private C command;
	private Control control;
	private Map<EventType, EventHandler> all;
}
