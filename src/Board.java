import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

class Board {
  //The base size of the board. Traditional sudoku boards will have a size of 9
  private static int size;
  //The array the board is being held in
  private static char[][] cellArray;

  /**
  * Defaults to a basic blank game board that is 9x9
  */
  public Board() {
    this.size = 3;
    cellArray = new char[9][9];
    for(int i = 0; i < size; i++) {
      for(int j = 0; j < size; j++) {
        cellArray[i][j] = 48;
      }
    }
  }
  /**
   * Create a new game board from a specified json file
   */
  public Board(String in) {
    importFromJson(in);
  }

  /**
  * Prints the details of the array.
  * Will state size and current contents of the array.
  */
  public void printDetails() {
    System.out.print("Size: ");
    System.out.println(size);
    System.out.println("Array: ");
    for(int i = 0; i < size; i++) {
      for(int j = 0; j < size; j++) {
          System.out.print(cellArray[i][j]);
      }
      System.out.print("\n");
    }
  }
  public void sendToJson() {

  }
  /**
  * A function to grab information from the json file. Defaults to input.json as filename.
  */
  public void importFromJson() {
      importFromJson("input.json");
  }
  /**
  * A function to grab information from the json file.
  * Data in the json file will be stored as a 'size' value that tells you how big the puzzle is.
  * There will also be a 2-d array holding the starting value stored in each cell.
  * A "0" in the cell will be interpreted as a blank value.
  * Values shall be stored as "1" to "9", and then "A" to "Z". More then 36 values will not be supported.
  */
  public void importFromJson(String fileName) {
    Path filePath = Path.of(fileName);
    try {
      //Read in the file
      String file = Files.readString(filePath);//Get the file as a massive string
      file = file.toLowerCase();//Ensure there are no upper case letters to simplify comparisons
      file = file.replaceAll("\\s","");//Remove excess white space
      ///Find the size of the array
      int sizeLoc = file.indexOf("\"size\"");//The location of "size"
      if(sizeLoc == -1) {//If no size was found
        //Exit
        System.out.println("No Size Found in json input");
        return;
      } else {
        //Size was found
        //Get the string containing the size
        String sizeString = file.substring(file.indexOf(":", sizeLoc)+1,file.indexOf(",", sizeLoc));
        //Get the size from the string
        size = Integer.parseInt(sizeString);
      }
      ///Parse the contents of the array
      int arrayStart = file.indexOf("\"cellarray\"");//The location of "cellArray"
      int arrayEnd = file.indexOf("]]",arrayStart);//The end of the cell array
      if(arrayStart == -1) {//If no array was found
        //Exit
        System.out.println("No Array Found in json input");
        return;
      } else {
        String nextRowContents;
        int nextRowStart = arrayStart;
        int nextRowEnd;
        while() {
          //Retrieve the start of the next row
          nextRowStart = file.indexOf("[", nextRowStart) + 1;
          //Retrieve the next row end point
          nextRowEnd = file.indexOf("]", nextRowStart);
          //Retrieve the next row contents
          nextRowContents = file.substring(nextRowStart, nextRowEnd);
          System.out.println(nextRowContents);
        }
      }

    } catch (IOException ex) {

    }

  }
}
