package com.corejsf;

import java.util.Arrays;
import java.util.Comparator;

import javax.faces.model.DataModel;

public class SortFilterModel<E> extends DataModel<E> {
   private DataModel<E> model;
   private Integer[] rows;
}
