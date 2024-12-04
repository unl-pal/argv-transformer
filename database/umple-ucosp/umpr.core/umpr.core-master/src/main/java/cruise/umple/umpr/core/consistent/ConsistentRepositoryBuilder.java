package cruise.umple.umpr.core.consistent;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URL;
import java.util.Optional;
import java.util.logging.Logger;

import cruise.umple.compiler.UmpleImportType;
import cruise.umple.umpr.core.DiagramType;
import cruise.umple.umpr.core.ImportAttrib;
import cruise.umple.umpr.core.ImportFSM;
import cruise.umple.umpr.core.License;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Builds an {@link ImportRepository} instance simply by removing some guess work. 
 * 
 * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
 * @since 11 Mar 2015
 */
public class ConsistentRepositoryBuilder {
  
  private final ImportRepository importRepos;
  
  private final Logger log;
  
  private final ConsistentsBuilder parent;
}