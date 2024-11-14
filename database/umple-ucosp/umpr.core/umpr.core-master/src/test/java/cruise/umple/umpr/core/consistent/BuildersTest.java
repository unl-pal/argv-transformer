package cruise.umple.umpr.core.consistent;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Time;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import cruise.umple.compiler.UmpleImportType;
import cruise.umple.umpr.core.ImportFSM;
import cruise.umple.umpr.core.Repository;
import cruise.umple.umpr.core.fixtures.MockModule;
import cruise.umple.umpr.core.repositories.TestRepository;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.common.base.Strings;
import com.google.common.io.Files;
import com.google.inject.Inject;

@Guice(modules=MockModule.class)
public class BuildersTest {
  
  private ConsistentsBuilder bld;
  private final ConsistentsFactory factory;
  private final Set<Repository> repos;
  
  private long time;
}
