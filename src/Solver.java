public class Solver {
    private Board board;
    /**
     * Defaults to input.json as the input file
     */
    public Solver() {
        board = new Board("input.json");
    }
    public Solver(String in) {
        board = new Board(in);
    }

    /**
     * Solves the sudoku using Brute Force.
     * Warning: Uses a lot of processing power and time
     */
    public void solveBruteForce() {

    }
    /**
     * Prints the details of the array.
     * Will state size and current contents of the array.
     */
    public void printDetails() {
        board.printDetails();
    }
}
