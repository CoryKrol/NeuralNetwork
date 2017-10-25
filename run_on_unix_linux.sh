#! /bin/bash

if [ $# -eq 4 ]
  then
    mkdir -p out/production/Assignment04Part01
    find ./src/com/ctk150230/neuralnet -name "*.java" > sources.txt
    javac @sources.txt -d ./out/production/Assignment04Part01

    java -cp ./out/production/Assignment04Part01 com.ctk150230.neuralnet.Main $1 $2 $3 $4
else
    echo "Error: Not enough arguments supplied, please provide <training_filename> <testing_filename> <learning_rate> <number_of_iterations>"
fi