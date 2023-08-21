package cards.hero;

import cards.minion.Minion;
import gameplay.Board;
import java.util.ArrayList;

public class KingMudface extends Hero {
    /* Constructors */
    public KingMudface() {}
    public KingMudface(int mana, String description, ArrayList<String> colors,
                       String name) {
        super(mana, description, colors, name);
    }

    @Override // Earth Born ability
    public void useAbility(Board board, int index) {
        for (Minion minion : board.row[index].elems) {
            minion.setHealth(minion.getHealth() + 1);
        }
    }
}
