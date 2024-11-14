name := "functional-java-from-scratch"

version := "1.0.0-SNAPSHOT"

crossPaths := false

autoScalaLibrary := false

libraryDependencies ++= Seq(
	"junit" % "junit" % "4.12" % "test",
	"org.mockito" % "mockito-core" % "1.9.5" % "test",
	"com.novocode" % "junit-interface" % "0.11" % "test"
)

historyRef := "presentation"