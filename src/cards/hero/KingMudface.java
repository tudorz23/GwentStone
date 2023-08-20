package cards.hero;

import cards.minion.Minion;
import gameplay.Board;
import java.util.ArrayList;

public class KingMudface extends Hero {
    public KingMudface() {}
    public KingMudface(int mana, String description, ArrayList<String> colors,
                       String name, int health, int usedTurn) {
        super(mana, description, colors, name, health, usedTurn);
    }

    @Override // Earth Born ability
    public void useAbility(Board board, int index) {
        for (Minion minion : board.row[index].elems) {
            minion.setHealth(minion.getHealth() + 1);
        }
    }
}
