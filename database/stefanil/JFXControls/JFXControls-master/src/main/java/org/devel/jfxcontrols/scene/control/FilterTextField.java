/*
 * This document contains trade secret data which is the property of
 * IAV GmbH. Information contained herein may not be used,
 * copied or disclosed in whole or part except as permitted by written
 * agreement from IAV GmbH.
 *
 * Copyright (C) IAV GmbH / Gifhorn / Germany
 */
package org.devel.jfxcontrols.scene.control;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import com.google.common.base.Strings;

import org.controlsfx.control.textfield.CustomTextField;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 * A {@link CustomTextField} implementation which shows a filter symbol on the left side and a cross on the right in
 * case the field contains text. A click on the cross deletes the text.
 */
public class FilterTextField extends CustomTextField {

  private final StackPane removeIcon = getStackPane(FontAwesomeIcon.REMOVE, "removeFilterButton");
  private final StackPane filterIcon = getStackPane(FontAwesomeIcon.FILTER, "filterIcon");
}

