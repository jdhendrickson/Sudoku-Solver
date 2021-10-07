import java.util.ArrayList;

class Main {
   public static void main(String[] args) {
       Board board = new Board();
       Solver solver = new Solver();
       solver.solveBruteForce();
       solver.board.printDetails();
   }
}
