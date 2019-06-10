# PAClab Automated Program Transformations

## OVERVIEW

**main.MainTransform.java** 
Given a directory of Java projects, this program attempts to transform each .java file in the directory into a compilable benchmark.
 
A directory of benchmarks is created, containing the programs that would successfully compile (before or after transformation) in their original directory structure. 

**mainFullFramework.MainAnalysis.java**
Given a CSV of GitHub repositories (as gathered by RepoReaper), this program will select suitable repositories, download them, search or classes containing SPF-suitable methods, and transform suitable classes into compilable, benchmark programs. The resulting benchmarks are run with Java PathFinder. 
 

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