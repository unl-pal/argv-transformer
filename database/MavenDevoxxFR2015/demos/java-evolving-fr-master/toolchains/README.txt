// build avec JDK 9
$ j9
$ mvn -V clean package exec:exec javadoc:javadoc
[INFO] -------------------------------------------------------------
[ERROR] COMPILATION ERROR : 
[INFO] -------------------------------------------------------------
[ERROR] Source option 1.5 is no longer supported. Use 1.6 or later.
[ERROR] Target option 1.5 is no longer supported. Use 1.6 or later.
[INFO] 2 errors 
[INFO] -------------------------------------------------------------
// failure: JDK 9 refuse -target 1.5, trop ancien


// build et exécution en JDK/JRE 7
$ j7
$ mvn -V clean package exec:exec javadoc:javadoc
[INFO] --- exec-maven-plugin:1.4.0:exec (default-cli) @ toolchains ---
toolchains.Main running on JVM version 1.7.0_71


// profil toolchain => compilation JDK 6, Maven reste JRE 7
$ mvn -V clean package exec:exec javadoc:javadoc -Ptoolchain
[INFO] --- maven-toolchains-plugin:1.1:toolchain (default) @ toolchains ---
[INFO] Required toolchain: jdk [ version='1.6' ]
[INFO] Found matching toolchain for type jdk: JDK[/home/opt/jdk1.6]
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ toolchains ---
[INFO] Toolchain in compiler-plugin: JDK[/home/opt/jdk1.6]
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /home/herve/projets/maven/misc/demos/toolchains/target/classes
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ toolchains ---
[INFO] Toolchain in compiler-plugin: JDK[/home/opt/jdk1.6]
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /home/herve/projets/maven/misc/demos/toolchains/target/test-classes
[INFO] 
[INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ toolchains ---
[INFO] Toolchain in surefire-plugin: JDK[/home/opt/jdk1.6]
[INFO] Surefire report directory: /home/herve/projets/maven/misc/demos/toolchains/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running toolchains.MainTest
toolchains.Main running on JVM version = 1.6.0_32
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.034 sec

Results :

Tests run: 1, Failures: 0, Errors: 0, Skipped: 0

[INFO] 
[INFO] --- exec-maven-plugin:1.4.0:exec (default-cli) @ toolchains ---
[INFO] Toolchain in exec-maven-plugin: JDK[/home/opt/jdk1.6]
toolchains.Main running on JVM version = 1.6.0_32
[INFO] 
[INFO] --- maven-javadoc-plugin:2.10.1:javadoc (default-cli) @ toolchains ---
[INFO] Toolchain in javadoc-plugin: JDK[/home/opt/jdk1.6]
[INFO] Fixed Javadoc frame injection vulnerability (CVE-2013-1571) in 1 files.


// test cas d'erreur: version de JDK qui n'est pas configurée dans les toolchains
$ mvn -V clean package exec:exec javadoc:javadoc -Ptoolchain-jdk5
[INFO] --- maven-toolchains-plugin:1.1:toolchain (default) @ toolchains ---
[INFO] Required toolchain: jdk [ version='5' ]
[ERROR] No toolchain matched from 4 found for type jdk
[ERROR] Cannot find matching toolchain definitions for the following toolchain types:
jdk [ version='5' ]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------


// démo bonus cas bizarre: la compilation avec un JDK 8 crée une dépendance à l'API JDK 8
$ j8
$ mvn -V clean package exec:exec javadoc:javadoc
$ java -cp target/classes toolchains.Main
toolchains.Main running on JVM version 1.7.0_71
Exception in thread "main" java.lang.NoSuchMethodError: java.util.concurrent.ConcurrentHashMap.keySet()Ljava/util/concurrent/ConcurrentHashMap$KeySetView;
        at toolchains.Main.callMap(Main.java:20)
        at toolchains.Main.main(Main.java:12)
// le problème est bien détecté par Animal Sniffer, mais cela ne le résoud pas: seul toolchain le peut


// autre bonus: on peut exécuter jdeps du JDK 8/9 alors que Maven utilise JDK 7 (nécessite Maven 3.3)
$ j7
$ mvn -V clean package exec:exec javadoc:javadoc -Pjdeps
