/**
 * 
 */
package cruise.umple.umpr.core.entities;

import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import cruise.umple.compiler.UmpleImportType;
import cruise.umple.umpr.core.ImportAttrib;
import cruise.umple.umpr.core.Repository;
import cruise.umple.umpr.core.util.Networks;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Simple wrapper around {@link StringEntity} that allows for downloading a URL to a string. 
 * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
 * @since Mar 2, 2015
 */
final class URLEntity implements ImportEntity {
  
  @SuppressWarnings("unused")
  private final Logger log;
  
  private final ImportEntity wrappedEntity;

}
