/*
 * Copyright (c) 2019.
 * Author: Naomi Bonnin
 * Course: CMIS 242 6383
 * Project Name: Project 3 (Iterative vs Recursive)
 * Filename: GUI.java
 * Updated: 9/17/19, 5:47 PM
 * Description: This program creates a simple calculator that calculates both the nth value in the sequence.
 * In addition, it calculates the efficiency of each method based of recursive calls or iterations.
 */

//Import statements for the various java libraries that are used in this project.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.lang.Math.abs;

//The GUI class is the bread and butter of the program.  It contains methods to both create the GUI using swing
//as well as show the GUI when the program launches.  Some of the elements need to be accessed from multiple methods
//and as such, those are declared as variables outside of the constructor.  The GUI class also contains an overridden
//action performed method that updates the uneditable fields.  Lastly, there is an inner class called OnClose that
//serves to generate a .csv file when the window is closed.
public class GUI implements ActionListener {

    //Creates the JFrame that will be used throughout the GUI class
    private JFrame frame = new JFrame();

    //Creates a ButtonGroup to ensure that only one option is selected
    private ButtonGroup type = new ButtonGroup();

    //Creates the various text fields necessary for the GUI to function
    private JTextField n = new JTextField(5);
    private JTextField result = new JTextField(20);
    private JTextField efficiency = new JTextField(5);

    //Private constructor to be called from the main method
    GUI() {

        //Ensures that the program terminates when the window is closed.
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Creates a new WindowListener that calls the OnClose class
        frame.addWindowListener(new OnClose());

        //Sets the title of the frame
        frame.setTitle("Sequence Calculator");

        //Ensures that the window is displayed in the center of the screen
        frame.setLocationRelativeTo(null);

        //Sets the layout to GridBagLayout
        frame.setLayout(new GridBagLayout());

        //Creates the two radio buttons used in this program.  Ensures that Iterative is selected by default and adds
        //both buttons to the previously declared ButtonGroup
        JRadioButton iterative = new JRadioButton("Iterative");
        iterative.setSelected(true);
        type.add(iterative);
        JRadioButton recursive = new JRadioButton("Recursive");
        type.add(recursive);

        //Creates a GridBagConstraints object as well as defining a few constant constraints
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.insets = new Insets(5, 10, 5, 10);

        //Adds a label for choosing which method to use.  Sets it to (0,0) on the grid
        c.gridx = 0;
        c.gridy = 0;
        JLabel typeLabel = new JLabel("Select Method:");
        frame.add(typeLabel, c);

        //Adds the iterative radio button and sets its action command.  Sets it to (1,0) on the grid
        c.gridx = 1;
        iterative.setActionCommand("Iterative");
        frame.add(iterative, c);

        //Adds the recursive radio button and sets its action command.  Sets it to (1,1) on the grid
        c.gridy = 1;
        recursive.setActionCommand("Recursive");
        frame.add(recursive, c);

        //Adds the label for n.  Sets it to (0,2) on the grid
        c.gridy = 2;
        c.gridx = 0;
        JLabel nLabel = new JLabel("Enter N:");
        frame.add(nLabel, c);

        //Adds the text field for n.  Sets it to (1,2) on the grid
        c.gridx = 1;
        frame.add(n, c);

        //Adds the calculate button.  Also adds an action listener to determine when the button is pressed.  Sets it to
        // (1,3) on the grid.
        c.gridy = 3;
        JButton calculate = new JButton("Calculate");
        frame.add(calculate, c);
        calculate.addActionListener(this);

        //Adds the result label.  Sets it to (0,4) on the grid
        c.gridx = 0;
        c.gridy = 4;
        JLabel resultLabel = new JLabel("Result:");
        frame.add(resultLabel, c);

        //Adds the result text field and sets it to be uneditable.  Sets it to (1,4) on the grid
        c.gridx = 1;
        result.setEditable(false);
        frame.add(result, c);

        //Adds the efficiency label.  Sets it to (0,5) on the grid
        c.gridx = 0;
        c.gridy = 5;
        JLabel efficiencyLabel = new JLabel("Efficiency:");
        frame.add(efficiencyLabel, c);

        //Adds the efficiency text field and sets it uneditable.  Sets it to (1,5) on the grid
        c.gridx = 1;
        efficiency.setEditable(false);
        frame.add(efficiency, c);

        //Verifies the proper placement of all of the elements and displays the GUI
        frame.pack();
        frame.setVisible(true);

    }

    //The main method exists only to create and show the GUI
    public static void main(String[] args) {
        new GUI();
    }

    //The actionPerformed method is overridden from the ActionListener superclass.  It defines the specific behavior
    //that should happen when an action event occurs.  In this case, the only possible action event is the pressing
    //of the calculate button.
    @Override
    public void actionPerformed(ActionEvent e) {

        //Determines which radio button is selected
        String selected = type.getSelection().getActionCommand();

        //Default nValue to 0 in case nothing is entered into the text field
        int nValue = 0;

        //Attempts to get the integer from the text field.  Also ensures that the value entered is a integer
        try {
            nValue = Integer.parseInt(n.getText().trim());
        } catch (NumberFormatException error) {
            JOptionPane.showMessageDialog(frame, "Please enter an Integer!");
        }
        nValue = abs(nValue);  //Eliminates any negative values

        //Switch statement that uses the selected radio button to determine which logic to use
        switch (selected) {

            //This case uses the iterative method to determine the n value as well as the efficiency score using the
            //utility class
            case "Iterative": {
                result.setText(Integer.toString(Sequence.computeIterative(nValue)));
                efficiency.setText(Integer.toString(Sequence.getEfficiency()));
                break;
            }

            //This case uses the recursive method to determine the n value as well as the efficiency score using the
            //utility class
            case "Recursive": {
                result.setText(Integer.toString(Sequence.computeRecursive(nValue)));
                efficiency.setText(Integer.toString(Sequence.getEfficiency()));
                break;
            }

            //This is a default case.  In theory, it should be impossible to get to this point.  It is, however,
            //included for completeness
            default: {
                JOptionPane.showMessageDialog(frame, "Unknown error has occurred");
                break;
            }

        }
    }

    //Inner class that is used to create a .csv file when the window is closed.  Contains an overridden method for
    //when the window is in the process of closing
    public static class OnClose extends WindowAdapter {

        //The default no argument constructor.  Given the simple nature of this adapter, there are no changes to
        //the constructor from the superclass
        OnClose() {
            super();
        }

        //Overridden windowClosing method is called when the window is closing.  The logic inside will create the .csv
        //file prior to program terminating
        @Override
        public void windowClosing(WindowEvent e) {

            //Creates a new stringbuilder that will be used to create the .csv
            StringBuilder builder = new StringBuilder();

            //CSV Headers are added to the string
            builder.append("Value of N");
            builder.append(",");
            builder.append("Iterative Efficiency");
            builder.append(",");
            builder.append("Recursive Efficiency");
            builder.append(",");
            builder.append(System.getProperty("line.separator")); //Adds a system agnostic \n

            //Iterates through the first 10 n values and calculates the efficiency scores for both method.  Adds the
            //results to the string and terminates with a new line.
            for (int i = 0; i != 11; i++) {
                builder.append(i);
                builder.append(",");
                Sequence.computeIterative(i);
                builder.append(Sequence.getEfficiency());
                builder.append(",");
                Sequence.computeRecursive(i);
                builder.append(Sequence.getEfficiency());
                builder.append(",");
                builder.append(System.getProperty("line.separator"));
            }

            //Attempts to create a new file called results.csv in the root (where the program is running from) directory.
            //It then writes the string to the file before flushing and closing the bufferedwriter.  Catches IOException
            //in the event that one is thrown.  Prints the result to the console.  Note that if the file result.csv
            //already exists, the file will NOT be created or overwritten.
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File("result.csv")));
                writer.write(builder.toString());
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        }
    }
}
