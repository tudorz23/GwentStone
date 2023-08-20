package cards.hero;

import cards.Card;
import gameplay.Board;
import java.util.ArrayList;

/**
 * To be extended by specific heroes' classes.
 */
public class Hero extends Card {
    private int health;
    private boolean usedTurn;

    public Hero() {}
    public Hero(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
        this.health = 30;
        this.usedTurn = false;
        this.setRow(5);
    }

    public void useAbility(Board board, int index) {}

    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public boolean getUsedTurn() {
        return usedTurn;
    }
    public void setUsedTurn(boolean usedTurn) {
        this.usedTurn = usedTurn;
    }
}
