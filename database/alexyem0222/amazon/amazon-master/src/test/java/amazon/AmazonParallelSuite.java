package amazon;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import amazon.AmazonHomePage;
import amazon.AmazonSearchResultsPage;
import amazon.AmazonSearchTestParallel;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        AmazonSearchTestParallel.class
})

public class AmazonParallelSuite {
}