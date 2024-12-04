// compilation avec Maven configuré pour génrer du J7 avec un JDK 8
$ j8

$ mvn clean compile
...

// puisque maven.compiler.source et target sont configurés pour 1.7 testons avec J7
// exécution en JDK(JRE) 8

$ j7
$ java -showversion -cp target/classes animalsniffer.Main Maven is mega cool

java version "1.7.0_75"
Java(TM) SE Runtime Environment (build 1.7.0_75-b13)
Java HotSpot(TM) 64-Bit Server VM (build 24.75-b04, mixed mode)

Exception in thread "main" java.lang.NoSuchMethodError: java.lang.String.join(Ljava/lang/CharSequence;[Ljava/lang/CharSequence;)Ljava/lang/String;
	at animalsniffer.Main.main(Main.java:27)

// WTF ?? et en J8 ??

// exécution en JDK(JRE) 8
$ java -showversion -cp target/classes animalsniffer.Main Maven is mega cool
java version "1.8.0_40"
Java(TM) SE Runtime Environment (build 1.8.0_40-b27)
Java HotSpot(TM) 64-Bit Server VM (build 25.40-b25, mixed mode)

Maven_is_mega_cool

// Malgré la compilation en J7, nous utilisons une API J8 d'où l'erreur

// Animal Sniffer à la rescousse pour valider que nous utilisons les bonnes APIs

$ mvn clean install -Panimal

[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building Animal Sniffer Demo 0.0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO]
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ animalsniffer ---
[INFO] Deleting /Users/arnaud/Code/MavenDevoxxFR2015/demos/animalsniffer/target
[INFO]
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ animalsniffer ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /Users/arnaud/Code/MavenDevoxxFR2015/demos/animalsniffer/src/main/resources
[INFO]
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ animalsniffer ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /Users/arnaud/Code/MavenDevoxxFR2015/demos/animalsniffer/target/classes
[INFO]
[INFO] --- animal-sniffer-maven-plugin:1.14:check (check-java-compat) @ animalsniffer ---
[INFO] Checking unresolved references to org.codehaus.mojo.signature:java17:1.0
[ERROR] /Users/arnaud/Code/MavenDevoxxFR2015/demos/animalsniffer/src/main/java/animalsniffer/Main.java:7: Undefined reference: String String.join(CharSequence, CharSequence[])
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 2.826 s
[INFO] Finished at: 2015-04-01T01:21:02+02:00
[INFO] Final Memory: 16M/220M
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.codehaus.mojo:animal-sniffer-maven-plugin:1.14:check (check-java-compat) on project animalsniffer: Signature errors found. Verify them and ignore them with the proper annotation if needed. -> [Help 1]
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR]
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException


// Animal sniffer via enforcer

$ mvn clean install -Panimal-enforcer

[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building Animal Sniffer Demo 0.0.1-SNAPSHOT
[INFO] ------------------------------------------------------------------------
[INFO]
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ animalsniffer ---
[INFO] Deleting /Users/arnaud/Code/MavenDevoxxFR2015/demos/animalsniffer/target
[INFO]
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ animalsniffer ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /Users/arnaud/Code/MavenDevoxxFR2015/demos/animalsniffer/src/main/resources
[INFO]
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ animalsniffer ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /Users/arnaud/Code/MavenDevoxxFR2015/demos/animalsniffer/target/classes
[INFO]
[INFO] --- maven-enforcer-plugin:1.4:enforce (check-java-compat) @ animalsniffer ---
[INFO] Checking unresolved references to org.codehaus.mojo.signature:java17:1.0
[ERROR] /Users/arnaud/Code/MavenDevoxxFR2015/demos/animalsniffer/src/main/java/animalsniffer/Main.java:7: Undefined reference: String String.join(CharSequence, CharSequence[])
[WARNING] Rule 0: org.codehaus.mojo.animal_sniffer.enforcer.CheckSignatureRule failed with message:
Signature errors found. Verify them and ignore them with the proper annotation if needed.
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 2.963 s
[INFO] Finished at: 2015-04-01T01:22:43+02:00
[INFO] Final Memory: 17M/220M
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-enforcer-plugin:1.4:enforce (check-java-compat) on project animalsniffer: Some Enforcer rules have failed. Look above for specific messages explaining why the rule failed. -> [Help 1]
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR]
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoExecutionException


