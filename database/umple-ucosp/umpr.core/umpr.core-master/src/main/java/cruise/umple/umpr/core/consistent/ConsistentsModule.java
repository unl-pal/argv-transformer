/**
 * 
 */
package cruise.umple.umpr.core.consistent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cruise.umple.umpr.core.ImportAttrib;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * Consistents Module setting up all injection 
 * 
 * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
 * 
 * @since 11 Mar 2015
 */
public class ConsistentsModule extends AbstractModule {
  
  /**
   * Used to mark in the {@link Consistents} module primarily, however any field may be annotated with this if it 
   * requires the {@link Module} or {@link ObjectMapper} for JSON parsing. 
   * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
   * @since 1 Apr 2015
   */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  @BindingAnnotation
  public @interface ConsistentsJacksonConfig {
    /* Intentionally empty */
  }

}
