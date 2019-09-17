/*
 * Copyright (c) 2019.
 * Author: Naomi Bonnin
 * Course: CMIS 242 6383
 * Project Name: Project 3 (Iterative vs Recursive)
 * Filename: SequenceTest.java
 * Updated: 9/17/19, 5:47 PM
 * Description: This program creates a simple calculator that calculates both the nth value in the sequence.
 * In addition, it calculates the efficiency of each method based of recursive calls or iterations.
 */

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SequenceTest {

    //Ensures that the provided sequence is accurately calculated
    @Test
    void computeIterativeTest() {
        assertEquals(0, Sequence.computeIterative(0));
        assertEquals(1, Sequence.computeIterative(1));
        assertEquals(2, Sequence.computeIterative(2));
        assertEquals(5, Sequence.computeIterative(3));
        assertEquals(12, Sequence.computeIterative(4));
        assertEquals(29, Sequence.computeIterative(5));
        Sequence.getEfficiency();  //Called to reset the efficiency counter
    }

    //Tests the input of a negative number.  Note that Math.abs is applied in the action handler
    @Test
    void computeIterativeNegativeNumberTest() {
        assertEquals(0, Sequence.computeIterative(0));
        assertEquals(1, Sequence.computeIterative(Math.abs(-1)));
        assertEquals(2, Sequence.computeIterative(Math.abs(-2)));
        assertEquals(5, Sequence.computeIterative(Math.abs(-3)));
        assertEquals(12, Sequence.computeIterative(Math.abs(-4)));
        assertEquals(29, Sequence.computeIterative(Math.abs(-5)));
        Sequence.getEfficiency();  //Called to reset the efficiency counter
    }

    //Ensures that the sequence is calculated correctly
    @Test
    void computeRecursive() {
        assertEquals(0, Sequence.computeRecursive(0));
        assertEquals(1, Sequence.computeRecursive(1));
        assertEquals(2, Sequence.computeRecursive(2));
        assertEquals(5, Sequence.computeRecursive(3));
        assertEquals(12, Sequence.computeRecursive(4));
        assertEquals(29, Sequence.computeRecursive(5));
        Sequence.getEfficiency();  //Called to reset the efficiency counter

    }

    //Tests the efficiency of each method against its theoretical efficiency
    @Test
    void getEfficiency() {
        //Sets value for n
        int n = 100;
        //Runs the iterative version to calculate the efficiency
        Sequence.computeIterative(n);
        //computeIterative should run in O(n+1) complexity.
        assertEquals((n + 1), Sequence.getEfficiency());
        //Runs the recursive version to calculate efficiency
        Sequence.computeRecursive(n);
        //computeRecursive should run in O(2n-1) complexity
        assertEquals((2 * n - 1), Sequence.getEfficiency());
        //New value for n
        n = 150;
        //Runs the iterative version to calculate the efficiency
        Sequence.computeIterative(n);
        //computeIterative should run in O(n+1) complexity.
        assertEquals((n + 1), Sequence.getEfficiency());
        //Runs the recursive version to calculate efficiency
        Sequence.computeRecursive(n);
        //computeRecursive should run in O(2n-1) complexity
        assertEquals((2 * n - 1), Sequence.getEfficiency());
        n = 1235;
        //Runs the iterative version to calculate the efficiency
        Sequence.computeIterative(n);
        //computeIterative should run in O(n+1) complexity.
        assertEquals((n + 1), Sequence.getEfficiency());
        //Runs the recursive version to calculate efficiency
        Sequence.computeRecursive(n);
        //computeRecursive should run in O(2n-1) complexity
        assertEquals((2 * n - 1), Sequence.getEfficiency());
    }
}