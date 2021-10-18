class Main {
   public static void main(String[] args) {
       Board board = new Board();
       Solver solver = new Solver("16x16Test.json");
       solver.printBoard();
       solver.solveByDeduction();
       solver.printBoard();
   }
}
