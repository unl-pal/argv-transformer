/**
 * 
 */
package cruise.umple.umpr.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import cruise.umple.compiler.UmpleImportType;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;

/**
 * @author kevin
 *
 */
public abstract class UmpleImportTypes {

  private static final Map<String, UmpleImportType> ALL_TYPES;
  // reflectively build up a map of all types
  static {
    ImmutableMap.Builder<String, UmpleImportType> allTypesBld = ImmutableMap.builder();
    
    
    List<Field> fields = Arrays.asList(UmpleImportType.class.getFields());
    fields.stream().filter(f -> {
      final int mods = f.getModifiers();
      
      return f.getType() == UmpleImportType.class && 
          f.isAccessible() && 
          Modifier.isStatic(mods) && Modifier.isPublic(mods);
    }).forEach(f -> {
      try {
        allTypesBld.put(f.getName(), (UmpleImportType)f.get(null));
      } catch (IllegalArgumentException | IllegalAccessException e) {
        // Neither exception should be thrown as the field is static and public
        Throwables.propagate(e);
      }
    });
    
    ALL_TYPES = allTypesBld.build();
  }
    
}
