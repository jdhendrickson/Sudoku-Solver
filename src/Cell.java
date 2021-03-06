import java.util.ArrayList;

public class Cell {
    private Character content;//Use Character class for consistency with the arraylist
    private boolean isStarter;
    private ArrayList<Character> notes;
    public Cell(boolean starter) {
        isStarter = starter;
        notes = new ArrayList<Character>();
        content = Character.valueOf('0');
    }
    public boolean isStarter() {
        return isStarter;
    }
    public boolean isSolved() {return content != '0';}
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
    /**
     * Prints the contents of the cell
     */
    public void printContent() {
        System.out.print(content);
    }

    /**
     * Converts an iterator to the character that is equivalent to it
     * @param in The iterator to be converted
     * @return The converted char
     */
    public static char iterToChar(char in) {
        return (char) ('0' + in);
    }
}
