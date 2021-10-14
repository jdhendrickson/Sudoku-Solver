public class Solver {
     Board board;
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
     * @return The solved sudoku board
     */
    public Board solveBruteForce() {
        return solveByBruteForce(0,0);
    }
    /**
     * A recursive function to brute force the sudoku puzzle
     * @param x Where the function is on the x-axis
     * @param y Where the function is on the y-axis
     * @return The current board state. Top level will return the completed board.
     */
    private Board solveByBruteForce(int x, int y) {
        if(x < board.getSize() - 1) {
            x++;
        } else if(y < board.getSize() - 1) {
            y++;
            x = 0;
        } else {
            //Last value has been found, sudoku has been solved
            System.out.println("SOLVED");
            solved = true;
            return board;
        }
        //Check if it's one of the given locations
        if(board.getCell(x,y).isStarter()) {
            //is a starter, don't need to do anything to this cell
            return solveByBruteForce(x, y);
        } else {
            char i = '0';
            while ((i - '0') < board.getSize() && !solved) {
                i++;
                if (isValidLocation(i, x, y,false)) {
                    board.getCell(x,y).setContent(i);
                    solveByBruteForce(x, y);
                    if(!solved)
                        board.getCell(x,y).setContent('0');
                }
            }
        }
        return board;
    }

    /**
     *
     */
    public Board populateNotes() {
        //Go through the x-direction of the board
        for (int i = 0; i < board.getSize(); i++) {
            //Go through the y-direction of the board
            for (int j = 0; j < board.getSize(); j++) {
                //For each value that can be in the sudoku
                for (int k = 0; k < board.getSize(); k++) {
                    //If the value is not yet solved
                    if (!board.getCell(i,j).isSolved()) {
                        //If it is a valid location for that character
                        if (isValidLocation((char) ('0' + k), i, j, false)) {
                            //Add the note
                            board.getCell(i, j).addNote((char) ('0' + k));
                        }
                    }
                }
            }
        }
        return board;
    }

    /**
     * Checks if the specified char can be placed in the specified location
     * @param in The char to be placed
     * @param x The x-location
     * @param y The y-location
     * @return Can the char be placed there?
     */
    private boolean isValidLocation(char in,int x,int y,boolean verbose) {
        //Check both x and y lines
        for (int i = 0; i < board.getSize(); i++) {
            //check the x line
            if (board.getCell(x, i).getContent() == in) {
                if (verbose) {
                    System.out.println("Invalid from y at " + x + "," + i +
                            ", found a " + board.getCell(i, y).getContent());
                }
                return false;
            }
            //check the y line
            if (board.getCell(i, y).getContent() == in) {
                if (verbose) {
                    System.out.println("Invalid from x at " + i + "," + y +
                            ", found a " + board.getCell(i, y).getContent());
                }
                return false;
            }
        }
        //Check box the cell is in
        int boxX = (x / board.getBoxSize()) * board.getBoxSize();
        int boxY = (y / board.getBoxSize()) * board.getBoxSize();
        if (verbose) {
            System.out.println("Checking from " + boxX + "," + boxY + " to "
                    + (boxX + board.getBoxSize() - 1) + "," + (boxY + board.getBoxSize() - 1));
        }
        for (int i = 0; i < board.getBoxSize(); i++) {
            for (int j = 0; j < board.getBoxSize(); j++) {
                if (board.getCell(boxX + i, boxY + j).getContent() == in) {
                    if (verbose) {
                        System.out.println("Invalid from box at " + (boxX + i) + "," + (boxY + j) +
                                ", found a " + board.getCell((boxX + i), (boxY + j)).getContent());
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Prints the current board status.
     * Will state size and current filled cells on the board.
     */
    public void printBoard() {
        board.printBoard();
    }
    /**
     * Prints the current note status.
     */
    public void printNotes() {
        board.printNotes();
    }
}
