#!/bin/bash

dir=$(dirname $0)

java -cp $dir/jline-1.0.jar:$dir/clojure-1.6.0.jar jline.ConsoleRunner clojure.main $*
