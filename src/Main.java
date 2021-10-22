import java.util.ArrayList;

class Main {
   public static void main(String[] args) {
       Board board = new Board("16x16Test.json");
       Solver solver = new Solver(board);
       solver.solveByDeduction();
       solver.printBoard();
   }
}
