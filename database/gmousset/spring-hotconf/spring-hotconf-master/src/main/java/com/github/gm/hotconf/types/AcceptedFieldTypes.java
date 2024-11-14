package com.github.gm.hotconf.types;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Accepted property field types.
 * 
 * @author Gwendal Mousset
 */
public enum AcceptedFieldTypes {

  /**
   * Accepted types.
   */

  /** String. */
  STRING(String.class, new StringConverter()),
  /** Integer. */
  INTEGER(Integer.class, new IntegerConverter()),
  /** int. */
  INT_PRIMITIVE(int.class, new IntegerConverter()),
  /** Long. */
  LONG(Long.class, new LongConverter()),
  /** long. */
  LONG_PRIMITIVE(long.class, new LongConverter()),
  /** Double. */
  DOUBLE(Double.class, new DoubleConverter()),
  /** double. */
  DOUBLE_PRIMITIVE(double.class, new DoubleConverter()),
  /** Float. */
  FLOAT(Float.class, new FloatConverter()),
  /** float. */
  FLOAT_PRIMITIVE(float.class, new FloatConverter());

  /** Accepted class. */
  private Class<?> clazz;
  /** Converter. */
  private TypeConverter<?> converter;
  /** Set of accepted types. */
  private static Set<AcceptedFieldTypes> set;
  /** Set of accepted classes. */
  private static Set<Class<?>> acceptedClasses;
  /** Map Accepted field types by class. */
  private static Map<Class<?>, AcceptedFieldTypes> map;

  static {
    set = new HashSet<>();
    set.add(STRING);
    set.add(INTEGER);
    set.add(INT_PRIMITIVE);
    set.add(LONG);
    set.add(LONG_PRIMITIVE);
    set.add(DOUBLE);
    set.add(DOUBLE_PRIMITIVE);
    set.add(FLOAT);
    set.add(FLOAT_PRIMITIVE);

    acceptedClasses = new HashSet<Class<?>>();
    map = new HashMap<>();
    for (AcceptedFieldTypes aft : set) {
      acceptedClasses.add(aft.clazz);
      map.put(aft.clazz, aft);
    }
  }

  /**
   * Enum constructor.
   * 
   * @param pClazz
   *          The accepted class.
   * @param pConverter
   *          The converter for this type.
   */
  private AcceptedFieldTypes(final Class<?> pClazz, final TypeConverter<?> pConverter) {
    this.clazz = pClazz;
    this.converter = pConverter;
  }

  /**
   * @return A set of all accepted classes.
   */
  public static Set<Class<?>> classes() {
    return acceptedClasses;
  }

  /**
   * @param pClazz
   *          A class.
   * @return The appropriate converter.
   */
  public static TypeConverter<?> converterForClass(final Class<?> pClazz) {
    return map.get(pClazz).converter;
  }
}