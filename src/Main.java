import java.util.ArrayList;

class Main {
   public static void main(String[] args) {
       Solver solver = new Solver(/*"16x16Test.json"*/);
       solver.board.getCell(1,0).addNote('1');
       solver.board.getCell(2,0).addNote('1');

       solver.board.getCell(5,0).addNote('1');
       solver.board.getCell(6,0).addNote('1');
       solver.populateNotesImproved();
       solver.printNotes();
       //*/
   }
}
