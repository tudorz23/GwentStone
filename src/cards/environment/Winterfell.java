package cards.environment;

import cards.minion.Minion;
import gameplay.Board;
import gameplay.Row;
import java.util.ArrayList;

public class Winterfell extends Environment {
    public Winterfell() {}
    public Winterfell(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    public void useAbility(Board board, int index) {
        for (Minion minion : board.row[index].elems) {
            minion.setFrozen(true);
        }
    }
}
