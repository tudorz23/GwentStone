package cards.minion;

import java.util.ArrayList;

public class Disciple extends SpecialMinion {
    public Disciple() {
    }
    public Disciple(int mana, String description, ArrayList<String> colors, String name, int health,
                    int attack, boolean frozen, boolean usedTurn) {
        super(mana, description, colors, name, health, attack, frozen, usedTurn);
    }

    @Override // God's Plan ability
    public void useAbility(Minion minion) {
        int tmp = minion.getHealth();
        tmp += 2;
        minion.setHealth(tmp);
    }
}
