package cards.hero;

import cards.Card;
import gameplay.Board;
import java.util.ArrayList;

/**
 * To be extended by specific heroes' classes.
 */
public class Hero extends Card {
    private int health;
    private int usedTurn;

    public Hero() {}
    public Hero(int mana, String description, ArrayList<String> colors, String name,
                int health, int usedTurn) {
        super(mana, description, colors, name);
        this.health = health;
        this.usedTurn = usedTurn;
    }

    public void useAbility(Board board, int index) {}

    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public int getUsedTurn() {
        return usedTurn;
    }
    public void setUsedTurn(int usedTurn) {
        this.usedTurn = usedTurn;
    }
}
