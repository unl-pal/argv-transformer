/**
 * 
 */
package org.devel.jfxcontrols.scene.control.skin;

import java.util.HashMap;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.IndexedCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import org.devel.jfxcontrols.scene.control.skin.animation.Flinger;

/**
 * @author stefan.illgen
 *
 */
@SuppressWarnings({
	"unchecked", "rawtypes"
})
public class FlowFlinger<M, I extends IndexedCell<M>> extends FlowExtension<M, I> {

	private Flinger<M, I> flinger;
	private double dragY;
}
