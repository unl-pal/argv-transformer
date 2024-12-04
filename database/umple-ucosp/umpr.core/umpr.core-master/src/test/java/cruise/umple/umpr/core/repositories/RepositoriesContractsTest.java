package cruise.umple.umpr.core.repositories;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

import java.net.URL;
import java.util.Set;
import java.util.logging.Logger;

import cruise.umple.umpr.core.DownloaderModule;
import cruise.umple.umpr.core.Repository;
import cruise.umple.umpr.core.util.Pair;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * This Test will test all loaded repositories to make sure that when inheriting from the {@link Repository} interface, 
 * they match the contracts outlined. This test may be slow due to it fetching the result of 
 * {@link Repository#getImports()} for every loaded {@link Repository}. 
 * 
 * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
 * 
 * @since 24 Feb 2015
 *
 */
// Intentionally using the NON-mock version, this tests the implemented repositories that they match their contracts
@Guice(modules={DownloaderModule.class})
@Test(groups={"long-runtime"})
public class RepositoriesContractsTest {
    
    @Inject
    private Set<Repository> allRepositories;
    
    @Inject
    private Logger logger;
  
}
