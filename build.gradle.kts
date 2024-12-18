plugins {
  id("java")
  id("application")
  id("java-library")
  id("idea")
  id("jvm-test-suite")
}

group "dev.arg-v.transformer"
version "1.0.0"
// rootProject.name.set("argv-transformer")

repositories {
  mavenCentral() // Most commonly used reposistory
  maven {
    url = uri("https://repo.eclipse.org/content/groups/releases/")
  } // Eclipse specific repo that cannot be found on maven central
}

// set run to run the entire program via driver
application {
  mainClass.set("full.Driver")
}

// This enforces java 8 compiance with the code
// also allows for the use of later compilers as long as the code conforms
java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

// All External Dependencies from either repo
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
  testImplementation("net.bytebuddy:byte-buddy:1.12.19")
  testImplementation("net.bytebuddy:byte-buddy-agent:1.12.19")
  testImplementation("junit:junit:4.13.2")
  testImplementation("org.mockito:mockito-core:4.11.0")
  testImplementation("org.objenesis:objenesis:3.3")
  testImplementation("org.testng:testng:7.5.1")
}

tasks.compileJava {
  sourceSets {
    main {
      java {
        srcDirs("src/java")
      }
    }
  }
}

tasks.processResources {
  // yup this is blank
}

tasks.classes {
  sourceSets {
    main {
      java {
        srcDirs("build/classes/java")
      }
    }
  }
}

tasks.run {
  group = "execution"
}


tasks.compileTestJava {
  sourceSets {
    test {
      java {
        srcDirs("test")
      }
    }
  }
}

tasks.processTestResources {
  // yup this is blank
}

tasks.testClasses {
  sourceSets {
    test {
      java {
        srcDirs("build/classes/test")
      }
    }
  }
}


tasks.test {
  testLogging {
    events("FAILED")
  }
}

tasks.javadoc {
}

// tasks.register("testdoc", Javadoc::class) {
//   classpath += configurations.testRuntimeClasspath.get()
//   classpath += configurations.runtimeClasspath.get()
//   classpath += files("build/classes/test")
//   source(fileTree("test"), fileTree("src/java"))
//   destinationDir = file("build/docs/testdoc")
// }

// Task that generates a file called my.classpath
// my.classpath is a template file with many necessary parts used by various common IDEs
// This file is not meant to be an end all file, rather a starting point
tasks.register("ide-paths") {
  group = "Set-Up"
  description = "Generate a file, 'my.classpath'\nFile is a template with class path entries for gradle-managed dependencies"
  doLast {
    val classpathFile = file("my.classpath")
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
    classpathFile.appendText("\t<classpathentry kind=\"con\" path=\"org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.8/\"/>\n")
    classpathFile.appendText("\t<classpathentry kind=\"con\" path=\"org.eclipse.buildship.core.gradleclasspathcontainer\"/>\n")
    classpathFile.appendText("\t<classpathentry kind=\"src\" path=\"src/java\"/>\n")
    classpathFile.appendText("\t<classpathentry kind=\"src\" path=\"src/test\"/>\n")
    classpathFile.appendText("\t<classpathentry kind=\"src\" path=\"test\"/>\n")

    paths.forEach { path ->
      path.get().forEach { file ->
        if (!dependencyList.contains(file.toString())) {
          dependencyList.add(file.toString())
          classpathFile.appendText(
            "\t<classpathentry exported=\"true\" kind=\"lib\" path=\"${file.absolutePath}\"/>\n"
          )
        }
      }
    }
    classpathFile.appendText("</classpath>")
  }
}

// Custom Compile task used to run the individual parts from root
task<JavaCompile>("compile") {
  source(fileTree("src/java"))
  classpath = configurations.runtimeClasspath.get()
  destinationDirectory = file("build/classes/java")
  outputs.files(fileTree((destinationDirectory)))
}

// Custom Test Compile task for use with test types other than unit tests
task<JavaCompile>("compile-test") {
  dependsOn("compile")
  source(fileTree("src/java"), fileTree("src/test"), fileTree("test"))
  classpath = configurations.runtimeClasspath.get() + configurations.testRuntimeClasspath.get()
  classpath += files("build/classes/java")
  destinationDirectory = file("build/classes/test")
  outputs.files(fileTree((destinationDirectory)))
}

// Custom task class used for creating tasks for individual parts of the code
open class ExecOperationsTask @Inject constructor(@Internal val execOperations: ExecOperations) : DefaultTask()

// This is currently the same as 'run'
// Does not actually run the full application despite the name, only what is in Driver
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

// Runs only the filter task
// Assumes that necesary folders already exist eg database
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

// Runs only the transform task
// Assumes that necessary folders already exist eg suitablePrgms
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

tasks.register<ExecOperationsTask>("regression-transformer") {
  group = "testing"
  description = "Runs regression test for transformer"
  dependsOn("compile")
  doLast {
    execOperations.javaexec {
      classpath = files(configurations.runtimeClasspath.get().files.joinToString(File.pathSeparator))
      // classpath = files(configurations.testRuntimeClasspath.get().files.joinToString(File.pathSeparator))
      classpath += files(File.pathSeparator + file("build/classes/java"))
      mainClass.set("transform.Main")
      args("test/transformer/regression", "testOutput")
      // args("-cp")
      standardOutput = System.out
      errorOutput = System.err
    }
  }
}

// Wipes all folders created by the code as well as the build folder
// Use for fresh run of code
tasks.register<Delete>("reset") {
  group = "clean"
  description = "Resets the program - deletes build, database, suitablePrgms and benchmarks"
  delete(files("benchmarks", "suitablePrgms","build","database"))
  println("Deleted Files")
}
