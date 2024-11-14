#!/bin/bash

for ((i = 50; i <= 10000; i=i+50)); do

	echo "$i $(scala Main $i)"

done
