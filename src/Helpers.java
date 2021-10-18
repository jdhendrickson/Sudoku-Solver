import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
}
