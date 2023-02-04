package edu.virginia.cs.hw1;

//imported Hashtables to use for a "dictionary"
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
//imported csv reader

public class hw1_test_code {
    public static void main(String[] args) {
        int totalPop = 0;  //this will be the total population of the US.
        int statePop = 0; //this will be the total population of each state.
        //create a "dictionary" using HashTable for each state with a temporary value assigned to each key (0)
        int totalRep = 1; //TODO: Pranav will add a function to take in user input, as well as take in a file to read state populations.
        BufferedReader readFile;
        try {
            String fileName = args[0]; // Takes in the file name input
            FileReader file = new FileReader(fileName); // Finds file through file name
            readFile = new BufferedReader(file); // Turns file into readable object
            CSVParser parsedFile = CSVFormat.DEFAULT.withHeader().parse(readFile); // Allows file to be parsed
            List<String> headers = parsedFile.getHeaderNames(); // Gets headers from file
            for (CSVRecord record : parsedFile) {
                for (String field : record) {
                    System.out.print("\"" + field + "\", ");
                }
                System.out.println();
            }
            // TODO: Loop above currently displays csv file contents; Chris can reimplement this to add to hashtable.
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("No argument inputted");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File does not exist");
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file");
        }
        Hashtable<String, Integer> my_dict = new Hashtable<String, Integer>();
        //TODO: Chris will link the user input to a hashtable to organize the data.
        //for (every state in csv list) {
        //  totalPop += statePop
        //  my_dict.put(state, statePop);
        //}
        //TODO: Ryan will do the math on the numbers to allocate representatives
        float avgPopRep = totalPop / totalRep;
        int allocatedReps = 0; //create a variable to keep track of how many reps we allocated.
        float repDivision = 0; // a variable to keep track of the result of state population / average population per rep.
        //for (each state in my_dict) {
        //  repDivision = my_dict.get(state) / avgPopRep
        // need to figure out how to floor the repDivision and keep the decimal remainder for later comparison

        //  my_dict.put(state, "flooredRepDivision");
        //  allocatedReps += "flooredRepDivision"
        //}
        // once we've finished allocations for every state...
        // for ("totalRep - allocatedRep" amount of times) {
        //  find largest remainder, give 1 rep to the corresponding state, then move to next largest remainder.
        //}
        //TODO: return an output for each state.
    }
}
