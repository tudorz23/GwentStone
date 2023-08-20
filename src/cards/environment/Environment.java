package cards.environment;

import cards.Card;
import gameplay.Board;
import java.util.ArrayList;

/**
 * Contains useAbility() method.
 */
public class Environment extends Card {
    public Environment() {
    }
    public Environment(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
        this.setRow(4);
    }

    public void useAbility(Board board, int index) {}
}
