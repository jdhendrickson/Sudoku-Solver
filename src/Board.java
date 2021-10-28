import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

class Board {
  //The base size of the board. Traditional sudoku boards will have a size of 9
  private int size;
  //The array the board is being held in
  private Cell[][] cellArray;

  /**
  * Defaults to a basic blank game board that is 3x3
  */
  public Board() {
    this.size = 3;
    cellArray = new Cell[size][size];
    blankBoard();
  }
  /**
   * Creates a blank board of the specified size
   */
  public Board(int in) {
    this.size = in;
    cellArray = new Cell[size][size];
    blankBoard();
  }
  /**
   * Create a new game board from a specified json file
   * @param in The name of the json. Do not include the .json.
   */
  public Board(String in) {
    importFromJson(in);
  }
  public int getSize() { return size; }
  public Cell[][] getCellArray() { return cellArray; }
  /**
   * Gets the size of the smaller box of the board.
   * This would be a box of size 3 on a 9x9 board, or 4 on a 16x16 board.
   * @return The size of the box
   */
  public int getBoxSize() {
    return (int) Math.sqrt(size);
  }
  /**
   * Gets the number of hints the board starts with
   * @return The number of hints the board starts with
   */
  public int getStartingCount() {
    int out = 0;
    //Go through the x-direction of the board
    for (int x = 0; x < size; x++) {
      //Go through the y-direction of the board
      for (int y = 0; y < size; y++) {
        if (cellArray[y][x].isStarter()) {
          //If the value has been marked as a starter
          out++;
        }
      }
    }
    return out;
  }
  /**
   * Gets the number of hints the board starts with
   * @return The number of hints the board starts with
   */
  public int getSolvedCount() {
    int out = 0;
    //Go through the x-direction of the board
    for (int x = 0; x < size; x++) {
      //Go through the y-direction of the board
      for (int y = 0; y < size; y++) {
        if (cellArray[y][x].isSolved()) {
          //If the cell has been solved
          out++;
        }
      }
    }
    return out;
  }
  /**
   * Get a particular cell
   * @param x The x coordinate
   * @param y The y coordinate
   * @return A reference to the cell at that point
   */
  public Cell getCell(int x, int y) {
    return cellArray[y][x];
  }
  /**
   * Gets the specified box as a small board. Assumes you are using box coordinates.
   * @param x A x location in the box
   * @param y A y location in the box
   */
  public Board getBox(int x, int y) {
    return getBox(x,y,true);
  }
  /**
   * Gets the specified box as a small board.
   * @param x A x location in the box
   * @param y A y location in the box
   * @param usingBoxCoords Decides whether to use box coordinates or not.
   *                       True means that each box has it's own index
   *                       False means that we are deriving box location based off which box contains the provided cell
   * @return The specified box
   */
  public Board getBox(int x, int y, Boolean usingBoxCoords) {
    int xStart, yStart;
    //Get the locations of the box start
    if (usingBoxCoords) {
      xStart = x * getBoxSize();
      yStart = y * getBoxSize();
    } else {
      xStart = getBoxStart(x);
      yStart = getBoxStart(y);
    }
    Board out = new Board(getBoxSize());
    //Transfer all the items over
    for (int i = 0; i < getBoxSize(); i++) {
      for (int j = 0; j < getBoxSize(); j++) {
        //Transfer from the board to the box
        out.getCell(i,j).setContent(this.getCell(xStart + i,yStart + j).getContent());
        out.getCell(i,j).addNotes(this.getCell(xStart + i,yStart + j).getNotes());
      }
    }
    return out;
  }
  /**
   * Replaces the specified box on a board with a different, provided one. Assumes you are using box coordinates
   * @param x A x location in the box
   * @param y A y location in the box
   * @param box The board we are replacing the specified box with
   */
  public void setBox(int x, int y, Board box) {
    setBox(x,y,box,true);
  }
  /**
   * Replaces the specified box on a board with a different, provided one.
   * @param x A x location in the box
   * @param y A y location in the box
   * @param box The board we are replacing the specified box with
   * @param usingBoxCoords Decides whether to use box coordinates or not.
   *                       True means that each box has it's own index
   *                       False means that we are deriving box location based off which box contains the provided cell
   */
  public void setBox(int x, int y, Board box, Boolean usingBoxCoords) {
    int xStart, yStart;
    //Get the locations of the box start
    if (usingBoxCoords) {
      xStart = x * getBoxSize();
      yStart = y * getBoxSize();
    } else {
      xStart = getBoxStart(x);
      yStart = getBoxStart(y);
    }
    //Transfer all the items over
    for (int i = 0; i < getBoxSize(); i++) {
      for (int j = 0; j < getBoxSize(); j++) {
        //Transfer from the box to the board
        this.getCell(xStart + i,yStart + j).setContent(box.getCell(i,j).getContent());
        this.getCell(xStart + i,yStart + j).getNotes().clear();
        this.getCell(xStart + i,yStart + j).addNotes(box.getCell(i,j).getNotes());
      }
    }
  }
  /**
   * Sets the specified cell to be a different cell
   * @param x The x-coordinate
   * @param y The y-coordinate
   * @param in The cell to replace the specified cell with
   */
  public void setCell(int x, int y, Cell in) {
    cellArray[y][x] = in;
  }
  /**
   * Gets the start location of the box
   * @param x Any location in a line
   * @return The location where the box starts on that line
   */
  public int getBoxStart(int x) { return (x / getBoxSize()) * getBoxSize(); }

  /**
   * Clears all values on the board, resetting it to be entirely 0s
   */
  public void blankBoard() {
    for(int i = 0; i < size; i++) {
      for(int j = 0; j < size; j++) {
        setCell(i,j, new Cell(false));
      }
    }
  }

  /**
   * Clears all values on the board, resetting it to only the starting values
   */
  public void resetBoard() {
    for(int i = 0; i < size; i++) {
      for(int j = 0; j < size; j++) {
        //Check if this is a starter cell
        if (!getCell(i,j).isStarter()) {
          //Reset if it is not
          getCell(i, j).setContent('0');
        }
      }
    }
  }

  /**
   * Prints the current board formatted to look nice and readable.
   * Will state current filled cells on the board.
   */
  public void printBoard() {
    printBoard('s');
  }
  /**
   * Prints the current board with the specified formatting
   * @param format Which format you wish to print the board with.
   *               'n' means no formatting
   *               's' means standard formatting
   */
  public void printBoard(char format) {
    String out = "";
    if (format == 'n') {
      for (int y = 0; y < size; y++) {
        for (int x = 0; x < size; x++) {
          out += getCell(x,y).getContent();
        }
        out += '\n';
      }
    } else if (format == 's') {
      String printLine = "-";
      for (int i = 0; i < size + getBoxSize(); i++) {
        printLine += "-";
      }
      printLine += "\n";
      out = printLine;
      //Go through every box on the board
      for (int y1 = 0; y1 < getBoxSize(); y1++) {
        //Go through every cell in the box
        for (int y2 = 0; y2 < getBoxSize(); y2++) {
          out += "|";
          //Go through every box on the board
          for (int x1 = 0; x1 < getBoxSize(); x1++) {
            //Go through every cell in the box
            for (int x2 = 0; x2 < getBoxSize(); x2++) {
              out += getCell((x1 * getBoxSize()) + x2, (y1 * getBoxSize()) + y2).getContent();
            }
            out += "|";
          }
          out += "\n";
        }
        out += printLine;
      }
    }
    System.out.println(out);
  }
  /**
   * Prints out all the notes in the board
   */
  public void printNotes() {
    String out = "";
    //Create the horizontal border between cells
    String printLines = "";
    for (int i = 0; i < getBoxSize(); i++) {
      printLines += "+";
      //Create a border for a single box
      for (int j = 0; j < getBoxSize(); j++) {
        //Create border for a single cell
        for (int k = 0; k < getBoxSize(); k++) {
          printLines += "-";
        }
        printLines += "+";
      }
    }

    printLines += "\n";
    out += printLines;
    //For each y position
    for (int y = 0; y < size; y++) {
      //For each of the rows the notes are displayed on
      for (int row = 0; row < getBoxSize(); row++) {
        //Go through all the different boxes
        for (int x1 = 0; x1 < getBoxSize(); x1++) {
          out += "|";
          //Go through all the cells in each box
          for (int x2 = 0; x2 < getBoxSize(); x2++) {
            //Check all the numbers that will be on the current row
            for (char j = 1; j <= getBoxSize(); j++) {
              out += nextNote((x1 * getBoxSize()) + x2, y, (char) ((row * getBoxSize()) + j));
            }
            out += "|";
          }
        }
        out += "\n";
      }
      out += printLines;
      if (y % getBoxSize() == getBoxSize() - 1 && y < size - 1) {
        out += printLines;
      }
    }
    System.out.println(out);
  }
  /**
   * Prints out all notes assuming this board is a box
   */
  public void printNotesBox() {
    String out = "";
    //Create the horizontal border between cells
    String printLines = "";
    printLines += "+";
    //Create a border for a single box
    for (int j = 0; j < getSize(); j++) {
      //Create border for a single cell
      for (int k = 0; k < getSize(); k++) {
        printLines += "-";
      }
      printLines += "+";
    }
    printLines += "\n";

    out += printLines;
    //Create a single row
    //For each y position
    for (int y = 0; y < size; y++) {
      //For each of the rows the notes are displayed on
      for (int row = 0; row < getSize(); row++) {
          out += "|";
          //Go through all the cells in each box
          for (int x2 = 0; x2 < getSize(); x2++) {
            //Check all the numbers that will be on the current row
            for (char j = 1; j <= getSize(); j++) {
              out += nextNote(x2, y, (char) ((row * getSize()) + j));
            }
            out += "|";
          }
        out += "\n";
      }
      out += printLines;
    }
    System.out.println(out);
  }
  /**
   * Helper function for printing notes
   */
  private char nextNote(int x, int y, char iter) {
    //Assume the output is a space
    ArrayList<Character> currentNotes = getCell(x, y).getNotes();
    char out;
    //Check if the cell is solved
    if (getCell(x, y).isSolved()) {
      out = getCell(x, y).getContent();
    } else {
      //Check if the iterator is a note
      if (currentNotes.contains(Helpers.iterToChar(iter))) {
        //Add the note to what will be printed
        out = Helpers.iterToChar(iter);
      } else {
        //Add a spacer if the iterator is not a note
        out = ' ';
      }
    }
    return out;
  }
  /**
   * Returns the current board state as a json-formatted string
   * @return The current board state
   */
  public String sendToJson() {
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
    return out;
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
      file = file.replaceAll("\\s","");//Remove excess white space
      ///Find the size of the array
      int sizeLoc = file.toLowerCase().indexOf("\"size\"");//The location of "size"
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
      int arrayStart = file.toLowerCase().indexOf("\"cellarray\"");//The location of "cellArray"
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
            //Assume cell started filled
            cellArray[i][j] = new Cell(true);
            //Populate cell
            cellArray[i][j].setContent(file.substring(nextRowStart + 1 + 4 * j, nextRowStart + 2 + 4 * j).charAt(0));
            //Check if actually filled
            if(cellArray[i][j].getContent()=='0') {
              //Cell started blank
              cellArray[i][j] = new Cell(false);
            }
          }
          i++;
        }
      }
    } catch (IOException ex) {

    }
  }
  /**
   * Compare an iterator to a character
   * @param iterator The iterator to be compared. Must be smaller than board size.
   * @param compared The char the iterator is being compared to. Will probably be larger than '0'.
   * @return Is the iterator equal to the compared?
   */
  public boolean isEqual(char iterator, char compared) {
    return  ('0' + iterator) == compared;
  }
}
