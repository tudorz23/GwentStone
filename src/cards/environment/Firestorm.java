package cards.environment;

import cards.minion.Minion;
import gameplay.Board;
import java.util.ArrayList;

public class Firestorm extends Environment {
    /* Constructor */
    public Firestorm(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    public void useAbility(Board board, int index) {
        for (Minion minion : board.row[index].elems) {
            minion.setHealth(minion.getHealth() - 1);
        }

        // Check if any minion is dead
        board.row[index].elems.removeIf(minion -> minion.getHealth() <= 0);
    }
}
