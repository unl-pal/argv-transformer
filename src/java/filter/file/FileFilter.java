package filter.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import download.GitProject;
import sourceAnalysis.AnalyzedFile;
import sourceAnalysis.AnalyzedMethod;
import transform.TypeChecking.TypeChecker.CType;
import logging.Logger;

/**
 * Class to find java files suitable for symbolic execution.
 *
 * @author mariapaquin
 */
public class FileFilter {

  private static final Logger logger = Logger.defaultLogger;
  private HashMap<File, String> fileInfo = new HashMap<>();
  private ArrayList<String> summaryInfo = new ArrayList<>();

  private ArrayList<File> suitableFiles;
  private ArrayList<File> javaFiles;
  private ArrayList<AnalyzedFile> analyzedFiles;
  private File database;
  private int suitableMethods;
  private List<GitProject> gitProjects;

  private CType type;
  private int minExpr;
  private int minIfStmt;
  private int minParams;

  /**
   * Create a new FileFilter
   * 
   * @param gitProjects A list of GitHub projects
   */
  public FileFilter(List<GitProject> gitProjects) {
    this.gitProjects = gitProjects;
    suitableFiles = new ArrayList<File>();
    suitableMethods = 0;
  }

  /**
   * Create a new FileFilter
   * 
   * @param database A directory of GitHub projects
   */
  public FileFilter(File database) {
    this.database = database;
    suitableFiles = new ArrayList<File>();
    javaFiles = new ArrayList<File>();
    suitableMethods = 0;
  }

  public FileFilter(File database, String type, int minExpr, int minIfStmt, int minParams) {
    this.database = database;
    suitableFiles = new ArrayList<File>();
    javaFiles = new ArrayList<File>();
    analyzedFiles = new ArrayList<AnalyzedFile>();
    suitableMethods = 0;
    setUp(type, minExpr, minIfStmt, minParams);
  }

  private void setUp(String type, int minExpr, int minIfStmt, int minParams) {
    switch (type) {
      case "I":
        this.type = CType.INT;
        break;
      case "R":
        this.type = CType.REAL;
        break;
      case "B":
        this.type = CType.BOOLEAN;
        break;
      case "S":
        this.type = CType.STRING;
        break;
      default:
        this.type = CType.ANY;
        break;
    }
    this.minExpr = minExpr;
    this.minIfStmt = minIfStmt;
    this.minParams = minParams;
  }

  public FileFilter(List<GitProject> gitProjects, String type, int minExpr, int minIfStmt, int minParams) {
    this.gitProjects = gitProjects;
    suitableFiles = new ArrayList<File>();
    suitableMethods = 0;

    setUp(type, minExpr, minIfStmt, minParams);

  }

  /**
   * Getter for suitable file count
   * 
   * @return the number of SPF suitable files
   */
  public ArrayList<File> getSuitableFiles() {
    return suitableFiles;
  }

  public ArrayList<File> getJavaFiles() {
    return javaFiles;
  }

  /**
   * Getter for suitable method count
   * 
   * @return the number of SPF suitable methods
   */
  public int getSuitableMethodCount() {
    return suitableMethods;
  }

  /**
   * Collect all the java files in the project and add them
   * to the list javaFiles.
   */
  public void collectJavaFiles() {
    try {
      Files.find(Paths.get(database.getAbsolutePath()), 999,
          (p, bfa) -> p.toString().endsWith(".java"))
          .forEach(p -> javaFiles.add(p.toFile()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Use the SuitableMethodFinder to search the javaFiles list for files
   * suitable for SPF. Add suitable files to the list suitableFiles.
   */
  public void collectSuitableFiles() {
    for (File file : javaFiles) {
      try {
        // SymbolicSuitableMethodFinder finder = new SymbolicSuitableMethodFinder(file);
        SuitableMethodFinder finder = new SuitableMethodFinder(file, type, minExpr, minIfStmt, minParams);
        // SimplifiedSuitableClassFinder finder = new
        // SimplifiedSuitableClassFinder(file, type, minExpr, minIfStmt, minParams);
        finder.analyze();
        AnalyzedFile af = finder.getAnalyzedFile();
        if (af.isSuitable()) {
          suitableMethods += af.getSuitableMethodCount();
          suitableFiles.add(file);
          analyzedFiles.add(af);
          // fileInfo.put(file, af.getFileInfo());
          summaryInfo.add(af.getSummary());
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * return the respective AnalyzedFile for a given File
   */
  public AnalyzedFile getAnalyzedFile(File f) {
    AnalyzedFile ret = null;
    for (AnalyzedFile af : analyzedFiles) {
      if (af.getFile().equals(f)) {
        ret = af;
        break;
      }
    }
    return ret;
  }

  /**
   * Use the SymbolicSuitableMethodFinder to search the gitProjects list for files
   * suitable for SPF. Add suitable files to the list suitableFiles.
   */
  public void collectSuitableFilesInProjectList() {
    for (GitProject project : gitProjects) {
      // ArrayList<File> files = project.getFiles();
      javaFiles = project.getFiles();
      collectSuitableFiles();
      // for (File file : files) {
      // try {
      // //SymbolicSuitableMethodFinder finder = new
      // SymbolicSuitableMethodFinder(file);
      // SuitableMethodFinder finder = new SuitableMethodFinder(file, type, minExpr,
      // minIfStmt);
      // finder.analyze();
      // AnalyzedFile af = finder.getAnalyzedFile();
      // try {
      // suitableMethods += af.getSpfSuitableMethodCount();
      // if (af.isSymbolicSuitable()) {
      // suitableFiles.add(file);
      // }
      // } catch (Exception e) {
      // continue;
      // }
      // } catch (IOException e) {
      // e.printStackTrace();
      // }
      // }
    }
  }

  // public String getFileInfo(File file) {
  //   return fileInfo.get(file);
  // }

  // returns csv format with leading repo name
  public String getSummaryInfo(String repo) {
    StringBuilder info = new StringBuilder();
    for (String fileInfo : summaryInfo) {
      info.append(repo).append(",").append(fileInfo).append("\n");
    }
    return info.toString();
  }

}
