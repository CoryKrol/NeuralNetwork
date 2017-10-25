package com.ctk150230.neuralnet;

import java.io.IOException;

public class Assignment04Part01 {

    // Threshold for classification of testing data against trained sigmoid unit
    static final double THRESHOLD = 0.5;

    // Euler's Number rounded to 7 decimal places
    // Used in the sigmoidFunction method
    static final double EULER = 2.7182818;

    public static void main(String[] args) throws IOException{
        // Check if the correct amount of arguments were passed
        if(args.length != 4){
            usage();
            System.exit(1);
        }

        String trainingFilename = args[0];
        String testingFilename = args[1];
        double learningRate = Double.parseDouble(args[2]);
        int numOfIterations = Integer.parseInt(args[3]);

        perceptronDataClass trainingData = new perceptronDataClass(trainingFilename);
        perceptronDataClass testingData = new perceptronDataClass(testingFilename);

        // Weight Vector
        double[] weightVector = new double[trainingData.getNumOfAttributes()];

        // Initialize all weight values to 0
        for(double attributeWeight : weightVector)
            attributeWeight = 0.0;



        // TODO: Remove
        // Used to debug data parsing
        //trainingData.printData();


        // Call the Gradient Descent Algorithm on the training data with initial weight values, the data from parsed training file,
        // specified learning rate, and the specified number of iterations
        weightVector = gradientDescentAlgorithm(numOfIterations, learningRate, weightVector, trainingData);

        // TODO: Implement Accuracy checking of trained Sigmoid Perceptron against the Training and Testing Data and output results to console
    }

    /**
     * A method that implements the Gradient Descent Algorithm to update the weights based on an input of training data<br>
     *
     * After updating the weights it uses the new weights to calculate the predicted output of the trained Sigmoid Perceptron
     * Unit using the data values it just used to update the weights. <br>
     *
     * After updating the weights and predicting the perceptron output it then prints to console the new weight values and predicted
     * output. <br>
     *
     * After finishing the specified number of iterations the method returns the trained weight values to the caller.
     * @param numIterations the number of iterations to use in the algorithm
     * @param learningR the learning rate to use in the algorithm
     * @param weightV an array containing the weights initialized to 0
     * @param trainingSet the perceptronDataClass that was initialized with the training data file
     * @return an array containing the updated weight values after running the algorithm for the specified number of iterations
     */
    private static double[] gradientDescentAlgorithm(int numIterations, double learningR, double[] weightV, perceptronDataClass trainingSet){

        String[] attributeNameVector = trainingSet.getAttributeNameVector();

        // Used to roll over to first data instance if numIterations > numOfDataInstances
        int numOfDataInstances = trainingSet.getNumOfDataInstances();

        for(int i = 1; i <= numIterations; i++){

            // The value of the data instance to use which rolls over to the first data instance
            // if numIterations > numOfDataInstances
            int dataInstanceForCurrentIteration = (i - 1) % numOfDataInstances;

            // Get attribute value vector from custom data class for the data instance specified in
            // 'dataInstanceForCurrentIteration'
            int[] dataAttributeVector = trainingSet.prepareDataInstanceVector(dataInstanceForCurrentIteration);

            // Get the true classification value for the data instance specified in 'dataInstanceForCurrentIteration'
            int dataInstanceTrueValue = trainingSet.getDataClassificationValue(dataInstanceForCurrentIteration);

            // Calculate the dot product of the weightVector and dataAttributeVector
            double dotProductResult = dotProduct(weightV, dataAttributeVector);

            // Calculate the Perceptron Output (Sigmoid Fuction using the dot product of the weightVector and
            // dataAttributeVector as the input values)
            double sigmoidFunctionResult = sigmoidFunction(dotProductResult);

            // Calculate the value of derivative of the Sigmoid Function using dot product of the weightVector and
            // dataAttributeVector as the input values
            double sigmoidDerivativeResult = sigmoidDerivativeFunction(sigmoidFunctionResult);

            // Calculate the Error between the True Output Value and the Calculated Ouput Value
            // (Sigmoid Function using the dot product of the weightVector and dataAttributeVector
            double deltaValue = deltaValue(dataInstanceTrueValue, sigmoidFunctionResult);

            // Update the weight values using the previously calculated values and the learning rate input by the user
            weightV = weightUpdateFunction(weightV, learningR, deltaValue, dataAttributeVector, sigmoidDerivativeResult);

            // Calculate the output of the Sigmoid Perceptron Unit with updated weight values
            // Calculate the new dot product of the updated weightVector and dataAttributeVector
            dotProductResult = dotProduct(weightV, dataAttributeVector);

            // Calculate the new output of the Sigmoid Perceptron Unit with the updated weights and the data values
            // Used to update the weights
            double networkOutput = sigmoidFunction(dotProductResult);

            // Output iteration results to the console
            outputIterationResults(i, weightV, networkOutput, attributeNameVector);
        }

        // Return the trained weights after the specified number of iterations.
        return weightV;
    }

    /**
     * A method to print out the results of each iteration of the Gradient Descent Algorithm
     * @param iterationNumber the current iteration
     * @param updatedWeightVector an array containing the updated weight values as a result from the current iteration
     * @param networkOutputValue the networkOutput value? TODO: fix when I figure out what the nexworkOutput is
     * @param attributeNameVector an array containing the names of each attribute
     */
    private static void outputIterationResults(int iterationNumber, double[] updatedWeightVector, double networkOutputValue, String[] attributeNameVector){
        System.out.print("After iteration: " + iterationNumber + ": ");

        // Output updated weight values for each attribute rounded to 4 decimal places
        for(int i = 0; i < attributeNameVector.length; i++){
            System.out.print("w(" + attributeNameVector[i] + ") = ");
            System.out.printf("%.4f", roundTo4DecimalPlaces(updatedWeightVector[i]));
            System.out.print(", ");
        }

        // Output the Network Output value from the training iteration
        System.out.print("output = ");
        System.out.printf("%.4f", roundTo4DecimalPlaces(networkOutputValue));
        System.out.println("\n");
    }

    /**
     * A method to calculate the dot product of 2 arrays
     * @param weightVector an array containing all the weight values
     * @param attributeVector an array containing all the attribute values for a data instance
     * @return the dot product of the 2 arrays
     */
    private static double dotProduct(double[] weightVector, int[] attributeVector){
        double scalarProduct = 0.0;

        for(int i = 0; i < weightVector.length; i++)
            scalarProduct += weightVector[i] * ((double)attributeVector[i]);

        return scalarProduct;
    }

    /**
     * A method to compute the Sigmoid Function output of a number
     * @param dotProduct the dot product of the weightVector and a data instance's attributeVector
     * @return the output of the Sigmoid Function for a number
     */
    private static double sigmoidFunction(double dotProduct){
        return (1.0 / (1.0 + Math.pow(EULER, (-1.0 * dotProduct))));
    }

    /**
     * A method to output the value of the derivative of the Sigmoid Function for a given output of the Sigmoid Function
     * @param sigmoidFunctionValue the output of the Sigmoid Function for the given value we are calculating the derivative at
     * @return the derivative of the sigmoid function for a given output of the Sigmoid Function
     */
    private static double sigmoidDerivativeFunction(double sigmoidFunctionValue){
        return (sigmoidFunctionValue * (1.0 - sigmoidFunctionValue));
    }

    /**
     * A method to calculate the difference between the true classification value of a data instance and the
     * perceptron's calculated classification value for it <br>
     *
     * Slightly unnecessary, but it reduces clutter and makes the code more readable
     * @param dataClassificationValue the true classification of the data instance
     * @param sigmoidFunctionValue the value of the Sigmoid Function for the dot product of the weightVector and
     *                             a data instance's attributeVector
     * @return the difference between the True Classification Value of a data instance and the perceptron's calculated
     * classification value for it
     */
    private static double deltaValue(int dataClassificationValue, double sigmoidFunctionValue){
        return (((double)dataClassificationValue) - sigmoidFunctionValue);
    }

    /**
     * A method to update the weight values <br>
     *
     * @param weightVector an array holding the weight values
     * @param learningRate the learning rate specified at runtime
     * @param deltaValue the calculated weight delta value for the training iteration
     * @param attributeVector an array containing the attribute values for the data instance used in the training iteration
     * @param sigmoidDerivativeValue the derivative value for the Sigmoid Function at the dot product of the weightVector
     *                               and attributeVector for the data instance used in the current training iteration
     * @return an array containing the updated weight values
     */
    private static double[] weightUpdateFunction(double[] weightVector, double learningRate, double deltaValue, int[] attributeVector, double sigmoidDerivativeValue){

        // Iterate through weight values and update them
        for(int i = 0; i < weightVector.length; i++)
            weightVector[i] += (learningRate * deltaValue * ((double)attributeVector[i]) * sigmoidDerivativeValue);

        return weightVector;
    }

    /**
     * A method to round a decimal to 4 decimal places
     * @param number the number to be rounded
     * @return the number rounded to 4 decimal places
     */
    private static double roundTo4DecimalPlaces(double number){
        return (Math.round(number * 10000.0) / 10000.0);
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
