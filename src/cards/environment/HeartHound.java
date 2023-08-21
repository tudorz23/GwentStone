package cards.environment;

import cards.minion.Minion;
import gameplay.Board;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static utils.Constants.ROWS;

public class HeartHound extends Environment {
    /* Constructors */
    public HeartHound() {}
    public HeartHound(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    public void useAbility(Board board, int index) {
        int mirror = ROWS - index;

        // get the minion with the highest health from that row
        Minion minion = Collections.max(board.row[index].elems,
                Comparator.comparing(Minion::getHealth));

        // add it to the mirrored row
        board.row[mirror].elems.add(minion);

        // remove it from the original row
        board.row[index].elems.remove(minion);
    }
}
