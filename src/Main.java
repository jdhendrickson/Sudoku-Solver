class Main {
   public static void main(String[] args) {
       Solver solver = new Solver("16x16Test.json");
       solver.solveByDeduction();
       solver.printNotes();
   }
}
