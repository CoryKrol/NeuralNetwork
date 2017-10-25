package com.ctk150230.neuralnet;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException{

        if(args.length != 4){
            usage();
            System.exit(1);
        }

        String trainingFilename = args[0];
        String testingFilename = args[1];
        double learningRate = Double.parseDouble(args[2]);
        int numOfIterations = Integer.parseInt(args[3]);

        perceptronDataClass testingData = new perceptronDataClass(trainingFilename);
        testingData.printData();
    }


    /**
     * Used to display the proper usage if the correct number of arguments are not supplied when launching the
     * program from the console.
     */
    private static void usage(){
        System.out.println("Perceptron Learning Algorithm");
        System.out.println("Author: Charles Krol\n");
        System.out.println("Usage: java -cp com.ctk150230.neuralnet.Main <training_filename> <testing_filename> <learning_rate> <number_of_iterations>");
    }
}
