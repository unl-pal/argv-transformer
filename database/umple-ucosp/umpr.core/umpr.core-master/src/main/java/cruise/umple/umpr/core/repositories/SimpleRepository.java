/**
 * 
 */
package cruise.umple.umpr.core.repositories;

import static com.google.common.base.Preconditions.checkArgument;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cruise.umple.umpr.core.DiagramType;
import cruise.umple.umpr.core.License;
import cruise.umple.umpr.core.Repository;
import cruise.umple.umpr.core.entities.ImportEntity;

import com.google.common.collect.ImmutableList;

/**
 * {@link SimpleRepository} is a means of defining repositories quickly and consistently. This class aims to use an
 * Annotation based scheme to ease the implementation of {@link Repository} types. 
 *
 * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
 *
 * @since Apr 9, 2015
 */
abstract class SimpleRepository implements Repository {
  
  /**
   * Marks a field that will be the repository name from {@link SimpleRepository}. This must mark a {@link String} or
   * static method that returns a {@link String}.
   *
   * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
   *
   * @since Apr 9, 2015
   */
  @Target({ElementType.FIELD, ElementType.METHOD})
  @Retention(RetentionPolicy.RUNTIME)
  public @interface Name {
    // no content
  }
  
  /**
   * Marks a field that will be the repository Description from {@link SimpleRepository}. This must mark a 
   * {@link String} or static method that returns a {@link String}.
   *
   * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
   *
   * @since Apr 9, 2015
   */
  @Target({ElementType.FIELD, ElementType.METHOD})
  @Retention(RetentionPolicy.RUNTIME)
  public @interface Description {
    // no content
  }
  
  /**
   * Marks a field that will be the repository Remote URL from {@link SimpleRepository}. This must mark a {@link String}
   * or {@link URL} or static method that returns a {@link String} or {@link URL}.
   *
   * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
   *
   * @since Apr 9, 2015
   */
  @Target({ElementType.FIELD, ElementType.METHOD})
  @Retention(RetentionPolicy.RUNTIME)
  public @interface Remote {
    // no content
  }
  
  /**
   * Marks a field that will be the {@link DiagramType} from {@link SimpleRepository}. This must mark a 
   * {@link DiagramType} or static method that returns a {@link DiagramType}.
   *
   * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
   *
   * @since Apr 9, 2015
   */
  @Target({ElementType.FIELD, ElementType.METHOD})
  @Retention(RetentionPolicy.RUNTIME)
  public @interface DType {
    // no content
  }
  
  /**
   * Marks a field that will be the {@link License} from {@link SimpleRepository}. This must mark a {@link License} or a
   * static method that returns a {@link License}.
   *
   * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
   *
   * @since Apr 9, 2015
   */
  @Target({ElementType.FIELD, ElementType.METHOD})
  @Retention(RetentionPolicy.RUNTIME)
  public @interface CLicense {
    // no content
  }
  
  private final String name;
  private final String description;
  private final Optional<URL> remoteLoc;
  private final DiagramType diagramType;
  private final License license;
  
  private final Logger log;
  
}
