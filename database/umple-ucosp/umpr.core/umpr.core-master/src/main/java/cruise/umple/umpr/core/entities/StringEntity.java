package cruise.umple.umpr.core.entities;

import static com.google.common.base.Preconditions.checkNotNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Logger;

import cruise.umple.compiler.UmpleImportType;
import cruise.umple.umpr.core.ImportAttrib;
import cruise.umple.umpr.core.Repository;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Convenience entity that stores a {@link Supplier} for {@link String} and accesses it quickly. 
 * 
 * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
 * @since Mar 2, 2015
 */
final class StringEntity implements ImportEntity {
  
  @SuppressWarnings("unused")
  private final Logger log;
  
  private final Supplier<String> content;
  private final Repository repository;
  private final Path path;
  private final UmpleImportType fileType;
  private final Optional<ImportAttrib> attrib;
}