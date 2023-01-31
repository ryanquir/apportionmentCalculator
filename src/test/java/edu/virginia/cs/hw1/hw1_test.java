package edu.virginia.cs.hw1;

//imported Hashtables to use for a "dictionary"
import java.util.Hashtable;

public class hw1_test {
    public static void main(String[] args) {
        int totalPop = 0;  //this will be the total population of the US.
        int statePop = 0; //this will be the total population of each state.
        //create a "dictionary" using HashTable for each state with a temporary value assigned to each key (0)
        int totalRep = 1; //TODO: Change 0 to take in user input, as well as take in a file to read state populations.
        Hashtable<String, Integer> my_dict = new Hashtable<String, Integer>();
        //for (every state in csv list) {
        //  totalPop += statePop
        //  my_dict.put(state, statePop);
        //}
        float avgPopRep = totalPop / totalRep;
        int allocatedReps = 0; //create a variable to keep track of how many reps we allocated.
        float repDivision = 0; // a variable to keep track of the result of state population / average population per rep.
        //for (each state in my_dict) {
        //  repDivision = my_dict.get(state) / avgPopRep
        // TODO: need to figure out how to floor the repDivision and keep the decimal remainder for later comparison

        //  my_dict.put(state, "flooredRepDivision");
        //  allocatedReps += "flooredRepDivision"
        //}
        // once we've finished allocations for every state...
        // for ("totalRep - allocatedRep" amount of times) {
        //  //TODO: find largest remainder, give 1 rep to the corresponding state, then move to next largest remainder.
        //}
        //TODO: write a function to display our allocated reps.
    }
}
