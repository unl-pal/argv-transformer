/**
 * 
 */
package cruise.umple.umpr.core.repositories;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Stream;

import cruise.umple.compiler.UmpleImportType;
import cruise.umple.umpr.core.DiagramType;
import cruise.umple.umpr.core.ImportAttrib;
import cruise.umple.umpr.core.ImportFSM;
import cruise.umple.umpr.core.ImportFSM.State;
import cruise.umple.umpr.core.License;
import cruise.umple.umpr.core.entities.ImportEntity;
import cruise.umple.umpr.core.entities.ImportEntityFactory;

import com.google.common.base.Charsets;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import com.google.inject.Inject;
import com.google.inject.Singleton;


/**
 * Test repository that will respond immediately without lag. 
 * 
 * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
 *
 * @since 11 Mar 2015
 */
@Singleton
public class TestRepository extends SimpleRepository {
  
  @Name
  public static final String TEST_NAME = "TestRespository-ECore";
  
  @Description
  public static final String DESCRIPTION = "Fast and small ECore repository.";
  
  @DType
  public final static DiagramType REPO_DTYPE = DiagramType.CLASS;
  
  @Remote
  public final static String REPO_REMOTE = "http://www.example.com/";
  
  @CLicense
  public final static License REPO_LICENSE = License.MIT;
  
  public final List<ImportEntity> entities;
  
  public final ImportEntityFactory factory;
  
  public static final List<String> ECORE_FILES = ImmutableList.<String>builder()
      .add("bibtex.ecore")
      .add("ocl-operations.ecore")
      .add("sharengo.import-failure.ecore")
      .add("intentional-failure.ecore") // fail on fetch
      .add("adelfe.model-failure.ecore")
      .build();
  
  public static final Map<String, ImportFSM.State> failStates = ImmutableMap.<String, ImportFSM.State>builder()
      .put("bibtex.ecore", State.Completed)
      .put("ocl-operations.ecore", State.Completed)
      .put("sharengo.import-failure.ecore", State.Import)
      .put("intentional-failure.ecore", State.Fetch)
      .put("adelfe.model-failure.ecore", State.Model)
      .build();
  
  public static final double SUCCESS_RATE;
  
  public static final Set<String> ECORE_FILES_SET = ImmutableSet.copyOf(ECORE_FILES);
  public static final Map<String, Supplier<String> > ECORE_MAP;
  public static final Map<String, String> ECORE_CONTENT;
  
  static {
    
    Map<String, Supplier<String>> content = Maps.asMap(ECORE_FILES_SET, path -> 
      () -> {
          try {
            URL url = Resources.getResource("repositories/" + path);
            
            return Resources.toString(url, Charsets.UTF_8);
          } catch (IllegalArgumentException | IOException e) {
            throw new IllegalStateException("RESOURCE_FAILURE -- \n"
                + "If multiple failures occur it is likely due to resources not on the classpath.", e);
          }
        });
    
    ECORE_MAP = ImmutableMap.copyOf(content);
    
    ECORE_CONTENT = Maps.transformEntries(ECORE_MAP, (key, val) -> {
      try {
        return val.get();
      } catch (IllegalStateException ise) {
        return ise.getMessage();
      }
    });
    
    long successes = ECORE_CONTENT.values().stream().filter(s -> { return !s.startsWith("RESOURCE_FAILURE"); }).count();
    
    SUCCESS_RATE = (1.0 * successes) / ECORE_CONTENT.values().size();
  }

}
