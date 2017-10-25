package com.ctk150230.neuralnet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * <h1>Perceptron Data Class</h1>
 *
 * This class parses a data file and organizes it into data structures for use
 * by the gradient descent algorithm.
 *
 * The data is stored in a LinkedHashMap with nested ArrayLists
 *
 * The data is accessed using an attribute's name as a key into the LinkedHashMap and
 * a specific data instance's value on that attribute is accessed using the instance's
 * number as an index into the ArrayList (Starting at 0)
 *
 * @see ArrayList
 * @see LinkedHashMap
 * @see Map
 * @see BufferedReader
 * @see FileReader
 * @see IOException
 * @see FileNotFoundException
 *
 * @author Charles Krol
 * @since 2017-10-24
 */

public class perceptronDataClass {

    // Uses Attribute names as keys and holds linked lists
    // with all data instance values for a specific attribute
    private LinkedHashMap<String, ArrayList<Integer>> dataMap;

    // Used to hold the name of the classification attribute so
    // we don't have to iterate through the entire LinkedHashMap to get
    // a Data Instance's classification value
    private String classificationAttributeName;

    // Number of Attributes and Data Instances
    private int numOfDataInstances;
    private int numOfAttributes;


    /**
     * A constructor which accepts a filename as an argument and Initializes
     * the LinkedHashMap. The filename is passed as an argument to the method
     * parseDataFile which parses the file and populates the LinkedHashMap and the
     * nested ArrayLists with the data.
     * @param fileName the path to a training or testing file location
     * @throws IOException
     */
    perceptronDataClass(String fileName) throws IOException{

        dataMap = new LinkedHashMap<>();
        parseDataFile(fileName);
    }

    /**
     * This method parses a file and populates the perceptronDataClass Object's
     * LinkedHashMap and the nested ArrayLists with data. <br>
     *
     * This method also counts the number of Attributes and Data Instances <br>
     *
     * The name of the classification attribute is stored for easy access to an instance's
     * classification value without having to iterate through the entire LinkedHashMap each time
     * we need it.
     *
     * @param fileName the path to a training or testing file location
     * @throws IOException
     */
    private void parseDataFile(String fileName) throws IOException{

        FileReader fr = null;
        BufferedReader br = null;

        // Holds a line read from the input file
        String line;

        // String Array to hold lines read from the input file split on whitespaces
        String[] splitStr;

        try{
            // Create FileReader and BufferedReader
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);

            // Read first in line from the file
            line = br.readLine();

            splitStr = line.split("\\s");

            numOfAttributes = splitStr.length;

            // Initialize dataMap with attributes
            for(int i = 0; i < numOfAttributes; i++){

                // Store the name of the classification attribute
                if(i == numOfAttributes - 1)
                    classificationAttributeName = splitStr[i];

                dataMap.put(splitStr[i], new ArrayList<>());
            }

            // Counts the number of Data Instances in our file
            numOfDataInstances = 0;
            line = br.readLine();

            // Iterate through remaining lines of file
            while(line != null){

                // Keep track of position in splitStr Array
                int i = 0;

                // Split line up on whitespaces
                splitStr = line.split("\\s");

                // Iterate through attributes and store values for current line in contained
                // ArrayLists
                for(Map.Entry<String, ArrayList<Integer>> entry : dataMap.entrySet()) {
                    entry.getValue().add(Integer.parseInt(splitStr[i]));
                    i++;
                }

                // Increment number of data instances
                numOfDataInstances++;

                // Grab next line for processing
                line = br.readLine();
            }

        } catch (FileNotFoundException e){
            System.out.println("Error: Filename " + fileName + " does not exist");
            System.exit(1);
        } finally {
            if (br != null)
                br.close();
            if (fr != null)
                fr.close();
        }
    }

    /**
     * Used for debugging the file parsing algorithm
     */
    void printData(){

        // Print out attribute Names for first row
        for(Map.Entry<String, ArrayList<Integer>> entry: dataMap.entrySet()){
            System.out.print(entry.getKey() + " ");

            // If the attribute is the classification value print newline
            if(entry.getKey().equals(classificationAttributeName))
                System.out.print("\n");
        }

        // Iterate through data instances
        for(int i = 0; i < numOfDataInstances; i++){

            // Print out attribute values for the current data instance
            for(Map.Entry<String, ArrayList<Integer>> entry: dataMap.entrySet()){
                System.out.print(entry.getValue().get(i) + " ");

                // If the attribute is the classification value print newline
                if(entry.getKey().equals(classificationAttributeName))
                    System.out.print("\n");
            }
        }
    }

}
