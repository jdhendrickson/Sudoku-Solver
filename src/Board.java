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
      String file = Files.readString(filePath);//Get the file as a massive string
      file = file.toLowerCase();//Ensure there are no upper case letters to simplify comparisons
      file = file.replaceAll("\\s","");//Remove excess white space
      int sizeLoc = file.indexOf("\"size\"");//The location of "Size"
      if(sizeLoc == -1) {//If no size was found
        //Exit
        return;
      } else {
        //Size was found
        //Get the string containing the size
        String sizeString = file.substring(file.indexOf(":", sizeLoc)+1,file.indexOf(",", sizeLoc));
        //Get the size from the string
        size = Integer.parseInt(sizeString);
      }


    } catch (IOException ex) {

    }

  }
}
