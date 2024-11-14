/**
 * 
 */
package cruise.umple.umpr.core.repositories;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;

import cruise.umple.compiler.UmpleImportType;
import cruise.umple.umpr.core.DiagramType;
import cruise.umple.umpr.core.ImportAttrib;
import cruise.umple.umpr.core.License;
import cruise.umple.umpr.core.Repository;
import cruise.umple.umpr.core.entities.ImportEntity;
import cruise.umple.umpr.core.entities.ImportEntityFactory;
import cruise.umple.umpr.core.util.Networks;

import com.google.inject.Inject;

/**
 * Simple, one-file repository used in the Umpr repository tutorial, the model is still useful so the code is left here.
 * 
 * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
 * @since 31 Mar 2015
 * 
 * @see https://code.google.com/p/umple/wiki/UmprCoreRepositories
 * 
 */
class Iso20022EcoreRepository extends SimpleRepository implements Repository {
  
  private static final String GIST_URL = "https://gist.githubusercontent.com/Nava2/4ca3335224d51c185c0b/" + 
                                         "raw/87067f5d6889d9efa6032de25331fde6d1d0f88c/iso20022.ecore";
  
  @Name
  private static final String REPO_NAME = "ISO20022";
  
  @Remote
  private final static String REPO_URL = "http://www.iso20022.org/e_dictionary.page";
  
  @Description
  private final static String REPO_DESC = "ISO20022 Ecore implementation provided by the ISO20022 group";

  @DType
  private final static DiagramType REPO_DTYPE = DiagramType.CLASS;
  
  @CLicense
  private final static License REPO_LICENSE = License.UNKNOWN;
  
  //Creates ImportEntity instances
  private final ImportEntityFactory factory;

}
