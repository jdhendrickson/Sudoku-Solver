class Main {
   public static void main(String[] args) {
       Board board = new Board();
       Solver solver = new Solver("4x4Test.json");
       solver.solveByDeduction();
       solver.printBoard();
   }
}
