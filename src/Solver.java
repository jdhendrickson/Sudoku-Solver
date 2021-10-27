public class Solver {
    private Board board;
    private boolean solved;
    /**
     * Defaults to input.json as the input file
     */
    public Solver() {
        board = new Board("input.json");
        solved = false;
    }
    public Solver(Board in) {
        board = in;
        solved = false;
    }

    /**
     * Solves the sudoku using Brute Force.
     * Warning: Uses a lot of processing power and time
     * @return The solved sudoku board
     */
    public Board solveBruteForce() {
        return solveBruteForce(-1,0);
    }
    /**
     * A recursive function to brute force the sudoku puzzle
     * @param x Where the function is on the x-axis
     * @param y Where the function is on the y-axis
     * @return The current board state. Top level will return the completed board.
     */
    private Board solveBruteForce(int x, int y) {
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
            return solveBruteForce(x, y);
        } else {
            char i = 0;
            while (i < board.getSize() && !solved) {
                i++;
                if (isValidLocation(Helpers.iterToChar(i), x, y,false)) {
                    board.getCell(x,y).setContent(Helpers.iterToChar(i));
                    solveBruteForce(x, y);
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
        Board box;
        //Set up the boolean tests
        int whichRow, whichColumn;
        boolean isOnlyValue;
        //Go through all the boxes
        for (int xBox = 0; xBox < board.getBoxSize(); xBox++) {
            for (int yBox = 0; yBox < board.getBoxSize(); yBox++) {
                box = board.getBox(xBox, yBox, true);
                //For each possible item
                for (char i = 1; i <= board.getSize(); i++) {
                    //Check each box for lines of notes
                    whichRow = -1;
                    whichColumn = -1;
                    isOnlyValue = true;
                    //Check for lines
                    for (int j = 0; j < box.getSize(); j++) {
                        for (int k = 0; k < box.getSize(); k++) {
                            //If it is found in the corresponding row and not in any other row
                            if (box.getCell(k, j).getNotes().contains(Helpers.iterToChar(i))) {
                                //If the value has been found before
                                if (whichRow != -1) {
                                    isOnlyValue = false;
                                }
                                //If a row containing the value has not been found before
                                if (whichRow == -1) {
                                    //There has not been a row already found, save this row
                                    whichRow = j;
                                } else if (whichRow != j) {
                                    //There has already been a row found with this value, no line in this box
                                    whichRow = -2;
                                }
                            }
                            //If it is found in the corresponding column and not in any other column
                            if (box.getCell(j, k).getNotes().contains(Helpers.iterToChar(i))) {
                                //If a column containing the value has not been found before
                                if (whichColumn == -1) {
                                    //There has not been a column already found, save this column
                                    whichColumn = j;
                                } else if (whichColumn != j) {
                                    //There has already been a column found with this value, no line in this box
                                    whichColumn = -2;
                                }
                            }
                        }
                    }
                    if (whichRow >= 0 && !isOnlyValue) {
                        //Remove the values from the x row
                        for (int j = 0; j < board.getSize(); j++) {
                            //Remove unwanted notes
                            board.getCell(j, yBox * board.getBoxSize() + whichRow).removeNote(Helpers.iterToChar(i));
                        }
                    } else if (whichColumn >= 0 && !isOnlyValue) {
                        //Remove the values from the y row
                        for (int j = 0; j < board.getSize(); j++) {
                            //Remove unwanted notes
                            board.getCell(xBox * board.getBoxSize() + whichColumn, j).removeNote(Helpers.iterToChar(i));
                        }
                    } else {
                        //Nothing was found"
                    }
                    board.setBox(xBox, yBox, box, true);
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
        int boxX = board.getBoxStart(x);
        int boxY = board.getBoxStart(y);
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
