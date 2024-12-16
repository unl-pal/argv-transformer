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
  mavenCentral()
  maven {
    url = uri("https://repo.eclipse.org/content/groups/releases/")
  }
}

application {
  mainClass.set("full.Driver")
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(8))
    // vendor.set(JvmVendorSpec.AZUL)
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
        // srcDirs(configurations.runtimeClasspath)
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
}

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

task<JavaCompile>("compile") {
  // source(fileTree("src/java"), fileTree("test"))
  source(fileTree("src/java"))
  classpath = configurations.runtimeClasspath.get()
  destinationDirectory = file("build/classes/java")
  outputs.files(fileTree((destinationDirectory)))
}

task<JavaCompile>("compile-test") {
  dependsOn("compile")
  // source(fileTree("build/classes/java"), fileTree("src/test"))
  // source(fileTree("src/test"), fileTree("test"))
  source(fileTree("src/java"), fileTree("src/test"), fileTree("test"))
  classpath = configurations.runtimeClasspath.get() + configurations.testRuntimeClasspath.get()
  // classpath += files("test/unit")
  // classpath += files("test/testresources")
  // classpath += files("test/transformer")
  classpath += files("build/classes/java")
  destinationDirectory = file("build/classes/test")
  outputs.files(fileTree((destinationDirectory)))
}

// tasks.register("full") {
//   group = "execution"
//   description = "Runs the 'Full' thing"
//   sourceSets {
//     main {
//       java {
//         srcDirs("src/java")
//       }
//     }
//   }
//   dependsOn("compileJava")
//   doLast {
//     execOperations.javaexec  {
//       classpath = files(configurations.runtimeClasspath.get().files.joinToString(File.pathSeparator))
//       classpath += files(File.pathSeparator + file("build/classes/java"))
//       mainClass.set("full.Driver")
//       args("-cp")
//       standardOutput = System.out
//       errorOutput = System.err
//     }
//   }
//   // classpath.set(configurations.runtimeClasspath)
//   // classpath(sourceSets.main.runtimeClasspath.get().files.joinToString(File.pathSeparator))
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

tasks.register<ExecOperationsTask>("regression") {
  group = "testing"
  description = "Runs Regression Tests"
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

