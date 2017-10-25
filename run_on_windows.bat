if ("%4"=="") goto error


dir /s /B *.java > sources.txt
mkdir out
mkdir out\production
mkdir out\production\Assignment04Part01\
javac @sources.txt -d out\production\Assignment04Part01\

java -cp out\production\Assignment04Part01\ com.ctk150230.neuralnet.Main %1 %2 %3 %4

:error
@echo Error: Not enough arguments supplied, please provide <training_filename> <testing_filename> <learning_rate> <number_of_iterations>
exit 1