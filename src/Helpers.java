import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Helpers {
    /**
     * Saves the input value as a json file.
     * Note that this saves as a .json file, not as a text file in json format.
     * @param fileName The name of the file. Do not include the file extension.
     * @param contents The string you wish to save to the file
     */
    public static void saveToFile(String fileName, String contents) {
        try {
            //Ensure the file is created
            File output = new File(fileName + ".json");
            if (output.createNewFile()) {
                System.out.println("File created: " + output.getName());
            } else {
                System.out.println(output.getName() +" already exists.");
            }
            //Create a filewriter
            FileWriter myWriter = new FileWriter(output.getName());
            //Write the json
            myWriter.write(contents);
            //Close the file
            myWriter.close();
            System.out.println("Json file written to.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    /**
     * Converts an iterator to the character that is equivalent to it
     * @param in The iterator to be converted
     * @return The converted char
     */
    public static char iterToChar(char in) {
        if (in < 10) {//0-9 numbers
            return (char) ('0' + in);
        } else if (in < 36) {//A-Z
            return (char) ('A' - 10 + in);
        }
        //Other values not found, return null
        return 0;
    }
    /**
     * Converts an input string into what the equivalent char should be for sudoku
     * @param in The string containing the number input. Must be smaller than a char.
     * @return The char that the value should be to be put into the cell value
     */
    public static char strToChar(String in) {
        //Convert the string input into a number within a char value's range
        char temp = (char) Integer.parseInt(in);
        //Use the iterToChar to convert the char number into the char value
        return iterToChar(temp);
    }
    /**
     * Makes the code wait for a user input before continuing.
     * Used as a bad replacement for debugging
     */
    public static void waitForInput() {
        System.out.println("Waiting for input");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }


    /**
     * Checks if the specified char can be placed in the specified location
     * @param in The char to be placed
     * @param x The x-location
     * @param y The y-location
     * @param board The board you are looking for the location on
     * @param verbose Use verbose output? Will print to console if true
     * @return Can the char be placed there?
     */
    public static boolean isValidLocation(char in, int x, int y, Board board, boolean verbose) {
        //Check both x and y lines
        for (int i = 0; i < board.getSize(); i++) {
            //check the x line
            if (board.getCell(x, i).getContent() == in) {
                if (verbose) {
                    System.out.println("Invalid from y at " + x + "," + i +
                            ", found a " + board.getCell(i, y).getContent());
                }
                return false;
            }
            //check the y line
            if (board.getCell(i, y).getContent() == in) {
                if (verbose) {
                    System.out.println("Invalid from x at " + i + "," + y +
                            ", found a " + board.getCell(i, y).getContent());
                }
                return false;
            }
        }
        //Check box the cell is in
        int boxX = board.getBoxStart(x);
        int boxY = board.getBoxStart(y);
        if (verbose) {
            System.out.println("Checking from " + boxX + "," + boxY + " to "
                    + (boxX + board.getBoxSize() - 1) + "," + (boxY + board.getBoxSize() - 1));
        }
        for (int i = 0; i < board.getBoxSize(); i++) {
            for (int j = 0; j < board.getBoxSize(); j++) {
                if (board.getCell(boxX + i, boxY + j).getContent() == in) {
                    if (verbose) {
                        System.out.println("Invalid from box at " + (boxX + i) + "," + (boxY + j) +
                                ", found a " + board.getCell((boxX + i), (boxY + j)).getContent());
                    }
                    return false;
                }
            }
        }
        return true;
    }
}
