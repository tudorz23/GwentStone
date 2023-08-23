package gameplay;

import cards.minion.Minion;
import java.util.ArrayList;
import static utils.Constants.MAX_ROW_SIZE;

/**
 * Contains an ArrayList of Minion objects.
 * To be paired with Board class to simulate the game board.
 */
public class Row {
    // elements of a single row of the board
    public ArrayList<Minion> elems = new ArrayList<>();

    /**
     * Checks if the row is full or not.
     * @return true if full, false if it is not full
     */
    public boolean isFull() {
        return elems.size() == MAX_ROW_SIZE;
    }
}
