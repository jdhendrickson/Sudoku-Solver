import java.util.ArrayList;

class Main {
   public static void main(String[] args) {
       Solver solver = new Solver(/*"16x16Test.json"*/);
       for (char i = 1; i < 10; i++) {
           solver.board.getCell(0, 0).addNote(Helpers.iterToChar(i));
       }
       solver.board.getBox(0,0,true).printNotesBox();
   }
}
