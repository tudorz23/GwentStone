package cards.minion;

import java.util.ArrayList;

public class TheRipper extends SpecialMinion {
    /* Constructors */
    public TheRipper() {
    }
    public TheRipper(int mana, String description, ArrayList<String> colors, String name, int health,
                     int attack) {
        super(mana, description, colors, name, health, attack);
    }


    @Override // Weak Knees ability
    public void useAbility(Minion minion) {
        int tmp = minion.getAttack();
        tmp -= 2;
        minion.setAttack(tmp);

        if (minion.getAttack() < 0)
            minion.setAttack(0);
    }
}
