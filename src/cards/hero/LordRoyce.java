package cards.hero;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import cards.minion.Minion;
import gameplay.Board;

public class LordRoyce extends Hero {
    public LordRoyce() {}
    public LordRoyce(int mana, String description, ArrayList<String> colors,
                     String name, int health, int usedTurn) {
        super(mana, description, colors, name, health, usedTurn);
    }

    @Override // Sub-Zero ability
    public void useAbility(Board board, int index) {
        // get the minion with the highest attack from that row
        Minion minion = Collections.max(board.row[index].elems,
                Comparator.comparing(Minion::getAttack));

        // TODO: nu stiu daca merge si asa
        // minion.setFrozen(true);

        int pos = board.row[index].elems.indexOf(minion);
        board.row[index].elems.get(pos).setFrozen(true);
    }
}
