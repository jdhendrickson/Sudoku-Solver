class Main {
   public static void main(String[] args) {
       Board board = new Board();
       Solver solver = new Solver();
       //solver.populateNotes();
       solver.board.getCell(0,2).addNote('1');
       solver.board.getCell(1,2).addNote('2');
       solver.board.getCell(2,2).addNote('3');

       solver.board.getCell(3,2).addNote('4');
       solver.board.getCell(4,2).addNote('5');
       solver.board.getCell(5,2).addNote('6');

       solver.board.getCell(6,2).addNote('7');
       solver.board.getCell(7,2).addNote('8');
       solver.board.getCell(8,2).addNote('9');
       solver.printNotes();
   }
}
