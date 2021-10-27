public class Randomizer {
    private Board board;
    public Randomizer() {
        board = new Board("input.json");
    }
    public Randomizer(Board in) {
        board = in;
    }
}
