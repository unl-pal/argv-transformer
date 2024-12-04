package cruise.umple.umpr.core;

import java.io.File;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

import cruise.umple.umpr.core.fixtures.MockModule;
import cruise.umple.umpr.core.repositories.TestRepository;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.common.io.Files;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Created by kevin on 15-02-23.
 */
@Guice(modules={MockModule.class})
public class ConsoleMainTest {

    @Inject
    private Provider<ConsoleMain> mainProvider;

    private ConsoleMain.Config cfg;
    private ConsoleMain main;


}
