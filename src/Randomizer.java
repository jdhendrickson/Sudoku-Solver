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
        Random r = new Random();
        int x, y, iterator;
        Cell tempCell;
        //Clear the current board
        board.blankBoard();
        //Create a random solution
        //For each number
        for (int i = 0; i < board.getSize(); i++) {
            //Add all of this number that can exist
            iterator = 0;
            while (iterator < board.getSize()) {
                //Pick a random location
                x = r.nextInt(board.getSize());
                y = r.nextInt(board.getSize());
                //Check the location is good
                if (Helpers.isValidLocation((char) i, x, y, board, false)) {
                    //valid location, can be used
                    board.getCell(x, y).setContent(Helpers.iterToChar((char) (i + 1)));
                    //Number was placed, increase iterator
                    iterator++;
                }
            }
        }


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
