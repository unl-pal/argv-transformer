/**
 * 
 */
package org.devel.jfxcontrols.scene.control.skin;

import java.util.ArrayList;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;

import com.sun.javafx.scene.control.skin.TreeTableViewSkin;
import com.sun.javafx.scene.control.skin.VirtualFlow;

/**
 * @author stefan.illgen
 *
 */
public class ExtandableTreeTableViewSkin<S> extends TreeTableViewSkin<S>
		implements TreeItemHolder<S, TreeTableRow<S>> {

	private ExtendableFlow<S, TreeTableRow<S>> extensibleFlow;

}
