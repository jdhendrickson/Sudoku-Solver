

Class Board {
  //The base size of the board. Traditional sudoku boards will have a size of 9
  private static final int size;
  //The array the board is being held in
  private static char[][] cellArray;

  /**
  * Defaults to a basic blank game board that is 9x9
  */
  public Board() {
    size = 9;
    cellArray = new char[9][9];
    for(int i = 0; i < size; i++) {
      for(int j = 0; j < size; j++) {
        cellArray[i][j] = 0;
      }
    }
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
  public static void SendToJson() {

  }
  /**
  * A function to grab information from the json file. Defaults to input.json as filename.
  */
  public static void ImportFromJson() {
      ImportFromJson("input.json");
  }
  /**
  * A function to grab information from the json file.
  * Data in the json file will be stored as a 'size' value that tells you how big the puzzle is.
  * There will also be a 2-d array holding the starting value stored in each cell.
  * A "0" in the cell will be interpreted as a blank value.
  * Values shall be stored as "1" to "9", and then "A" to "Z". More then 36 values will not be supported.
  */
  public static void ImportFromJson(String fileName) {

  }
}
