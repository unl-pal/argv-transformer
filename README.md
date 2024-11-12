# PAClab Automated Program Transformations

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
 * jpf - Running JPF
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

## DEV BRANCH ADD ONS
config file has extended configurations

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
