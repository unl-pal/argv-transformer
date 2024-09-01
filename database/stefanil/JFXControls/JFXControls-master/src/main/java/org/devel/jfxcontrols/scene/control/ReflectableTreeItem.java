package org.devel.jfxcontrols.scene.control;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;

/**
 * This {@link ReflectableTreeItem} gets grounded by a special type (i.e. an
 * instance of {@link Class}). It may also create an arbitrary amount of
 * instances of the type using the default constructor.
 * 
 * @author stefan.illgen
 * 
 */
public class ReflectableTreeItem<T extends Node> extends TreeItem<String> {

	private Class<T> ground;
}
