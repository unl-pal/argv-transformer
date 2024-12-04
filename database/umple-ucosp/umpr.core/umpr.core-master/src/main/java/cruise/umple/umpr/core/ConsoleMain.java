package cruise.umple.umpr.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cruise.umple.umpr.core.consistent.Consistents;
import cruise.umple.umpr.core.consistent.ImportRepositorySet;
import cruise.umple.umpr.core.entities.ImportEntity;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class ConsoleMain {

  @Parameters
  static class Config {

    @Parameter(names={"--import", "-i"}, description = "Folder to save import files to")
    File importFileFolder;

    @Parameter(names={"-o", "--output"}, description = "Output folder for generated .ump files", required = true)
    File outputFolder;

    @Parameter(description = "[Repository1] [.. [RepositoryN]]")
    List<String> respositories = Collections.emptyList();

    @Parameter(names={"-l", "--limit"}, description = "Number of imports to download in total, " +
            "there are no guarantees to which repositories are used or what order. (-1 implies no limit)")
    int limit = -1;
    
    @Parameter(names={"-O", "--override"}, description="Force overriding of the output folders, "
        + "i.e. remove output folder contents.")
    boolean override = false;
    
    @Parameter(names = {"-h", "-?", "--help"}, help = true, description="Print help message.")
    boolean help;


  }

  private final Logger logger;
  private final Set<Repository> repositories;

  public static final Path IGNORE_FILE = Paths.get(".umpr.core.ignore");
}
