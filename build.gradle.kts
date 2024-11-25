plugins { id("java") }

repositories {
  mavenCentral()
  // flatDir {
  //   // dirs 'lib';
  //   dirs("lib")
  // }
}

dependencies {
  implementation("org.apache.bcel:bcel:6.2")
  implementation("org.apache.commons:commons-csv:1.5")
  implementation("commons-io:commons-io:2.16.1")
  // implementation("org.eclipse.jdt:org.eclipse.jdt.core:3.39.0")
  // implementation("org.eclipse.platform:org.eclipse.core.contenttype:3.9.500")
  // implementation("org.eclipse.platform:org.eclipse.core.resources:3.7.101.dist")
  implementation("org.yaml:snakeyaml:2.0")
  implementation(fileTree(mapOf("dir" to "lib", "includes" to listOf("*.jar"))))
  // implementation(fileTree(dir("lib"), includes(["*.jar"])))
  // implementation(files("lib/bcel-6.2.jar"))
  // implementation(files("commons-csv-1.5.jar"))
  // implementation(files("commons-io-2.16.1.jar"))
  // implementation(files("jpf.jar"))
  // implementation(files("org.eclipse.core.contenttype-3.4.200-v20140207-1251.jar"))
  // implementation(files("org.eclipse.core.jobs-3.6.0-v20140424-0053.jar"))
  // implementation(files("org.eclipse.core.resources_3.7.101.dist.jar"))
  // implementation(files("org.eclipse.core.runtime-3.7.0.jar"))
  // implementation(files("org.eclipse.equinox.common-3.6.0.v20100503.jar"))
  // implementation(files("org.eclipse.equinox.preferences-3.5.200-v20140224-1527.jar"))
  // implementation(files("org.eclipse.jdt.ui-6.12.2.jar"))
  // implementation(files("org.eclipse.jface.jar"))
  // implementation(files("org.eclipse.ltk.core.refactoring-6.12.2.jar"))
  // implementation(files("org.eclipse.osgi-3.7.1.jar"))
  // implementation(files("org.eclipse.text_3.5.0.jar"))
  // implementation(files("sootclasses-trunk-jar-with-dependencies.jar"))
  testImplementation("org.testng:testng:7.10.2")
}

tasks.withType<JavaCompile> { options.encoding = "UTF-8" }

sourceSets {
    main {
      java {
        srcDirs("src/java")
      }
    }
    test {
      java {
        srcDirs("test/")
      }
    }
}

tasks.register("download") {}

tasks.register("filter") {}

tasks.register("transform") {}

tasks.named<Test>("test") {
  useTestNG {
    // val options = this as TestNGOptions
    // options.excludeGroups("integrationTests")
    // options.includeGroups("unitTests")
  }
  maxHeapSize = "1G"
  testLogging { events("passed") }
  reports.html.required = true
}

// tasks.withType<Test> {
//   useTestNG()
//   // TestNG config needed?
// }

tasks.withType<Jar> {
  // configure JAR packaging
}
