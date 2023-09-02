package cards.hero;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import cards.minion.Minion;
import gameplay.Board;

public class LordRoyce extends Hero {
    /* Constructor */
    public LordRoyce(int mana, String description, ArrayList<String> colors,
                     String name) {
        super(mana, description, colors, name);
    }

    // Sub-Zero ability
    public void useAbility(Board board, int index) {
        // Get the minion with the highest attack from that row
        Minion minion = Collections.max(board.row[index].elems,
                Comparator.comparing(Minion::getAttack));

         minion.setFrozen(true);
    }
}
