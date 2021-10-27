import java.util.ArrayList;

class Main {
   public static void main(String[] args) {
       Board board = new Board("9x9Test.json");
       Randomizer randomizer = new Randomizer(board);
       Solver solver = new Solver(randomizer.randomize());
       solver.solveBruteForce().printBoard();
   }
}
