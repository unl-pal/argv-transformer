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
  implementation("org.apache.bcel:bcel:6.2")
  // implementation("bcel-6.2")
  implementation("org.apache.commons:commons-csv:1.5")
  // implementation("commons-csv-1.5")
  implementation("commons-io:commons-io:2.16.1")
  // implementation("commons-io-2.16.1")
  implementation("org.eclipse.jdt:org.eclipse.jdt.core:3.37.0")
  // implementation("eclipse.jdt.core")
  // implementation("jpf")
  implementation(files("jpf.jar"))
  // implementation("org.eclipse.core:org.eclipse.core.contenttype:3.4.200.v20140207-1251")
  // implementation("org.eclipse.core.contenttype-3.4.200-v20140207-1251")
  implementation("org.eclipse.platform:org.eclipse.core.jobs:3.8.0")
  // implementation("org.eclipse.core.jobs-3.6.0-v20140424-0053")
  implementation("org.eclipse.core:org.eclipse.core.resources:3.7.100") // not the same
  // implementation("org.eclipse.core:org.eclipse.core.resources:3.8.101.v20130717-0806") // not the same
  // implementation("org.eclipse.core.resources_3.7.101.dist")
  implementation("org.eclipse.core:org.eclipse.core.runtime:3.7.0")
  // implementation("org.eclipse.core.runtime-3.7.0")
  implementation("org.eclipse.equinox:org.eclipse.equinox.common:3.6.0.v20100503")
  // implementation("org.eclipse.equinox.common-3.6.0.v20100503")
  implementation("org.eclipse.platform:org.eclipse.equinox.preferences:3.6.1") // not the same
  // implementation("org.eclipse.equinox.preferences-3.5.200-v20140224-1527")
  // implementation("org.eclipse.jdt:org.eclipse.jdt.ui:3.33.200") // not the same
  // implementation("org.eclipse.jdt.ui-6.12.2")
  // implementation("org.eclipse.platform:org.eclipse.jface:3.35.100") // no version specified
  // implementation("org.eclipse.jface")
  implementation("org.eclipse.platform:org.eclipse.ltk.core.refactoring:3.14.600") // not the same
  // implementation("org.eclipse.ltk.core.refactoring-6.12.2")
  implementation("org.eclipse.osgi:org.eclipse.osgi:3.7.1")
  // implementation("org.eclipse.platform:org.eclipse.osgi:3.22.0") // not the same
  // implementation("org.eclipse.osgi-3.7.1")
  implementation("org.eclipse.text:org.eclipse.text:3.5.101") // not the same
  // implementation("org.eclipse.text_3.5.0")
  implementation("org.yaml:snakeyaml:2.0")
  // implementation("snakeyaml-2.0")
  implementation("org.soot-oss:soot:4.6.0")
  implementation(files("lib/sootclasses-trunk-with-dependencies.jar"))
  // implementation("sootclasses-trunk-with-dependencies")
  // implementation(fileTree(mapOf("dir" to "lib", "includes" to listOf("*.jar"))))")
  testImplementation("org.mockito:mockito-core:5.14.2")
  testImplementation("org.testng:testng:7.10.2")
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
}

task<JavaCompile>("compile") { // Custom named task for clarity
  source(fileTree("src/java"), "lib/sootclasses-trunk-with-dependencies.jar", "jpf.jar")
  // include("lib/sootclasses-trunk-with-dependencies.jar")
  // include("jpf.jar")
  classpath = configurations.runtimeClasspath.get()
  destinationDirectory = file("build/classes/java/")
  outputs.files(destinationDirectory)
}

tasks.register("run") { // Custom named task for clarity
  group = "execution"
  description = "Runs the compiled application"
  dependsOn("compile")
  doLast {
    val mainClass = "full.Driver" // Replace with the actual main class
    exec {
      commandLine("java", "-cp", "build/classes/java/", mainClass)
    }
  }
}
