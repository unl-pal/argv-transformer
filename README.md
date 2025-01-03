# PAClab Automated Program Transformations

## About
Welcome to PAClab Automated Program Transformations.
This program automatically downloads, filters and transforms open source reposistories into benchmarks for static analysis tools.

## Download
```git clone ```

## Setting Up && Running the Project
This project uses gradle

### Code Steps
The tool uses the file **config.properties** to set the options and properties of the tool before each run.
This file can be edited manually to suit the users needs and will be covered in greater detail later.
This tool uses a csv file of github repository urls as input to begin the program.
This list of urls can be obtained using the **BOA** tool, RepoReaper or other available programs.
<!--TODO --COORECT ME WHEN WRONG-->
At this point the program will then download the repos into the **database**.
From there the programs are filtered using the requirements set in the **config.properties** file and stored in **suiatablePrgms**.
Finally these **suitablePrgrms** are transformed by the tool and the new benchmarks stored in **benchmarks**

All newly created benchmarks are tested for compilability and can be set to follow SVCOMP standards.

## Config Properties
- csv - relative file path to file with OSS github URLs
- projectCount - how many projects to grab that meet the requirements
- maxLoc - upper limit to number of lines of code
- minLoc - lower limit to number of lines of code
- downloadDir - where to download the repos to
- benchmarkDir - where to write the benchmarks to
- debug - run code with debugging logs and features on
- debugLevel - what level of debug errors are reported
- type - type of expression
- minExpr - int minimum number of expressions required
- minIfStmt - int minimum number of if statements required
- minParams - int minimum number of parameters required
- minTypeExpr - int minimum number of type expressions required
- minTypeCond - int minimum number of type conditions required
- minTypeParams - int minimum number of type parameters required
- transformAll - boolean to transform all code regardless of if it already compiles
- target - string for compatibility i.e. SVCOMP 
- verifier - location of the verifier code needed to compile benchmarks using x compatibility

### Supported Properties
exprType:
 * X - don't care what type of expression is
 * I - integer type (int, short, byte, long)
 * R - real type (double, float)
 * S - string type (String, char)
 Currently code also ensures that the arguments to a method of that type too (plus boolean).

minExpr:
 * a minimum number of infix, prefix or postfix expression of the defined type encountered in a method

ifStmt:
 * X - don't care if there is an ifStmt in a method
 * Y - a conditional statement should be present in a method
 * N - a method should have no ifStmt
 
 ifStmtType -- what expression type should be present in a conditional statement:
 * X - don't care what type of expression is
 * I - integer type (int, short, byte, long)
 * R - real type (double, float)
 * S - string type (String, char)
 
 minIfStmt:
  * a minimum number of conditional statements defined above required in a method.

## Main Classes

**filter.Main.java** 
This program filters a directory of repositories for java files suitable for symbolic execution. 

(Suitability is defined in sourceAnalysis.AnalyzedMethod.java by isSymbolicSuitable() method. A java file 
is suitable if at least one of its methods is suitable.)

Input:
 * database - Directory of repositories. 
 
Output:
 * suitablePrgms - Directory of repositories containing only java files suitable for analysis (in original directory structure). 
 
**transform.Main.java** 
Given a directory of suitable java files, this program attempts to transform each into a compilable benchmark.
 
A directory of benchmarks is created, containing the programs that would successfully compile in their original directory structure. 

Input:
 * suitablePrgms - Directory of repositories containing only java files suitable for symbolic execution. 
 
Output:
 * benchmarks - Directory of compilable, suitable programs (in original directory structure). 

**full.Main.java**
Given a CSV of GitHub repositories (as gathered by RepoReaper), this program will select suitable repositories, download them, search for classes containing SPF-suitable methods, and transform suitable classes into compilable, benchmark programs.

Input:
* CSV of GitHub repositories as gathered by RepoReaper.

Output:
 * database - Directory of GitHub repos that meet project filter specification.
 * suitablePrgms - Directory containing suitable files extracted from GitHub repos. (This is where the transformation of the source code takes place.)
 * benchmarks - Directory of compilable, suitable programs, in their original directory structure. 

## PACKAGES

 * download - Downloading GitHub projects
 * filter - Filtering for relevant projects and files
 <!--* jpf - Running JPF-->
 * logging - For simple logging
 * full - Contains main for running with full framework, i.e. download, filter, transform, output
 * sourceAnalysis - Used to track files and methods suitable for symbolic execution
 * tests
 * transform - Transforming files into compilable benchmarks
 
## ADDITIONAL FILES

 * dataset.csv - First 5,000 entries (each entry is a GitHub project url with metadata) from RepoReaper dataset.
 * filtered-dataset.csv - 250 Java projects with min and max loc of 100 and 10,000
 * config.properties - Properties for running the full framework (i.e. downloading, filtering, transforming). 

## RUNNING WITH SPF

The paths for rt.jar and jfxrt.jar need to be added to Soot's classpath in jpf.ProgramUnderTest.java. (Soot is used for loop detection in class file). 

The path for jpf-symbc/build needs to be added to classpath in the compile() methods. (For transformations specific to SPF, i.e. using Debug.makeSymbolicInteger() in place of rand.nextInt().)

For SPF, the environment variable LD_LIBRARY_PATH needs to be set (in Eclipse, Run configurations -> Environment).
LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/home/MariaPaquin/pathfinder/jpf-symbc/lib/64bit:/home/MariaPaquin/pathfinder/jpf-symbc/lib

## TROUBLESHOOTING

The javac version (used in main.MainTransform.java and mainFullFramework.MainAnalysis.java to compile benchmarks) needs to be the same version as JDK for rt.jar set in Soot classpath (in jpf.ProgramUnderTest.java, used for loop detection). 

## Database
The '''database''' directory is populated by ARG-V with the set amount of potential repositories 
to be filtered. This process currently uses the dataset.csv file

## Suitable Programs Database
Using the repositories in database, ARG-V filters the repositories for suitable 
programs then places them in the '''suitablePrgms''' directory. 

Suitable programs are defined as programs containing all attributes described 
by the user at the start of the ARG-V process.

## Benchmarks
This directory is populated by the ARG-V Transformation code run on the 
'''suitablePrgms''' directory to create SV-COMP compliant benchmarks.

