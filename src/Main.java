class Main {
   public static void main(String[] args) {
       Board board = new Board();
       Solver solver = new Solver("16x16Test.json");
       for (char i = 0; i < 36; i++) {
           solver.board.getCell(1, 0).addNote(Helpers.iterToChar(i));
       }
       solver.printNotes();
   }
}
