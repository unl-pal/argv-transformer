# PAClab Automated Program Transformations

## Main Classes

**filter.Main.java** 
This program filters a directory of repositories for java programs suitable for symbolic execution. 

(Suitability is defined in sourceAnalysis.AnalyzedMethod.java by isSymbolicSuitable() method. A java program 
is suitable if at least one of its methods is suitable.)

Input:
 * database - Directory of repositories. 
 
Output:
 * suitablePrgms - Directory of repositories containing only java files suitable for analysis (in original directory structure). 
 
**transform.Main.java** 
Given a directory of suitable Java programs, this program attempts to transform each file into a compilable benchmark.
 
A directory of benchmarks is created, containing the programs that would successfully compile (before or after transformation) in their original directory structure. 

Input:
 * suitablePrgms - Directory of repositories, each of which contains only java classes suitable for analysis. 
 
Output:
 * benchmarks - Directory of compilable, suitable programs, in their original directory structure. 

**full.Main.java**
Given a CSV of GitHub repositories (as gathered by RepoReaper), this program will select suitable repositories, download them, search for classes containing SPF-suitable methods, and transform suitable classes into compilable, benchmark programs.

Input:
* CSV of GitHub repositories as gathered by RepoReaper.

Output:
 * database - Directory of GitHub repos that meet project filter specification.
 * suitablePrgms - Directory containing suitable programs extracted from GitHub repos. This is where the transformation of the source code takes place.
 * benchmarks - Directory of compilable, suitable programs, in their original directory structure. 

## PACKAGES

 * download - Downloading GitHub projects
 * filter - Filtering for relevant projects and files
 * jpf - Running JPF
 * logging - For simple logging
 * main - Transform a directory of suitable programs into compilable benchmarks
 * mainFull - Full framework: filter and download suitable GitHub projects, filter the projects for suitable files, transform those files and run SPF on the resulting benchmarks. 
 * sourceAnalysis - Used to look for files and methods suitable for symbolic execution
 * tests
 * transform - Transforming files into compilable benchmarks
 
## ADDITIONAL FILES

 * dataset.csv - First 5,000 entries (each entry is a GitHub project url with metadata) from RepoReaper dataset.
 * filtered-dataset.csv - 250 Java projects with min and max loc of 100 and 10,000
 * config.properties - Properties for running the full framework (i.e. downloading, filtering, transforming). 

## RUNNING WITH SPF

The paths for rt.jar and jfxrt.jar need to be added to Soot's classpath in jpf.ProgramUnderTest.java. (Soot is used for loop detection in class file). 

The path for jpf-symbc/build needs to be added to classpath in mainFullFramework.MainAnalysis.compile() method. (For transformations specific to SPF, i.e. using Debug.makeSymbolicInteger() in place of rand.nextInt().)

For SPF, the environment variable LD_LIBRARY_PATH needs to be set (in Eclipse, Run configurations -> Environment).
LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/home/MariaPaquin/pathfinder/jpf-symbc/lib/64bit:/home/MariaPaquin/pathfinder/jpf-symbc/lib

## TROUBLESHOOTING

The javac version (used in main.MainTransform.java and mainFullFramework.MainAnalysis.java to compile benchmarks) needs to be the same version as JDK for rt.jar set in Soot classpath (in jpf.ProgramUnderTest.java, used for loop detection). 


 
