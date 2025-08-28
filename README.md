# ARG-V Automated Program Transformations

## About
Welcome to ARG-V Automated Program Transformations.
This application automatically downloads, filters and transforms open source repositories into benchmarks for static analysis tools.

## Setting Up the Project
### Download
Clone the repository:
```bash
git clone git@github.com:unl-pal/argv-transformer.git
```

### Setting Up the Project
This project uses [gradle](https://gradle.org) to build, compile and run the program.
After cloning the repository run the gradle wrapper with no arguments `./gradlew` to set up the project.
This will fetch the appropriate version of Gradle, if needed, as well as all dependencies, and set to build Java 8 compatible code.

## Running the Project
Run `./gradlew` followed by any of the following tasks to get desired effect.

### Clean Tasks
- `reset` - Resets the program - deletes `build`, `database`, `suitablePrgms` and `benchmarks` directories

### Documentation Tasks
- `javadoc` - Generates Javadocs in the `build/docs/javadoc` directory with
  `index.html` as the entry point. These docs can be viewed and navigated from
  the browser.

### Execution Tasks
- `download` - Compiles and Runs the download program
- `filter` - Compiles and Runs the filter program
- `full` - Compiles Runs the full application
- `transform` - Compiles and Runs the transformer program

### Testing Tasks
- `regressionTransformer` - Runs regression test for transformer
- `test` - Runs the test suite and creates a report that can be viewed in a
  browser. The report is located in the `build/reports/tests/test` directory
  with the `index.html` file as the main entry point.
  
  Note: Be sure to exclude integrationExpected from the classpath.

Other tasks are available and can be seen by running `./gradlew tasks` from
the command line. This will display all tasks available and a brief description
of what they do.
<!--## Using the Tool-->
<!--The tool uses the file **config.properties** to set the options and properties of the tool before each run.-->
<!--This file can be edited manually to suit the users needs and will be covered in greater detail later.-->
<!--This tool uses a csv file of github repository urls as input to begin the program.-->
<!--This list of urls can be obtained using the **BOA** tool, RepoReaper or other available programs.-->
<!--<!--TODO --COORECT ME WHEN WRONG-->
<!--At this point the program will then download the repos into the **database**.-->
<!--From there the programs are filtered using the requirements set in the **config.properties** file and stored in **suiatablePrgms**.-->
<!--Finally these **suitablePrgrms** are transformed by the tool and the new benchmarks stored in **benchmarks**-->

<!--All newly created benchmarks are tested for compilability and can be set to follow SVCOMP standards.-->

## Config Properties
The tool uses the file **config.properties** to set the options and properties of the tool before each run.
This file can be edited manually to suit the users needs by changing the values of the file.

The available options to set are as follows:
- `csv` - relative file path to file with OSS GitHub URLs
- `projectCount` - how many projects to grab that meet the requirements
- `maxLoc` - upper limit to number of lines of code
- `minLoc` - lower limit to number of lines of code
- `downloadDir` - where to download the repos to
- `benchmarkDir` - where to write the benchmarks to
- `debug` - run code with debugging logs and features on
- `debugLevel` - what level of debug errors are reported
- `type` - type of expression
- `minExpr` - a minimum number of infix, prefix or postfix expression of the defined type encountered in a method
- `minIfStmt` - int minimum number of if statements required
- `minParams` - int minimum number of parameters required
- `minTypeExpr` - int minimum number of type expressions required
- `minTypeCond` - int minimum number of type conditions required
- `minTypeParams` - int minimum number of type parameters required
- `transformAll` - boolean to transform all code regardless of if it already compiles
- `target` - string for compatibility i.e. SVCOMP 
- `verifier` - location of the verifier code needed to compile benchmarks using x compatibility

### Supported Properties
expression Types Available for value:type:
 * `X` - don't care what type of expression is
 * `I` - integer type (int, short, byte, long)
 * `R` - real type (double, float)
 * `S` - string type (String, char)
 Currently code also ensures that the arguments to a method of that type too (plus boolean).

### In Development
ifStmt:
 * `X` - don't care if there is an ifStmt in a method
 * `Y` - a conditional statement should be present in a method
 * `N` - a method should have no ifStmt
 
 ifStmtType -- what expression type should be present in a conditional statement:
 * `X` - don't care what type of expression is
 * `I` - integer type (int, short, byte, long)
 * `R` - real type (double, float)
 * `S` - string type (String, char)
 
## Tools
### download
Given a csv file with names and meta data of repositories this program
downloads repositories and stores them in a database.

Input:
* `csv` - csv file with meta data and names of repositories such as created by RepoReaper

Config Settings:
- `csv` - relative file path to file with OSS github URLs
- `projectCount` - how many projects to grab that meet the requirements
- `maxLoc` - upper limit to number of lines of code
- `minLoc` - lower limit to number of lines of code
- `downloadDir` - where to download the repos to

Output:
* creates database directory of downloaded repositories for next steps

### filter
This program filters a directory of repositories for java files suitable for symbolic execution using the values set by the user.

(Suitability is defined in `sourceAnalysis.AnalyzedMethod.java` by the `isSymbolicSuitable()` method. A Java file 
is suitable if at least one of its methods is suitable.)

Input:
 * `database` - Directory of repositories. 

Config Settings:
- `maxLoc` - upper limit to number of lines of code
- `minLoc` - lower limit to number of lines of code
- `downloadDir` - where to download the repos to
- `benchmarkDir` - where to write the benchmarks to
- `type` - type of expression
- `minExpr` - int minimum number of expressions required
- `minIfStmt` - int minimum number of if statements required
- `minParams` - int minimum number of parameters required
- `minTypeExpr` - int minimum number of type expressions required
- `minTypeCond` - int minimum number of type conditions required
- `minTypeParams` - int minimum number of type parameters required

Output:
 * `suitablePrgms` - Directory of repositories containing only java files suitable for analysis (in original directory structure). 
 
### transform
Given a directory of suitable Java files, this program attempts to transform each into a compilable benchmark.
 
A directory of benchmarks is created, containing the programs that would successfully compile in their original directory structure. 

Input:
 * `suitablePrgms` - Directory of repositories containing only Java files suitable for symbolic execution. 

Config Settings:
- `debug` - run code with debugging logs and features on
- `debugLevel` - what level of debug errors are reported
- `transformAll` - boolean to transform all code regardless of if it already compiles
- `target` - string for compatibility i.e. SVCOMP 
- `verifier` - location of the verifier code needed to compile benchmarks using x compatibility

- `csv` - relative file path to file with OSS GitHub URLs
 
Output:
 * `benchmarks` - Directory of compilable, suitable programs (in original directory structure). 

### full
Given a CSV of GitHub repositories (as gathered by RepoReaper), this program will select suitable repositories, download them, search for classes containing SPF-suitable methods, and transform suitable classes into compilable, benchmark programs.

Input:
 * CSV of GitHub repositories as gathered by RepoReaper.

Config Settings:
 * All settings

Output:
 * `database` - Directory of GitHub repos that meet project filter specification.
 * `suitablePrgms` - Directory containing suitable files extracted from GitHub repos. (This is where the transformation of the source code takes place.)
 * `benchmarks` - Directory of compilable, suitable programs, in their original directory structure. 

## ADDITIONAL FILES

 * `dataset.csv` - First 5,000 entries (each entry is a GitHub project url with metadata) from RepoReaper dataset.
 * `filtered-dataset.csv` - 250 Java projects with min and max loc of 100 and 10,000
 * `config.properties` - Properties for running the full framework (i.e. downloading, filtering, transforming). 

## TROUBLESHOOTING

The `javac` version (used in `main.MainTransform.java` and `mainFullFramework.MainAnalysis.java` to compile benchmarks) needs to be the same version as JDK for `rt.jar` set in Soot classpath (in `jpf.ProgramUnderTest.java`, used for loop detection). 

## Development
### Contributing to the Project

## PACKAGES

 * `download` - Downloading GitHub projects
 * `filter` - Filtering for relevant projects and files
 * `logging` - For simple logging
 * `full` - Contains main for running with full framework, i.e. download, filter, transform, output
 * `sourceAnalysis` - Used to track files and methods suitable for symbolic execution
 * `tests`
 * `transform` - Transforming files into compilable benchmarks
