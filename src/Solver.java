import java.util.ArrayList;

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
            char i = 0;
            while (i < board.getSize() && !solved) {
                i++;
                if (isValidLocation(Helpers.iterToChar(i), x, y,false)) {
                    board.getCell(x,y).setContent(Helpers.iterToChar(i));
                    solveByBruteForce(x, y);
                    if(!solved)
                        board.getCell(x,y).setContent('0');
                }
            }
        }
        return board;
    }

    /**
     * Solves a board based off deduction.
     * This populates all the notes, and then finds which cells only have a single note.
     * It then repeats this process.
     * @return A solved board
     */
    public Board solveByDeduction() {
        int i = 0;
        while (i < 1) {
            i++;
            //Populate all the notes
            populateNotes();
            //Find any cells that only have a single note, and then solve them
            for (int x = 0; x < board.getSize(); x++) {
                for (int y = 0; y < board.getSize(); y++) {
                    //If there is only one note
                    if (board.getCell(x, y).getNotes().size() == 1) {
                        //That note is the solved value
                        board.getCell(x, y).setContent(board.getCell(x, y).getNotes().get(0));
                        //Reset and look for next solved
                        i = 0;
                        x = board.getSize()-1;
                    }
                }
            }
        }
        return board;
    }

    /**
     * Automatically populates the notes based off the formula in 'isValidLocation'
     * @return A board in which all the cells are either solved or populated with notes
     */
    public Board populateNotes() {
        //Go through the x-direction of the board
        for (int x = 0; x < board.getSize(); x++) {
            //Go through the y-direction of the board
            for (int y = 0; y < board.getSize(); y++) {
                //Remove all current notes
                board.getCell(x,y).getNotes().clear();
                //For each value that can be in the sudoku
                for (char k = 0; k <= board.getSize(); k++) {
                    //If the value is not yet solved
                    if (!board.getCell(x,y).isSolved()) {
                        //If it is a valid location for that character
                        if (isValidLocation(Helpers.iterToChar(k), x, y, false)) {
                            //Add the note
                            board.getCell(x, y).addNote(Helpers.iterToChar(k));
                        }
                    }
                }
            }
        }
        return board;
    }

    /**
     * Populates notes and then uses an algorithm to check for issues with the notes
     * @return A board populated with notes
     */
    public Board populateNotesImproved() {
        //populateNotes();
        /*
        //Check with boxes in x plane
        for (int boxX = 0; boxX < board.getSize(); boxX = boxX + board.getBoxSize()) {
            //Check with boxes in y plane
            for (int boxY = 0; boxY < board.getSize(); boxY = boxY + board.getBoxSize()) {
                //Check each box for lines of notes

            }
        }//*/
        int boxX = 2, boxY = 0;
        //Set up the boolean tests
        ArrayList<Boolean> xPlane = new ArrayList<Boolean>();
        ArrayList<Boolean> yPlane = new ArrayList<Boolean>();
        //For each possible item
        for (char i = 1; i <= board.getSize(); i++) {
            //Remove all items
            xPlane.clear();
            yPlane.clear();
            //Set up booleans for this value
            for (int j = 0; j < board.getBoxSize(); j++) {
                xPlane.add(false);
                yPlane.add(false);
            }
            //Check each row
            for (int j = 0; j < board.getBoxSize(); j++) {
                //If it is found in the corresponding row and not in any other row
                if (board.getCell(boxX, boxY + j).getNotes().contains(Helpers.iterToChar(i))) {
                    //That row is the only one that contains the value
                    xPlane.set(j, true);
                }
            }
            //Display for testing, remove for deployment
            System.out.print(Helpers.iterToChar(i));
            for (int j = 0; j < board.getBoxSize(); j++) {
                 System.out.print(" " + xPlane.get(j));
            }
            System.out.println("");
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
    /**
     * Returns the current board state in json format
     * @return A string containing the current board state in json format
     */
    public String sendToJson() {
        return board.sendToJson();
    }
}
