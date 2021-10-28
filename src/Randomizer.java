import java.util.Random;

public class Randomizer {
    private Board board;
    public Randomizer() {
        board = new Board("input.json");
    }
    public Randomizer(Board in) {
        board = in;
    }

    /**
     * Randomize the board
     * @return a randomized board
     */
    public Board randomize() {
        Solver solver;
        Random r = new Random();
        int x, y, iterator;
        Cell tempCell;
        //Clear the current board
        board.blankBoard();
        //Create a random solution
        ///Create the randomnesss
        //For each number
        for (int i = 1; i < board.getSize() + 1; i++) {
            //Add all of this number that can exist
            iterator = 0;
            while (iterator < 1) {
                //Pick a random location
                x = r.nextInt(board.getSize());
                y = r.nextInt(board.getSize());
                //Check the location is good
                if (Helpers.isValidLocation(Helpers.iterToChar((char) i), x, y, board, false)) {
                    //valid location, can be used
                    board.getCell(x, y).setContent(Helpers.iterToChar((char) i));
                    //Number was placed, increase iterator
                    iterator++;
                }
            }
        }
        //Finish solving the puzzle to prove it's a valid sudoku

        
        //Remove extra values to make a sudoku puzzle
        //Check after removing each value there is still only 1 solution


        //Set cells to be starters
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getCell(i,j).getContent() != '0') {
                    //This is not a blank cell, and is thus a starter
                    tempCell = new Cell(true);
                    tempCell.setContent(board.getCell(i,j).getContent());
                    board.setCell(i,j,tempCell);
                }
            }
        }
        return board;
    }
}
