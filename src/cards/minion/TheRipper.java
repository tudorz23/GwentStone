package cards.minion;

import java.util.ArrayList;

public class TheRipper extends SpecialMinion {
    public TheRipper() {
    }
    public TheRipper(int mana, String description, ArrayList<String> colors, String name, int health,
                     int attack, boolean frozen, boolean usedTurn) {
        super(mana, description, colors, name, health, attack, frozen, usedTurn);
    }

    @Override // Weak Knees ability
    public void useAbility(Minion minion) {
        int tmp = minion.getAttack();
        tmp -= 2;
        minion.setAttack(tmp);
    }
}
