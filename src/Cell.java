import java.util.ArrayList;

public class Cell {
    private Character content;//Use Character class for consistency with the arraylist
    private ArrayList<Character> notes;

    public char getContent() {
        return content;
    }
    public ArrayList<Character> getNotes() {
        return notes;
    }

    public void setContent(char c) {
        content = Character.valueOf(c);
    }
    public void addNote(char c) {
        notes.add(Character.valueOf(c));
    }
}
