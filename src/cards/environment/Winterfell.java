package cards.environment;

import cards.minion.Minion;
import gameplay.Board;
import java.util.ArrayList;

public class Winterfell extends Environment {
    /* Constructor */
    public Winterfell(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    public void useAbility(Board board, int index) {
        for (Minion minion : board.row[index].elems) {
            minion.setFrozen(true);
        }
    }
}
