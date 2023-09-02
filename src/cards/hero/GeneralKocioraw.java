package cards.hero;

import cards.minion.Minion;
import gameplay.Board;
import java.util.ArrayList;

public class GeneralKocioraw extends Hero {
    /* Constructor */
    public GeneralKocioraw(int mana, String description, ArrayList<String> colors,
                           String name) {
        super(mana, description, colors, name);
    }

    // Blood Thirst ability
    public void useAbility(Board board, int index) {
        for (Minion minion : board.row[index].elems) {
            minion.setAttack(minion.getAttack() + 1);
        }
    }
}
