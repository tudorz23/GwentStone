package cards.minion;

import java.util.ArrayList;

/**
 * General class for special minions.
 * Contains useAbility() method that will be overwritten
 * by extending classes.
 */
public abstract class SpecialMinion extends Minion {
    /* Constructor */
    public SpecialMinion(int mana, String description, ArrayList<String> colors, String name,
                         int health, int attack) {
        super(mana, description, colors, name, health, attack);
    }

    public abstract void useAbility(Minion minion);
}
