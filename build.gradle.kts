plugins {
  id("java")
}

group "com.argv-transform" // Replace with your group name
version "1.0.0" // Adjust version as needed

repositories {
  flatDir {
    dirs("lib")
  }
  mavenCentral() // Replace with your preferred repository if needed
}

dependencies {
  implementation(files("lib/bcel-6.2.jar"))
  implementation(files("lib/commons-csv-1.5.jar"))
  implementation(files("lib/commons-io-2.16.1.jar"))
  implementation(files("lib/eclipse.jdt.core.jar"))
  implementation(files("lib/jpf.jar"))
  implementation(files("lib/org.eclipse.core.contenttype-3.4.200-v20140207-1251.jar"))
  implementation(files("lib/org.eclipse.core.jobs-3.6.0-v20140424-0053.jar"))
  implementation(files("lib/org.eclipse.core.resources_3.7.101.dist.jar"))
  implementation(files("lib/org.eclipse.core.runtime-3.7.0.jar"))
  implementation(files("lib/org.eclipse.equinox.common-3.6.0.v20100503.jar"))
  implementation(files("lib/org.eclipse.equinox.preferences-3.5.200-v20140224-1527.jar"))
  implementation(files("lib/org.eclipse.jdt.ui-6.12.2.jar"))
  implementation(files("lib/org.eclipse.jface.jar"))
  implementation(files("lib/org.eclipse.ltk.core.refactoring-6.12.2.jar"))
  implementation(files("lib/org.eclipse.osgi-3.7.1.jar"))
  implementation(files("lib/org.eclipse.text_3.5.0.jar"))
  implementation(files("lib/snakeyaml-2.0.jar"))
  implementation(files("lib/sootclasses-trunk-jar-with-dependencies.jar"))
  // implementation("org.apache.commons:commons-csv:1.5")
  // implementation("commons-io:commons-io:2.16.1")
  // implementation("org.eclipse.jdt:org.eclipse.jdt.core:3.40.0")
  // implementation("org.eclipse.platform:org.eclipse.core.jobs:3.8.0")
  // implementation("org.eclipse.core:org.eclipse.core.resources:3.7.100") // not the same
  // implementation("org.eclipse.core:org.eclipse.core.runtime:3.7.0")
  // implementation("org.eclipse.equinox:org.eclipse.equinox.common:3.6.0.v20100503")
  // implementation("org.eclipse.platform:org.eclipse.equinox.preferences:3.6.1") // not the same
  // implementation("org.eclipse.platform:org.eclipse.ltk.core.refactoring:3.14.600") // not the same
  // implementation("org.eclipse.osgi:org.eclipse.osgi:3.7.1")
  // implementation("org.eclipse.text:org.eclipse.text:3.5.101") // not the same
  // implementation("org.yaml:snakeyaml:2.0")
  // implementation("org.soot-oss:soot:4.6.0")
  testImplementation("org.mockito:mockito-core:5.14.2")
  testImplementation("org.testng:testng:7.10.2")
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
}

// include("lib/sootclasses-trunk-with-dependencies.jar")
// include("jpf.jar")
// source(fileTree("src/java"), "lib/sootclasses-trunk-with-dependencies.jar", "jpf.jar")
task<JavaCompile>("compile") { // Custom named task for clarity
  source(fileTree("src/java"))
  classpath = configurations.runtimeClasspath.get()
  destinationDirectory = file("build/classes/java/")
  outputs.files(fileTree((destinationDirectory)))
}

// task<JavaCompile>("compTransform") { // Custom named task for clarity
//   source(fileTree("src/java"))
//   classpath = configurations.runtimeClasspath.get()
//   destinationDirectory = file("build/classes/java/")
//   outputs.files(fileTree((destinationDirectory)))
// }

// tasks.register("run") { // Custom named task for clarity
//   group = "execution"
//   description = "Runs the compiled application"
//   dependsOn("compile")
//   doLast {
//     val mainClass = "full.Driver" // Replace with the actual main class
//     val classpath = configurations.runtimeClasspath.get().files.joinToString(File.pathSeparator) { it.absolutePath }
    // exec {
    //   commandLine("java", "-cp", classpath, mainClass)
    //   // commandLine("java", "-cp", "build/classes/java/", mainClass)
    // }
//   }
// }

open class ExecOperationsTask @Inject constructor(@Internal val execOperations: ExecOperations) : DefaultTask()

tasks.register<ExecOperationsTask>("run") {
  group = "execution"
  description = "Runs the compiled application"
  dependsOn("compile")
  doLast {
    execOperations.javaexec {
    // classpath = configurations.runtimeClasspath
    classpath = files(configurations.runtimeClasspath.get().files.joinToString(File.pathSeparator))
    classpath += files(":build/classes/java")
    mainClass.set("full.Driver")
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
    // classpath = configurations.runtimeClasspath
    classpath = files(configurations.runtimeClasspath.get().files.joinToString(File.pathSeparator))
    classpath += files(":build/classes/java")
    mainClass.set("transform.Main")
    args("-cp")
    standardOutput = System.out
    errorOutput = System.err
    }
  }
}

// tasks.register("transform") { // Custom named task for clarity
//   group = "execution"
//   description = "Runs the transformer"
//   dependsOn("compile")
//   doLast {
//     val mainClass = "transform.Main" // Replace with the actual main class
//     val classpath = configurations.runtimeClasspath.get().files.joinToString(File.pathSeparator) { it.absolutePath } + files(":build/classes/java")
//     exec {
//       commandLine("java", "-cp", classpath, mainClass)
//       // commandLine("java", "-cp", "build/classes/java/", mainClass)
//     }
//   }
// }

