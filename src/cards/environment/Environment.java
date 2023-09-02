package cards.environment;

import cards.Card;
import gameplay.Board;
import java.util.ArrayList;
import static utils.Constants.ENVIRONMENT;

/**
 * Contains useAbility() method.
 */
public abstract class Environment extends Card {
    /* Constructor */
    public Environment(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
        this.setType(ENVIRONMENT);
    }

    public abstract void useAbility(Board board, int index);
}
