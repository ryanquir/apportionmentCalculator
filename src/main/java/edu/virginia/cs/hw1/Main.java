package edu.virginia.cs.hw1;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

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
//            List<String> headers = parsedFile.getHeaderNames();
//            for (CSVRecord record : parsedFile) {
//                for (String field : record) {
//                    System.out.print("\"" + field + "\", ");
//                }
//                System.out.println();
//            }
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("No argument inputted");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File does not exist");
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file");
        }
    }
}
