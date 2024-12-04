/**
 * 
 */
package cruise.umple.umpr.core.repositories;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.net.URL;
import java.util.NoSuchElementException;
import java.util.logging.Logger;
import java.util.stream.Stream;

import cruise.umple.umpr.core.DiagramType;
import cruise.umple.umpr.core.License;
import cruise.umple.umpr.core.Repository;
import cruise.umple.umpr.core.consistent.ConsistentsModule;
import cruise.umple.umpr.core.entities.EntityModule;
import cruise.umple.umpr.core.entities.ImportEntity;
import cruise.umple.umpr.core.repositories.SimpleRepositoryTest.SRModule;
import cruise.umple.umpr.core.util.Networks;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.common.base.Throwables;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;

/**
 * 
 *
 * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
 *
 * @since Apr 10, 2015
 */
@Guice(modules={SRModule.class})
public class SimpleRepositoryTest {
  
  public static class SRModule extends AbstractModule {
    
  }
  
  @SuppressWarnings("unused")
  private final Logger log;
  private final Injector injector;
  
  /**
   * Class used as a fixture to remove dead method definitions.
   * 
   * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
   *
   * @since Apr 10, 2015
   */
  private static class Fixture extends SimpleRepository implements Repository {

    public static final String REPO_NAME = "TestTest";
      
    public final static String REPO_URL_STRING = "http://www.example.com/";
    public final static URL REPO_URL_URL = Networks.newURL(REPO_URL_STRING);
    
    public final static String REPO_DESC = "It's a tarp!";
  
    public final static DiagramType REPO_DTYPE = DiagramType.CLASS;
    
    public final static License REPO_LICENSE = License.MIT;
    
  }
  
  static class Proper extends Fixture implements Repository {

    @Name
    private static final String REPO_NAME = Fixture.REPO_NAME;
    
    @Remote
    private final static String REPO_URL = Fixture.REPO_URL_STRING;
    
    @Description
    private final static String REPO_DESC = Fixture.REPO_DESC;
  
    @DType
    private final static DiagramType REPO_DTYPE = Fixture.REPO_DTYPE;
    
  }
  
  static class NoName extends Fixture implements Repository {

//    @Name
//    private static final String REPO_NAME = Fixture.REPO_NAME;
    
    @Remote
    private final static String REPO_URL = Fixture.REPO_URL_STRING;
    
    @Description
    private final static String REPO_DESC = Fixture.REPO_DESC;

    @DType
    private final static DiagramType REPO_DTYPE = Fixture.REPO_DTYPE;
    
    @CLicense
    private final static License REPO_LICENSE = Fixture.REPO_LICENSE;
    
  }
  
  static class NoUrl extends Fixture implements Repository {

    @Name
    private static final String REPO_NAME = Fixture.REPO_NAME;
    
//    @Remote
//    private final static String REPO_URL = Fixture.REPO_URL;
    
    @Description
    private final static String REPO_DESC = Fixture.REPO_DESC;
  
    @DType
    private final static DiagramType REPO_DTYPE = Fixture.REPO_DTYPE;
    
    @CLicense
    private final static License REPO_LICENSE = Fixture.REPO_LICENSE;
    
  }
  
  static class NoDesc extends Fixture implements Repository {

    @Name
    private static final String REPO_NAME = Fixture.REPO_NAME;
    
    @Remote
    private final static String REPO_URL = Fixture.REPO_URL_STRING;
    
//    @Description
//    private final static String REPO_DESC = Fixture.REPO_DESC;
  
    @DType
    private final static DiagramType REPO_DTYPE = Fixture.REPO_DTYPE;
    
    @CLicense
    private final static License REPO_LICENSE = Fixture.REPO_LICENSE;
    
  }
  
  static class NoDT extends Fixture implements Repository {

    @Name
    private static final String REPO_NAME = Fixture.REPO_NAME;
    
    @Remote
    private final static String REPO_URL = Fixture.REPO_URL_STRING;
    
    @Description
    private final static String REPO_DESC = Fixture.REPO_DESC;
  
//    @DType
//    private final static DiagramType REPO_DTYPE = Fixture.REPO_DTYPE;
    
    @CLicense
    private final static License REPO_LICENSE = Fixture.REPO_LICENSE;
    
  }
  
  static class NoLicense extends Fixture implements Repository {

    @Name
    private static final String REPO_NAME = Fixture.REPO_NAME;
    
    @Remote
    private final static String REPO_URL = Fixture.REPO_URL_STRING;
    
    @Description
    private final static String REPO_DESC = Fixture.REPO_DESC;
  
    @DType
    private final static DiagramType REPO_DTYPE = Fixture.REPO_DTYPE;
    
  }
  
  static class TwoNames extends Fixture implements Repository {

    @Name
    private static final String REPO_NAME = Fixture.REPO_NAME;
    
    @Name
    private static final String REPO_NAME2 = "TestTest2";
    
    @Remote
    private final static String REPO_URL = Fixture.REPO_URL_STRING;
    
    @Description
    private final static String REPO_DESC = Fixture.REPO_DESC;
  
    @DType
    private final static DiagramType REPO_DTYPE = Fixture.REPO_DTYPE;
    
    @CLicense
    private final static License REPO_LICENSE = Fixture.REPO_LICENSE;
    
  }
  
  static class TwoNamesOneFieldOneMethod extends Fixture implements Repository {

    @Name
    private static final String REPO_NAME = Fixture.REPO_NAME;
    
    @Remote
    private final static String REPO_URL = Fixture.REPO_URL_STRING;
    
    @Description
    private final static String REPO_DESC = Fixture.REPO_DESC;
  
    @DType
    private final static DiagramType REPO_DTYPE = Fixture.REPO_DTYPE;
    
    @CLicense
    private final static License REPO_LICENSE = Fixture.REPO_LICENSE;
    
  }
  
  static class TwoNamesTwoMethods extends Fixture implements Repository {
    
    @Remote
    private final static String REPO_URL = Fixture.REPO_URL_STRING;
    
    @Description
    private final static String REPO_DESC = Fixture.REPO_DESC;
  
    @DType
    private final static DiagramType REPO_DTYPE = Fixture.REPO_DTYPE;
    
    @CLicense
    private final static License REPO_LICENSE = Fixture.REPO_LICENSE;
    
  }
  
  static class UrlViaUrl extends Fixture implements Repository {

    @Name
    private static final String REPO_NAME = Fixture.REPO_NAME;
    
    @Remote
    private final static URL REPO_URL = Fixture.REPO_URL_URL;
    
    @Description
    private final static String REPO_DESC = Fixture.REPO_DESC;
  
    @DType
    private final static DiagramType REPO_DTYPE = Fixture.REPO_DTYPE;
    
    @CLicense
    private final static License REPO_LICENSE = Fixture.REPO_LICENSE;
    
  }
  
}
