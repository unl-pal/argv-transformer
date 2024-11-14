#!/usr/bin/env bash

clear

run() {
  echo -e "\\033[1m\$ $*\\033[0m"
  $*
}

commentaire() {
  echo ""
  echo -e "\\033[32m# $*\\033[0m"
}

enter() {
  echo -en "\\033[31m[...]\\033[0m"
  read
}

show() {
  #echo -en "\\033[92m"
  $* | pygmentize -l xml
  #echo -en "\\033[0m"
  enter
}

commentaire "cas étonnant : la compilation avec un JDK 8, du fait d'optimisation aggressives, crée une dépendance à l'API JDK 8..."
j8
run mvn -V clean package exec:exec javadoc:javadoc
commentaire "Animal Sniffer détecte le problème (même s'il plante lamentablement avec un NPE)..."

enter

commentaire "L'exécution avec un JRE 7 montre plus clairement le problème (qui ne provient pas du source)..."
j7
run java -cp target/classes toolchains.Main

commentaire "le problème est bien détecté par Animal Sniffer, mais cela ne le résoud pas."
commentaire "il faut donc faire attention à ne jamais compiler ce code source avec un JDK 8..."
commentaire "les toolchains permettent d'être sûrs de l'éviter..."

enter

commentaire "le JDK 9 apporte une réponse avec un nouveau flag à javac: -release (juillet 2015, JEP 247 : Compile for Older Platform Versions)."
commentaire "Sans ce flag, même problème qu'avec le JDK 8"
mkdir target/j9
j9
run javac -version -target 1.6 -source 1.6 src/main/java/*/*.java -d target/j9
j7
run java -cp target/j9 toolchains.Main

enter

commentaire "mais avec ce flag '-release 6', plus de problème"
mkdir target/j9-release6
j9
run javac -version -release 6 src/main/java/*/*.java -d target/j9-release6
j7
run java -cp target/j9-release6 toolchains.Main

enter

clear
commentaire "bonus : alors que Maven utilise un JDK 7, on peut exécuter jdeps du JDK 8/9..."
commentaire "ATTENTION : cela nécessite Maven 3.3"
show grep -B 2 -A 13 maven-jdeps-plugin pom-toolchain-jdeps.xml
run mvn -V clean package -f pom-toolchain-jdeps.xml
