#!/bin/bash

if [ $# -lt 2 ]
then
  echo "Not enough arguments"
    echo "Please use format: <scriptname> <run|compile> <desiredModule>"
  exit 1
fi
if [ $# -gt 2 ]

then
  echo "Too many arguments"
    echo "Please use format: <scriptname> <run|compile> <desiredModule>"
  exit 1
fi

echo "Compilation Script: $0 is running with arguments: $1 and $2"

classpath=./lib/commons-io-2.16.1.jar:lib/eclipse.jdt.core.jar:lib/org.eclipse.core.contenttype-3.4.200-v20140207-1251.jar:lib/org.eclipse.core.jobs-3.6.0-v20140424-0053.jar:lib/org.eclipse.core.jobs-3.6.0-v20140424-0053.jar:lib/org.eclipse.core.resources_3.7.101.dist.jar:lib/org.eclipse.core.runtime-3.7.0.jar:lib/org.eclipse.equinox.common-3.6.0.v20100503.jar:lib/org.eclipse.equinox.preferences-3.5.200-v20140224-1527.jar:lib/org.eclipse.jdt.ui-6.12.2.jar:lib/org.eclipse.jface.jar:lib/org.eclipse.ltk.core.refactoring-6.12.2.jar:lib/org.eclipse.osgi-3.7.1.jar:lib/org.eclipse.text_3.5.0.jar:lib/snakeyaml-2.0.jar:lib/commons-csv-1.5.jar:./src/java

if [ "$1" == "compile" ]; then
  if [ "$2" == "full" ]; then
    javac -cp $classpath -d ./build/classes/java/ src/java/full/Driver.java
    exit 0

  elif [ "$2" == "transform" ]; then
    javac -cp $classpath -d ./build/classes/java/ src/java/transform/Main.java
    exit 0

  else
    echo "Argument: $2 is invalid"
    echo "Please use format: <scriptname> <run|compile> <desiredModule>"
    exit 1
  fi

elif [ "$1" == "run" ]; then
  if [ "$2" == "full" ]; then
    java -cp $classpath:build/classes/java full.Driver
    exit 0

  elif [ "$2" == "transform" ]; then
    java -cp $classpath:build/classes/java transform.Main
    exit 0

  else
    echo "Argument: $2 is invalid"
    echo "Please use format: <scriptname> <run|compile> <desiredModule>"
    exit 1
  fi

else
  echo "Argument $1 is invalid"
    echo "Please use format: <scriptname> <run|compile> <desiredModule>"
    exit 1
fi
