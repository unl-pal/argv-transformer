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

commentaire "build et exécution classiques en JDK/JRE 7..."
j7
run mvn -V clean package exec:exec javadoc:javadoc

enter

clear
commentaire "maven-toolchain-plugin => Maven s'exécute en JRE 7 mais demande un JDK 6 pour les plugins toolchain-aware..."
show grep -B 2 -A 16 maven-toolchains-plugin pom-toolchain.xml
commentaire "avec des JDKs définis dans le toolchains.xml..."
show cat ~/.m2/toolchains.xml
run mvn -V clean package exec:exec javadoc:javadoc -f pom-toolchain.xml

enter

clear
commentaire "test cas d'erreur: version de JDK qui n'est pas configurée dans les toolchains..."
run mvn -V clean package exec:exec javadoc:javadoc -f pom-toolchain-jdk5.xml
