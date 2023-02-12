package edu.virginia.cs.hw1;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
//    String input = "C:\Users\chris\Downloads\test_population2022.csv ed";
    @Test
    @DisplayName("Scenario 1: File exists on computer, but it is an invalid type.")
    void main() {
        String[] input = new String[] { "C:\\Users\\chris\\Downloads\\test_population2022.c"};
        Exception invalidFile = assertThrows(RuntimeException.class, () -> hw1_test_code.main(input));
        String expectedMessage = "Invalid input file type";
        //Guide to check which error is being thrown was found at: https://www.baeldung.com/junit-assert-exception
        String actualMessage = invalidFile.getMessage();
        assertEquals(expectedMessage,actualMessage);
    }
    @Test
    @DisplayName("Scenario 2: User input error")
    void inputError() {
        String[] input = new String[] { "C:\\Users\\chris\\Downloads\\test_population2022.csv", "600", "-hamilton"};
        Exception invalidFile = assertThrows(RuntimeException.class, () -> hw1_test_code.main(input));
        String expectedMessage = "Invalid arguments. Try [file, *optional* representatives, *optional* \"--hamilton\"]";
        String actualMessage = invalidFile.getMessage();
        assertEquals(expectedMessage,actualMessage);
    }
    @Test
    @DisplayName("Scenario 3: Not enough reps to allocate")
    void insufficientReps() {
        String[] input = new String[] { "C:\\Users\\chris\\Downloads\\test_population2022.csv", "20", "--hamilton"};
        Exception invalidFile = assertThrows(RuntimeException.class, () -> hw1_test_code.main(input));
        String expectedMessage = "Not enough representatives to allocate to each state";
        String actualMessage = invalidFile.getMessage();
        assertEquals(expectedMessage,actualMessage);
    }

}