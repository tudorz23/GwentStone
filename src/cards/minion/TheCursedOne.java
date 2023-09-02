package cards.minion;

import java.util.ArrayList;

public class TheCursedOne extends SpecialMinion {
    /* Constructor */
    public TheCursedOne(int mana, String description, ArrayList<String> colors, String name,
                        int health, int attack) {
        super(mana, description, colors, name, health, attack);
    }

    // Shape-shift ability
    public void useAbility(Minion minion) {
        int tmp = minion.getHealth();
        minion.setHealth(minion.getAttack());
        minion.setAttack(tmp);
    }
}
