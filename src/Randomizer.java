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
        //Clear the current board
        board.blankBoard();
        //Create a random solution
        ///Add a random value for each number
        int x, y;
        Cell tempCell;
        for (int i = 0; i < board.getSize(); i++) {
            x = r.nextInt(board.getSize() + 1);
            y = r.nextInt(board.getSize() + 1);
            if (board.getCell(x,y).getContent() == '0') {
                //Blank cell, can be used
                board.getCell(x,y).setContent(Helpers.iterToChar((char) i));
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
