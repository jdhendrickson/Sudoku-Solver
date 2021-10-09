class Main {
   public static void main(String[] args) {
       Board board = new Board();
       Solver solver = new Solver();
       //solver.populateNotes();
       solver.board.getCell(0,0).addNote('1');
       solver.board.getCell(0,0).addNote('2');
       solver.board.getCell(0,0).addNote('4');
       solver.printNotes();
   }
}
