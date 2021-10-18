class Main {
   public static void main(String[] args) {
       Board board = new Board();
       Solver solver = new Solver();
       solver.solveByDeduction();
       solver.printBoard();
       String temp = "";
       char j;
       for(char i = 0; i < 36; i++) {
           j = Helpers.iterToChar(i);
           temp +=  (  + i) + ":" + j + "\n";
       }
       System.out.println(temp);
   }
}
