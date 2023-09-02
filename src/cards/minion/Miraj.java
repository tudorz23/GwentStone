package cards.minion;

import java.util.ArrayList;

public class Miraj extends SpecialMinion {
    /* Constructor */
    public Miraj(int mana, String description, ArrayList<String> colors, String name,
                 int health, int attack) {
        super(mana, description, colors, name, health, attack);
    }

    // Skyjack ability
    public void useAbility(Minion minion) {
        int tmp = this.getHealth();
        this.setHealth(minion.getHealth());
        minion.setHealth(tmp);
    }
}
