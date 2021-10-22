import java.util.ArrayList;

class Main {
   public static void main(String[] args) {
       Solver solver = new Solver("16x16Test.json");
       solver.solveByDeduction();
       solver.board.getBox(2,0).printNotesBox();
   }
}
