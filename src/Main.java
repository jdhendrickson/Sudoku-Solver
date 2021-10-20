import java.util.ArrayList;

class Main {
   public static void main(String[] args) {
       Solver solver = new Solver(/*"16x16Test.json"*/);
       solver.board.getCell(0,0).addNote('1');
       solver.board.getCell(3,0).addNote('1');
       solver.printNotes();
       solver.populateNotesImproved();
       //*/
   }
}
