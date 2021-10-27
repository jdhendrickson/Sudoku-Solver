class Main {
   public static void main(String[] args) {
       Board board = new Board("9x9Test.json");
       board.blankBoard();
       //Randomizer randomizer = new Randomizer(board);
       Solver solver = new Solver(board);
       solver.printBoard();
       solver.solveBruteForce().printBoard();
   }
}
