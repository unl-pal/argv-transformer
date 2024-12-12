plugins {
  id("java")
  id("application")
  id("java-library")
  id("idea")
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
  testImplementation("org.junit.jupiter:junit-jupiter:5.11.3")
  testImplementation("org.junit.jupiter:junit-jupiter-engine:5.11.3")
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.3")
  testImplementation("org.mockito:mockito-core:5.14.2")
  testImplementation("org.testng:testng:7.5.1")
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
    classpathFile.appendText("\t<classpathentry kind=\"src\" path=\"src/java\"/>\n")
    classpathFile.appendText("\t<classpathentry kind=\"src\" path=\"src/test\"/>\n")

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
  source(fileTree("src/java"))
  classpath = configurations.runtimeClasspath.get()
  destinationDirectory = file("build/classes/java")
  outputs.files(fileTree((destinationDirectory)))
}

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
