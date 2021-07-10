import java.io.*;

class SudokuSolver {
    private static int size;
    private static char[][] cellArray;

   public static void main(String[] args) {
       ImportFromJson();
       System.out.println("Size: ");
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
