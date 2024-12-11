plugins {
  id("java")
  id("application")
  id("java-library")
  id("idea")
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(8)
  }
}

group "dev.arg-v.transformer"
version "1.0.0"

repositories {
  mavenCentral()
  maven {
    url = uri("https://repo.eclipse.org/content/groups/releases/")
  }
}

dependencies {
  implementation("org.eclipse.jdt:org.eclipse.jdt.core:3.10.0.v20140604-1726")
  implementation("org.apache.commons:commons-csv:1.5")
  implementation("commons-io:commons-io:2.16.1")
  implementation("org.eclipse.core:contenttype:3.4.200-v20140207-1251")
  implementation("org.eclipse.core:org.eclipse.core.resources:3.7.100")
  implementation("org.eclipse.core:org.eclipse.core.runtime:3.7.0")
  implementation("org.eclipse.equinox:org.eclipse.equinox.common:3.6.0.v20100503")
  implementation("org.eclipse.platform:org.eclipse.core.jobs:3.8.0")
  implementation("org.eclipse.platform:org.eclipse.equinox.preferences:3.6.1")
  implementation("org.eclipse.osgi:org.eclipse.osgi:3.7.1")
  implementation("org.eclipse.text:org.eclipse.text:3.5.101")
  implementation("org.yaml:snakeyaml:2.0")
  implementation("org.soot-oss:soot:4.6.0")
  // implementation("org.mockito:mockito-core:5.14.2")
  // implementation("org.testng:testng:7.10.2")
  // implementation("org.testng:testng:7.5.1")
  implementation("org.junit.jupiter:junit-jupiter:5.11.3")
  implementation("org.junit.jupiter:junit-jupiter-api:5.11.3")
  implementation("org.junit.jupiter:junit-jupiter-engine:5.11.3")
  // testImplementation("org.junit.jupiter:junit-jupiter:5.11.3")
  // testImplementation("org.junit.jupiter:junit-jupiter-engine:5.11.3")
  // testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.3")
  testImplementation("org.mockito:mockito-core:5.14.2")
  testImplementation("org.testng:testng:7.5.1")
}

tasks.register("ide-paths") {
  group = "Set-Up"
  description = "Generate classpath file for IDEs to use for syntax and compiling"
  doLast {
    val classpathFile = file(".classpath")
    if (classpathFile.exists()) {
      classpathFile.delete()
    }
    val paths = listOf(
      configurations.compileClasspath,
      configurations.runtimeClasspath,
      configurations.testCompileClasspath,
      configurations.testRuntimeClasspath
    )
    val dependencyList = mutableListOf("")
    classpathFile.appendText("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
    classpathFile.appendText("<classpath>\n")
    // classpathFile.appendText("  <classpathentry kind=\"src\" path=\"src/java\"/>\n")
    // classpathFile.appendText("  <classpathentry kind=\"src\" path=\"src/test\"/>\n")
    classpathFile.appendText("  <classpathentry kind=\"src\" path=\"src\"/>\n")

    paths.forEach { path ->
      path.get().forEach { file ->
        if (!dependencyList.contains(file.toString())) {
          dependencyList.add(file.toString())
          classpathFile.appendText(
            // " <classpathentry kind=\"lib\" path=\"${file.absolutePath}\"/>\n"
            "  <classpathentry exported=\"true\" path=\"${file.absolutePath}\"/>\n"
            // " <classpathentry exported=\"true\" kind=\"lib\" path=\"${file.absolutePath}\"/>\n"
          )
        }
      }
    }
    classpathFile.appendText("</classpath>")
  }
}

task<JavaCompile>("compile") {
  source(fileTree("src/java"))
  classpath = configurations.runtimeClasspath.get()
  destinationDirectory = file("build/classes/java")
  outputs.files(fileTree((destinationDirectory)))
}

task<JavaCompile>("compile-test") {
  dependsOn("compile")
  // source(fileTree("build/classes/java"), fileTree("src/test"))
  source(fileTree("src/test"), fileTree("test"))
  classpath = configurations.runtimeClasspath.get() + configurations.testRuntimeClasspath.get() + configurations.testCompileClasspath.get()
  classpath += files("build/classes/java")
  destinationDirectory = file("build/classes/test")
  outputs.files(fileTree((destinationDirectory)))
}

// if (name == "compile-test") {
  //   logger.lifecycle("Classpath: ${classpath.joinToString(separator = "\n")}")
  // }

open class ExecOperationsTask @Inject constructor(@Internal val execOperations: ExecOperations) : DefaultTask()

tasks.register<ExecOperationsTask>("full") {
  group = "execution"
  description = "Runs the compiled application"
  dependsOn("compile")
  doLast {
    execOperations.javaexec {
    classpath = files(configurations.runtimeClasspath.get().files.joinToString(File.pathSeparator))
    classpath += files(File.pathSeparator + file("build/classes/java"))
    mainClass.set("full.Driver")
    args("-cp")
    standardOutput = System.out
    errorOutput = System.err
    }
  }
}

tasks.register<ExecOperationsTask>("filter") {
  group = "execution"
  description = "Runs the filter"
  dependsOn("compile")
  doLast {
    execOperations.javaexec {
    classpath = files(configurations.runtimeClasspath.get().files.joinToString(File.pathSeparator))
    classpath += files(File.pathSeparator + file("build/classes/java"))
    mainClass.set("filter.Main")
    args("-cp")
    standardOutput = System.out
    errorOutput = System.err
    }
  }
}

tasks.register<ExecOperationsTask>("transform") {
  group = "execution"
  description = "Runs the transformer"
  dependsOn("compile")
  doLast {
    execOperations.javaexec {
    classpath = files(configurations.runtimeClasspath.get().files.joinToString(File.pathSeparator))
    classpath += files(File.pathSeparator + file("build/classes/java"))
    mainClass.set("transform.Main")
    args("-cp")
    standardOutput = System.out
    errorOutput = System.err
    }
  }
}

tasks.register<Delete>("reset") {
  group = "clean"
  description = "Resets the program - deletes build, database, suitablePrgms and benchmarks"
  delete(files("benchmarks", "suitablePrgms","build","database"))
  println("Deleted Files")
}

tasks.register<ExecOperationsTask>("multitest") {
  group = "testing"
  description = "Experimental code for identifying and running many files in a dir"
  dependsOn("compile-test")
  doLast {
      // val transformerRegressionTests = listOf(
      //   "SymbolicFloatTest",
      //   "SymbolicDoubleTest",
      //   "SymbolicInttest"//,
      //   // "Release2SymbolicDoubleDemo"
      // )
      // for (test in transformerRegressionTests) {
      val filePath = "build/classes/test"
      val classDir = file(filePath)
      classDir.walk().filter { it.isFile && it.extension == "class" }.forEach {
        val test = it.relativeTo(classDir).path.removeSuffix(".class")
        test.replace('/', '.')
        try {
          logger.lifecycle("================\nNow Trying: " + it.relativeTo(classDir))
          // logger.lifecycle(test)
        execOperations.javaexec {
          classpath = files(configurations.runtimeClasspath.get().files.joinToString(File.pathSeparator))
          classpath += files(configurations.testRuntimeClasspath.get().files.joinToString(File.pathSeparator))
          classpath += files(File.pathSeparator + file("build/classes/java"))
          classpath += files(File.pathSeparator + file("build/classes/test"))
          // mainClass.set("SuitableMethodFinderTest.Class")
          // mainClass.set("transformer.regression." + test)
          // mainClass.set(classDir.joinToString('.', test)
          mainClass.set(test)
          args("-cp")
          standardOutput = System.out
          errorOutput = System.err
        }
    } catch (e: Exception) {
      logger.lifecycle("Tried: " + filePath + test +"\nAndFailed")
      }
    }
  }
}

// CODE FOR SEARCHING FOR AND RUNNING UNIT TESTS WITH VARIOUS LIBRARIES
// test {
//   // discover and execute JUnit4-based tests
//   useJUnit()
//
//   // discover and execute TestNG-based tests
//   useTestNG()
//
//   // discover and execute JUnit Platform-based tests
//   useJUnitPlatform()
//
//   // set a system property for the test JVM(s)
//   systemProperty 'some.prop', 'value'
//
//   // explicitly include or exclude tests
//   include 'org/foo/**'
//   exclude 'org/boo/**'
//
//   // show standard out and standard error of the test JVM(s) on the console
//   testLogging.showStandardStreams = true
//
//   // set heap size for the test JVM(s)
//   minHeapSize = "128m"
//   maxHeapSize = "512m"
//
//   // set JVM arguments for the test JVM(s)
//   jvmArgs '-XX:MaxPermSize=256m'
//
//   // listen to events in the test execution lifecycle
//   beforeTest { descriptor ->
//      logger.lifecycle("Running test: " + descriptor)
//   }
//
//   // fail the 'test' task on the first test failure
//   failFast = true
//
//   // skip an actual test execution
//   dryRun = true
//
//   // listen to standard out and standard error of the test JVM(s)
//   onOutput { descriptor, event ->
//      logger.lifecycle("Test: " + descriptor + " produced standard out/err: " + event.message )
//   }
// }
