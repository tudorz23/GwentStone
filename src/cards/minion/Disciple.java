package cards.minion;

import java.util.ArrayList;

public class Disciple extends SpecialMinion {
    /* Constructor */
    public Disciple(int mana, String description, ArrayList<String> colors, String name, int health,
                    int attack) {
        super(mana, description, colors, name, health, attack);
    }

    // God's Plan ability
    public void useAbility(Minion minion) {
        minion.setHealth(minion.getHealth() + 2);
    }
}
