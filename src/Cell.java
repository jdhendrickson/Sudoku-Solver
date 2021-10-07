import java.util.ArrayList;

public class Cell {
    private Character content;//Use Character class for consistency with the arraylist
    private ArrayList<Character> notes;
    public Cell() {
        notes = new ArrayList<Character>();
    }
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
    public void removeNote(char c) {
        notes.remove(Character.valueOf(c));
    }
    /**
     * Prints all the notes in the cell
     */
    public void printNotes() {
        for (char i : notes) {
            System.out.print(i);
        }
        System.out.println();
    }
}
