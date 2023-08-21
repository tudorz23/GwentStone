package cards.environment;

import cards.minion.Minion;
import gameplay.Board;
import java.util.ArrayList;

public class Firestorm extends Environment {
    /* Constructors */
    public Firestorm() {}
    public Firestorm(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    public void useAbility(Board board, int index) {
        for (Minion minion : board.row[index].elems) {
            minion.setHealth(minion.getHealth() - 1);
        }
    }
}
