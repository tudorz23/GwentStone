package cards.hero;

import cards.Card;
import gameplay.Board;
import java.util.ArrayList;
import static utils.Constants.HERO;
import static utils.Constants.INITIAL_HEALTH_HERO;

/**
 * To be extended by specific heroes' classes.
 */
public class Hero extends Card {
    private int health;
    private boolean usedTurn;

    /* Constructors */
    public Hero() {}
    public Hero(int mana, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
        this.health = INITIAL_HEALTH_HERO;
        this.usedTurn = false;
        this.setType(HERO);
    }

    public void useAbility(Board board, int index) {}

    /* Getters and Setters */
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
