plugins {
  id("java")
}

group "com.argv-transform"
version "1.0.0"

repositories {
  flatDir {
    dirs("lib")
  }
  mavenCentral()
}

dependencies {
  implementation(files("lib/bcel-6.2.jar"))
  implementation(files("lib/commons-csv-1.5.jar"))
  implementation(files("lib/commons-io-2.16.1.jar"))
  implementation(files("lib/eclipse.jdt.core.jar"))
//  implementation(files("lib/jpf.jar"))
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
  // implementation(files("lib/snakeyaml-2.0.jar"))
  // implementation(files("lib/sootclasses-trunk-jar-with-dependencies.jar"))
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
  implementation("org.yaml:snakeyaml:2.0")
  implementation("org.soot-oss:soot:4.6.0")
  testImplementation("org.mockito:mockito-core:5.14.2")
  testImplementation("org.testng:testng:7.10.2")
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
}

task<JavaCompile>("compile") {
  source(fileTree("src/java"))
  classpath = configurations.runtimeClasspath.get()
  destinationDirectory = file("build/classes/java/")
  outputs.files(fileTree((destinationDirectory)))
}

open class ExecOperationsTask @Inject constructor(@Internal val execOperations: ExecOperations) : DefaultTask()

tasks.register<ExecOperationsTask>("run") {
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
