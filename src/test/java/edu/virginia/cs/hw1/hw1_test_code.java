package edu.virginia.cs.hw1;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.lang.Integer;

public class hw1_test_code {
    public static Boolean hamilton = false;

    public static void main(String[] args) {
        //making an input to read csv file
        BufferedReader readFile;
        Hashtable<String, Integer> state_list = new Hashtable<>();
        Hashtable<String, Float> remainder_list = new Hashtable<>();
        try {
            String fileName = args[0];
            int totalRep;
            if (args.length > 1) {
                if (args[1].equals("--hamilton") && args.length == 2) {
                    // Ex. "java -jar Apportionment.jar census2020.xlsx --hamilton"
                    hamilton = true;
                    totalRep = 435;
                } else if (args.length == 3 && args[2].equals("--hamilton")) {
                    // Ex. java -jar Apportionment.jar 2020census.xlsx 600 --hamilton
                    hamilton = true;
                    totalRep = Integer.parseInt(args[1]);
                    if (totalRep < 0) {
                        throw new NumberFormatException();
                    }
                } else if (args.length == 2) {
                    // Ex. java -jar Apportionment.jar 2020census.csv 600
                    totalRep = Integer.parseInt(args[1]);
                    if (totalRep < 0) {
                        throw new NumberFormatException();
                    }
                } else {
                    throw new RuntimeException("Invalid arguments. Try [file, *optional* representatives, *optional* --hamilton]");
                }
            } else {
                //435 is the default representatives value, and we use huntington-hill.
                // Ex. java -jar Apportionment.jar 2020census.csv
                totalRep = 435;
            }
            Sheet sheet;
            if (fileName.endsWith(".csv")) {
                FileReader file = new FileReader(fileName);
                readFile = new BufferedReader(file);
                CSVParser parsedFile = CSVFormat.DEFAULT.withHeader().parse(readFile);
                createHash_csv(parsedFile, state_list, remainder_list);
            } else {
                sheet = getSheet(fileName);
                sheet.removeRow(sheet.getRow(0));
                createHash_xlsx(state_list, remainder_list, sheet);
            }
            // checking if provided reps are enough to allocate,
            if (totalRep < state_list.size()) {
                throw new RuntimeException("Not enough representatives to allocate to each state");
            }
            float totalPop = 0;
            for (String state : state_list.keySet()) {
                // add all the populations -> total population.
                totalPop += state_list.get(state);
            }
            float avgPopRep = totalPop / totalRep; //population per representative
            int allocatedRep = 0; //a variable to keep track of how many reps we allocated.
            if (hamilton) {
                hamiltonMethod(state_list, remainder_list, totalRep, avgPopRep, allocatedRep);
            } else {
                // TODO: huntington-hill method here.

            }

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

    private static void createHash_xlsx(Hashtable<String, Integer> state_list, Hashtable<String, Float> remainder_list, Sheet sheet) {
        float tempVal;
        int tempInt;
        for (Row row : sheet) {
            if (row.getPhysicalNumberOfCells() >= 2) {
                tempVal = (float) row.getCell(1).getNumericCellValue();
                tempInt = (int) tempVal;
                if (tempVal - tempInt == 0 && tempVal >= 0) {
                    state_list.put(row.getCell(0).getStringCellValue(), tempInt);
                    remainder_list.put(row.getCell(0).getStringCellValue(), (float) 0);
                }
            }
        }
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
                if (Collections.max(remainder_list.values()).equals(remainder_list.get(state))) {
                    allocatedRep++;
                    state_list.put(state, state_list.get(state) + 1);
                    remainder_list.remove(state);
                    break;
                }
            }
        }
    }

    //private static void huntingtonHillMethod(Hashtable<String, Integer> state_list, Hashtable<String, Float> remainder_list, int totalRep, float avgPopRep, int allocatedRep) {

    //}

    private static void output(Hashtable<String, Integer> state_list) {
        List<String> states = new ArrayList<>(state_list.keySet());
        Collections.sort(states);
        //used collections to alphabetize a list of states
        for (String state : states) {
            //this for-loop displays all states and their respective values (in integer format)
            System.out.println(state + " - " + state_list.get(state));
        }
    }

    static Sheet getSheet(String fileName) throws IOException {
        FileInputStream input = new FileInputStream(fileName);
        Workbook workbook;
        if (fileName.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(input);
            return workbook.getSheetAt(0);
        } else if (fileName.endsWith(".xls")) {
            workbook = new HSSFWorkbook(input);
            return workbook.getSheetAt(0);
        } else {
            throw new RuntimeException("Invalid input file type");
        }
    }

    public static void createHash_csv(CSVParser parsedFile, Hashtable<String, Integer> state_list, Hashtable<String, Float> remainder_list) throws ParseException {
        float tempVal;
        int tempInt;
        for (CSVRecord record : parsedFile) {
            if (record.size() >= 2) {
                tempVal = NumberFormat.getNumberInstance(Locale.US).parse(record.values()[1]).floatValue();
                tempInt = (int) tempVal;
                if (tempVal - tempInt == 0 && tempVal >= 0) {
                    state_list.put(record.values()[0], tempInt);
                    remainder_list.put(record.values()[0], (float) 0);
                }
            }
        }
    }
}