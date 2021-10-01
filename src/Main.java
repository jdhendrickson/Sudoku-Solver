class Main {
    private static int size;
    private static char[][] cellArray;

   public static void main(String[] args) {
       Board board = new Board();
       board.printDetails();
       try {
           board.importFromJson();
           board.printDetails();
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
}
