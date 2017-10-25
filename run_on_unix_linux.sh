#! /bin/bash

if [ $# -eq 4 ]
  then
    mkdir -p out/production/NeuralNetwork
    find ./src/com/ctk150230/neuralnet -name "*.java" > sources.txt
    javac @sources.txt -d ./out/production/NeuralNetwork

    java -cp ./out/production/NeuralNetwork com.ctk150230.neuralnet.Main $1 $2 $3 $4
else
    echo "Error: Not enough arguments supplied, please provide <training_filename> <testing_filename> <learning_rate> <number_of_iterations>"
fi