package gameplay;

import static utils.Constants.*;

/**
 * Represents the place where Minion objects are placed during the game.
 * Contains an Array of 4 Row objects.
 * Use: board.row[index].elems
 */
public class Board {
    public Row[] row = new Row[ROWS];

    public Board() {
        for (int i = 0; i < ROWS; i++) {
            row[i] = new Row();
        }
    }
}
