package cards.hero;

import cards.minion.Minion;
import gameplay.Board;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EmpressThorina extends Hero {
    public EmpressThorina() {}
    public EmpressThorina(int mana, String description, ArrayList<String> colors,
                          String name) {
        super(mana, description, colors, name);
    }

    @Override // Low Blow ability
    public void useAbility(Board board, int index) {
        // get the minion with the highest health from that row
        Minion minion = Collections.max(board.row[index].elems,
                Comparator.comparing(Minion::getHealth));

        board.row[index].elems.remove(minion);
    }
}
