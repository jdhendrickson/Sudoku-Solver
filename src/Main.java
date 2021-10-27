class Main {
   public static void main(String[] args) {
       Board board = new Board("9x9Test.json");
       board.blankBoard();
       Randomizer randomizer = new Randomizer(board);

       Solver solver;
       for (int i = 0; i < 5; i++) {
           solver = new Solver(randomizer.randomize());
           solver.solveBruteForce().printBoard();
       }
   }
}
