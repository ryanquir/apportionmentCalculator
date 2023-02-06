package edu.virginia.cs.hw1;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
//        System.out.println("Make sure you updated the build.gradle " +
//                "file to point to the right main class");
        BufferedReader readFile;
        try {
            String fileName = args[0];
            FileReader file = new FileReader(fileName);
            readFile = new BufferedReader(file);
            CSVParser parsedFile = CSVFormat.DEFAULT.withHeader().parse(readFile);
            List<String> temporary = new ArrayList<String>();
            Hashtable<String, Integer> state_list = new Hashtable<String, Integer>();
            for (CSVRecord record : parsedFile) {
                for (String field : record) {
                    //made a list of states and their values next to each other.
                    temporary.add(field);
                }
            }
            //-------------------------------------------------------------
            for (int i=0;i< temporary.size()-1;i+=2) {
                //for every other value in the temporary list, add the state and its population to a dictionary.
                state_list.put(temporary.get(i), NumberFormat.getNumberInstance(Locale.US).parse(temporary.get(i+1)).intValue());
            }
            //System.out.println(temporary); List of all states next to their respective populations
            //System.out.println(temporary.size()); Size of list. Should be 102 since there are 50 states + DC, times 2.
            //System.out.println(state_list); Hashtable dictionary (non-alphabetical, somehow) of all states paired with their respective populations.
            //System.out.println(state_list.size()); Size of the dictionary. Should be 51 for each state + DC.

            // Found help at : https://www.geeksforgeeks.org/how-to-iterate-through-hashtable-in-java/
            for (String key : state_list.keySet()) {
                // this for-loop displays all states and their respective population (in integer format)
                System.out.println(key
                        + "\t\t"
                        + state_list.get(key));
            }
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("No argument inputted");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File does not exist");
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file");
        } catch (ParseException e) {
            throw new RuntimeException(e);
            // Parse exception for debugging and error avoidance.
        }
        //-------------------------------------------------------------
        //TODO: Create Hamilton's algorithm using the created HashTable "state_list"
        //for (every state in csv list) {
        //  totalPop += statePop
        //  my_dict.put(state, statePop);
        //}
        float avgPopRep = totalPop / totalRep;
        int allocatedReps = 0; //create a variable to keep track of how many reps we allocated.
        float repDivision = 0; // a variable to keep track of the result of state population / average population per rep.
        //for (each state in state_list) {
        //  repDivision = my_dict.get(state) / avgPopRep
        //}
        // need to figure out how to floor the repDivision and keep the decimal remainder for later comparison
        //  my_dict.put(state, "flooredRepDivision");
        //  allocatedReps += "flooredRepDivision"
        //}
        // once we've finished allocations for every state...
        // for ("totalRep - allocatedRep" amount of times) {
        //  find largest remainder, give 1 rep to the corresponding state, then move to next largest remainder.
        //}
    }
}
