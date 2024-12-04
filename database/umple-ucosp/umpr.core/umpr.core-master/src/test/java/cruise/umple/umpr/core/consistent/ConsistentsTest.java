package cruise.umple.umpr.core.consistent;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;

import cruise.umple.umpr.core.ImportFSM;
import cruise.umple.umpr.core.Repository;
import cruise.umple.umpr.core.fixtures.MockModule;
import cruise.umple.umpr.core.repositories.TestRepository;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.codepoetics.protonpack.StreamUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.inject.Inject;
import com.jayway.jsonassert.JsonAssert;
import com.jayway.jsonassert.JsonAsserter;

@Guice(modules={MockModule.class})
@Test
public class ConsistentsTest {
  
  private final ConsistentsFactory factory;
  
  private ConsistentsBuilder bld;
  private final Set<Repository> repos;
  
  private Path TEST_UMP_DIR;
  
}
