package cards.environment;

import cards.Card;
import gameplay.Board;
import java.util.ArrayList;
import static utils.Constants.ENVIRONMENT;

/**
 * Contains useAbility() method.
 */
public class Environment extends Card {
    /* Constructors */
    public Environment() {
    }
    public Environment(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
        this.setType(ENVIRONMENT);
    }

    public void useAbility(Board board, int index) {}
}
