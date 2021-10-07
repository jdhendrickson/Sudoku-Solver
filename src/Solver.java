public class Solver {
    private Board board;
    private boolean solved;
    /**
     * Defaults to input.json as the input file
     */
    public Solver() {
        board = new Board("input.json");
    }
    public Solver(String in) {
        board = new Board(in);
        solved = false;
    }

    /**
     * Solves the sudoku using Brute Force.
     * Warning: Uses a lot of processing power and time
     */
    public Board solveBruteForce() {
        solveByBruteForce(0,0);
        return board;
    }
    private void solveByBruteForce(int x, int y) {
        System.out.println("x:"+x+"y:"+y);
        if(x < board.getSize()) {
            x++;
        } else if(y < board.getSize()) {
            y++;
        } else {
            //Last value has been found, sudoku has been solved
            board.printDetails();
            return;
        }
        //Check if it's one of the given locations
        if(board.getCell(x,y).getIsStarter()) {
            //is a starter, don't need to do anything to this cell
            solveByBruteForce(x, y);
        } else {
            char i = 0;
            while (i < board.getSize()) {
                i++;
                if (isValidLocation((char) ('0' + i), x, y)) {
                    board.getCell(x,y).setContent((char) ('0' + i));
                    solveByBruteForce(x, y);
                }
            }
        }
    }

    /**
     * Checks if the specified char can be placed in the specified location
     * @param in The char to be placed
     * @param x The x-location
     * @param y The y-location
     * @return Can the char be placed there?
     */
    private boolean isValidLocation(char in,int x,int y) {
        //Check both x and y lines
        for (int i = 0; i < board.getSize(); i++) {
            if (board.getCell(x, i).getContent() != in || board.getCell(i, y).getContent() != in) {
                return false;
            }
        }
        //Check box the cell is in
        int boxX = x % board.getBoxSize();
        int boxY = y % board.getBoxSize();
        for (int i = 0; i < board.getBoxSize(); i++) {
            for (int j = 0; j < board.getBoxSize(); j++) {
                if (board.getCell(boxX + i, boxY + j).getContent() != in) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Prints the details of the array.
     * Will state size and current contents of the array.
     */
    public void printDetails() {
        board.printDetails();
    }
}
