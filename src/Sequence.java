/*
 * Copyright (c) 2019.
 * Author: Naomi Bonnin
 * Course: CMIS 242 6383
 * Project Name: Project 3 (Iterative vs Recursive)
 * Filename: Sequence.java
 * Updated: 9/17/19, 5:47 PM
 * Description: This program creates a simple calculator that calculates both the nth value in the sequence.
 * In addition, it calculates the efficiency of each method based of recursive calls or iterations.
 */

import java.util.Arrays;

//Utility or helper class that contains the actual logic for calculating the nth value.  Contains a private constructor
//to prevent an object from being created outside of the class.
final class Sequence {

    //Variable that is updated as efficiency is calculated
    private static int efficiency = 0;

    //No argument constructor for completeness
    private Sequence() {
        //Intentionally blank
    }

    //The compute iterative method calculates the nth value in the sequence using the iterative method.  The method
    //takes one argument that corresponds to n.  Returns an int that corresponds to the nth value.  Runs in O(n) time
    //complexity.  (Technically it is O(n+1) but constants don't matter)
    static int computeIterative(int n) {

        //Updates the efficiency count.  This is necessary since the same variable is being used for both methods
        efficiency++;

        //Creates an array to store all values of n.  The array is 2 elements larger then n in order to aid in
        //the calculation of the nth value
        int[] results = new int[n + 2];
        results[0] = 0; //Sets the value of 0.  This is needed because the formula draws upon these two values prior
        results[1] = 1; //Sets the value of 1.  to the calculation of these two values.

        //Loops through all values of n
        for (int i = 0; i != n; i++) {

            //The formula for calculating the next value in the sequence.  The mathematical formula is as follows:
            // f(x) = 2(n-1)+(n-2)
            results[i + 2] = 2 * results[i + 1] + results[i];

            //Updates the efficiency score once for every time the program loops through
            efficiency++;
        }

        //Returns the calculated nth value of the sequence
        return results[n];
    }

    //The computeRecursive method recursively calculates the nth value in the sequence.  Takes one argument that
    //corresponds to n.  Returns an int that corresponds to the nth value in the sequence.  This is the original version
    //that runs in O(n^2) time complexity.  It is left here simply as proof that I did this method originally prior to
    //asking how it is possible to reduce the complexity.
    static int computeRecursiveOld(int n) {

        //Updates the efficiency score
        efficiency++;

        //Initial default or base case
        if (n == 0) {
            return 0;
        }

        //Additional base case
        if (n == 1) {
            return 1;
        }

        //Recursive call of the method.  Returns an int.  Mathematical formula is as follows: f(x) = 2(n-1)+(n-2)
        else {
            return 2 * computeRecursive(n - 1) + computeRecursive(n - 2);
        }
    }


    //The computeRecursive method recursively calculates the nth value in the sequence.  Takes one argument that
    //corresponds to n.  Returns an int that corresponds to the nth value in the sequence.  This version included
    //memoization to allow the method to run in O(n) time complexity. (Technically its O(2n-1) but constants don't
    //really matter.
    private static int sequenceMemo(int n, int[] values) {
        efficiency++;
        if (n == 0 || n == 1) return n;

        if (values[n] == -1) {
            values[n] = 2 * sequenceMemo(n - 1, values) + sequenceMemo(n - 2, values);
        }

        return values[n];
    }

    //Part of the method above, this method creates an array of n+1 size and fills it with -1 so that the above method
    //knows that the value has not yet been calculated.
    static int computeRecursive(int n) {
        int[] values = new int[n + 1];
        Arrays.fill(values, -1);
        return sequenceMemo(n, values);
    }

    //The getEfficiency method simply returns the most recently calculated efficiency score and then resets it
    static int getEfficiency() {
        int efficiencyFinal = efficiency;
        efficiency = 0;
        return efficiencyFinal;
    }
}
