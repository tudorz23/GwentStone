package cards.environment;

import cards.minion.Minion;
import gameplay.Board;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HeartHound extends Environment {
    /* Constructor */
    public HeartHound(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
    }

    public void useAbility(Board board, int index) {
        int mirror = 3 - index;

        // Get the minion with the highest health from that row
        Minion minion = Collections.max(board.row[index].elems,
                Comparator.comparing(Minion::getHealth));

        // Add it to the mirrored row
        board.row[mirror].elems.add(minion);

        // Remove it from the original row
        board.row[index].elems.remove(minion);
    }
}
