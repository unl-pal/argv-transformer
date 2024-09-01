/*
 * This document contains trade secret data which is the property of
 * IAV GmbH. Information contained herein may not be used,
 * copied or disclosed in whole or part except as permitted by written
 * agreement from IAV GmbH.
 *
 * Copyright (C) IAV GmbH / Gifhorn / Germany
 */
package org.devel.jfxcontrols.sample;

import com.google.common.base.MoreObjects;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import org.devel.jfxcontrols.scene.control.FilterableTableColumn;
import org.devel.jfxcontrols.scene.control.FilterableTableView;

public class FilterableTableViewExample {

  @FXML
  public FilterableTableColumn<Person, String> ftcColumnX;
  @FXML
  public FilterableTableColumn<Person, String> ftcColumnY;
  @FXML
  public FilterableTableColumn<Person, String> ftcColumnZ;
  @FXML
  private FilterableTableView<Person> fttvTable;

  public class Person {
    private final String name;
    private final String age;
    private final String place;
  }
}
