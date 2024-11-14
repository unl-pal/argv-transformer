/**
 * 
 */
package cruise.umple.umpr.core.consistent;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URL;
import java.nio.file.Path;
import java.sql.Date;
import java.sql.Time;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Logger;

import cruise.umple.umpr.core.DiagramType;
import cruise.umple.umpr.core.License;
import cruise.umple.umpr.core.Repository;

import com.google.common.base.Strings;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Builds a repository chain. This provides a simpler fluent-builder API that delegates to the umple models: 
 * {@link ImportRepositorySet}, {@link ImportRepository}, and {@link ImportFile}. 
 * 
 * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
 * 
 * @since 11 Mar 2015
 */
public class ConsistentsBuilder {
  
  private final Logger log;
  private final ConsistentsFactory factory;
  
  private final ImportRepositorySet repositorySet;
  
  private static final Function<Path, String> PATH_TRANSFORM = (path) -> {
    if (path == null) {
      return null;
    } 
    
    return path.toAbsolutePath().normalize().toString();
  };
  

  
  
}
