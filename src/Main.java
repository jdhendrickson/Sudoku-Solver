class Main {
   public static void main(String[] args) {
       Board board = new Board();
       Solver solver = new Solver();
       //solver.populateNotes();
       solver.board.getCell(0,0).addNote('1');
       solver.board.getCell(1,0).addNote('2');
       solver.board.getCell(2,0).addNote('3');

       solver.board.getCell(3,0).addNote('2');
       solver.board.getCell(4,0).addNote('1');
       solver.board.getCell(5,0).addNote('3');

       solver.board.getCell(6,0).addNote('3');
       solver.board.getCell(7,0).addNote('2');
       solver.board.getCell(8,0).addNote('1');
       solver.printNotes();
   }
}
