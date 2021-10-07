import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

class Board {
  //The base size of the board. Traditional sudoku boards will have a size of 9
  private static int size;
  //The array the board is being held in
  private static Cell[][] cellArray;

  /**
  * Defaults to a basic blank game board that is 9x9
  */
  public Board() {
    this.size = 3;
    cellArray = new Cell[size][size];
    for(int i = 0; i < size; i++) {
      for(int j = 0; j < size; j++) {
        cellArray[i][j] = new Cell();
        cellArray[i][j].setContent('0');
      }
    }
  }
  public static int getSize() {
    return size;
  }
  public static Cell[][] getCellArray() {
    return cellArray;
  }
  /**
   * Get a particular cell
   * @param x The x coordinate
   * @param y The y coordinate
   * @return A reference to the cell at that point
   */
  public Cell getCell(int x, int y) {
    return cellArray[x][y];
  }
  /**
   * Set a particular cell
   * @param x The x coordinate
   * @param y The y coordinate
   * @param c The value to set the cell to
   */
  public void setCell(int x, int y, char c) {
    cellArray[x][y].setContent(c);
  }
  /**
   * Adds a note to a particular cell
   * @param x The x coordinate
   * @param y The y coordinate
   * @param c The value to add as a note
   */
  public void addNote(int x, int y, char c) {
    cellArray[x][y].addNote(c);
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
          System.out.print(cellArray[i][j].getContent());
      }
      System.out.print("\n");
    }
  }
  /**
   * Saves the current board state as a json file. Defaults to "Default.json"
   */
  public void sendToJson() {
    sendToJson("Default");
  }
  /**
   * Saves the current board state as a json file.
   * Note that this saves as a .json file, not as a text file in json format.
   * @param fileName The name of the file. Do not include the file extension.
   */
  public void sendToJson(String fileName) {
    //Add the size
    String out = "{\n\t\"size\":";
    out += size;
    //Add the cell array label
    out += ",\n\t\"cellArray\":[";
    //Add the cell array
    for(int i = 0; i < size; i++) {
      out += "\n\t\t[";
      //Add each cell
      for(int j = 0; j < size; j++) {
        out +="\"";
        //Add the contents of the cell
        out += cellArray[i][j].getContent();
        out +="\",";
      }
      //Remove extra comma at the end
      out = out.substring(0,out.length() - 1);
      out+= "],";
    }
    //Remove extra comma at the end
    out = out.substring(0,out.length() - 1);
    out += "\n\t]\n}";
    //Write the json to the file
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
      myWriter.write(out);
      //Close the file
      myWriter.close();
      System.out.println("Json file written to.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
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
  * Values shall be stored as "1" to "9", and then "A" to "Z". More than 36 values will not be supported.
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
        //Redefine the cell array with the proper size
        cellArray = new Cell[size][size];
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
        int nextRowStart = file.indexOf("[", arrayStart) + 1;
        int nextRowEnd = nextRowStart;
        int i = 0;
        while(nextRowEnd < arrayEnd - 1) {
          //Retrieve the start of the next row
          nextRowStart = file.indexOf("[", nextRowStart) + 1;
          //Retrieve the next row end point
          nextRowEnd = file.indexOf("]", nextRowStart);
          //Retrieve the next row contents
          nextRowContents = file.substring(nextRowStart, nextRowEnd);
          //Split the row into it's contents
          for(int j = 0; j < size; j++) {
            cellArray[i][j] = new Cell();
            cellArray[i][j].setContent(file.substring(nextRowStart + 1 + 4 * j, nextRowStart + 2 + 4 * j).charAt(0));
          }
          i++;
        }
      }
    } catch (IOException ex) {

    }
  }
}
