import java.util.ArrayList;

class Main {
   public static void main(String[] args) {
       Board board = new Board("9x9Test.json");
       Solver solver = new Solver(board);
       solver.solveByDeduction();
       solver.printBoard();
   }
}
