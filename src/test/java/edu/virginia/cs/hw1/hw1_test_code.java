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
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.lang.Integer;

public class hw1_test_code {
    public static void main(String[] args) {
        //making an input to read csv file
        BufferedReader readFile;
        Hashtable<String, Integer> state_list = new Hashtable<>();
        Hashtable<String, Float> remainder_list = new Hashtable<>();
        try {
            String fileName = args[0];
            int totalRep;
            if (args.length > 1) {
                totalRep = Integer.parseInt(args[1]);
                if (totalRep < 0) {
                    throw new NumberFormatException();
                }
            }
            else {
                //435 is the default representatives value
                totalRep = 435;
            }
            FileReader file = new FileReader(fileName);
            readFile = new BufferedReader(file);
            CSVParser parsedFile = CSVFormat.DEFAULT.withHeader().parse(readFile);
            //CSV reading functions and loop taken from apache commons java docs: https://javadoc.io/doc/org.apache.commons/commons-csv/latest/index.html
            createHash(parsedFile, state_list, remainder_list);

            int totalPop = 0; //a variable to store the total population of all states
            for (String state : state_list.keySet()) {
                // this for loop adds all the populations from each state to make a total population.
                totalPop += state_list.get(state);
            }
            float avgPopRep = totalPop / totalRep; //population per representative
            int allocatedRep = 0; //a variable to keep track of how many reps we allocated.
            hamiltonMethod(state_list, remainder_list, totalRep, avgPopRep, allocatedRep);
            //sorts the state_list and prints their respective representatives.
            output(state_list);

        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("No argument inputted");
        } catch (NumberFormatException e) {
            throw new RuntimeException("Not a valid number");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File does not exist");
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file");
        } catch (ParseException e) {
            throw new RuntimeException("Invalid value in file; Cannot be parsed");
            // Parse exception for debugging and error avoidance.
        }
        //-------------------------------------------------------------
    }

    private static void hamiltonMethod(Hashtable<String, Integer> state_list, Hashtable<String, Float> remainder_list, int totalRep, float avgPopRep, int allocatedRep) {
        //found help at https://docs.oracle.com/javase/7/docs/api/java/util/Collections.html
        int flooredRepDivision;
        float repDivision;
        for (String state : state_list.keySet()) {
            repDivision = ((float) state_list.get(state)) / avgPopRep;
            flooredRepDivision = (int) Math.floor(repDivision);
            remainder_list.put(state, repDivision - flooredRepDivision);
            state_list.put(state, flooredRepDivision);
            allocatedRep += flooredRepDivision;
        }
        //once we've finished allocations for every state...
        while (totalRep > allocatedRep) {
            //find the largest remainder, give 1 rep to the corresponding state, repeat until out of reps
            for (String state : state_list.keySet()) {
                if (Collections.max(remainder_list.values()) == remainder_list.get(state)) {
                    allocatedRep++;
                    state_list.put(state, state_list.get(state) + 1);
                    remainder_list.remove(state);
                    break;
                }
            }
        }
    }

    private static void output(Hashtable<String, Integer> state_list) {
        List<String> states = new ArrayList<>(state_list.keySet());
        Collections.sort(states);
        //used collections to alphabetize a list of states
        for (String state : states) {
            //this for-loop displays all states and their respective values (in integer format)
            System.out.println(state + " - " + state_list.get(state));
        }
    }

    public static void createHash(CSVParser parsedFile, Hashtable state_list, Hashtable remainder_list) throws ParseException {
        float tempVal;
        int tempInt;
        for (CSVRecord record : parsedFile) {
            if (record.size() >= 2) {
                tempVal = NumberFormat.getNumberInstance(Locale.US).parse(record.values()[1]).floatValue();
                tempInt = (int)tempVal;
                if (tempVal-tempInt == 0 && tempVal >= 0) {
                    state_list.put(record.values()[0], tempInt);
                    remainder_list.put(record.values()[0], (float) 0);
                }
            }
        }
    }
}
