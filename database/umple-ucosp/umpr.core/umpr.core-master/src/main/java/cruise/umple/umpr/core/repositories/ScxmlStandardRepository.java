/**
 * 
 */
package cruise.umple.umpr.core.repositories;

import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Stream;

import cruise.umple.compiler.UmpleImportType;
import cruise.umple.umpr.core.DiagramType;
import cruise.umple.umpr.core.DocumentFactory;
import cruise.umple.umpr.core.ImportAttrib;
import cruise.umple.umpr.core.License;
import cruise.umple.umpr.core.Repository;
import cruise.umple.umpr.core.entities.ImportEntity;
import cruise.umple.umpr.core.entities.ImportEntityFactory;
import cruise.umple.umpr.core.util.Networks;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;

/**
 * 
 * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
 *
 * @since Mar, 2015
 */
public class ScxmlStandardRepository extends SimpleRepository implements Repository {
 
  @Name
  private static final String REPO_NAME = "W3C-SCXML";
  
  @Remote
  private final static String REPO_URL = "http://www.w3.org/TR/scxml/";
  
  @Description
  private final static String REPO_DESC = "State Chart XML (SCXML): State Machine Notation for Control Abstraction";

  @DType
  private final static DiagramType REPO_DTYPE = DiagramType.STATE;
  
  @CLicense
  private final static License REPO_LICENSE = License.W3C;

  private final Logger logger;
  private final DocumentFactory documentFactory;
  private final ImportEntityFactory entityFactory;
  
  private static final Set<String> EXAMPLE_ANCHOR_NAMES = ImmutableSet.<String>builder()
      .add("N1182E") // microwave-01.scxml
      .add("N1183A") // microwave-02.scxml
      .add("N11845") // 
      .add("N11851") // traffic light
      .add("N11859") // black jack
      .build();
}
