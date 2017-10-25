if ("%4"=="") goto error


dir /s /B *.java > sources.txt
mkdir out
mkdir out\production
mkdir out\production\NeuralNetwork\
javac @sources.txt -d out\production\NeuralNetwork\

java -cp out\production\NeuralNetwork\ com.ctk150230.neuralnet.Main %1 %2 %3 %4

:error
@echo Error: Not enough arguments supplied, please provide <training_filename> <testing_filename> <learning_rate> <number_of_iterations>
exit 1