package cards.minion;

import cards.Card;

import java.util.ArrayList;

/**
 * Includes fields specific to Minions.
 * To be extended by SpecialMinion class.
 */
public class Minion extends Card {
    private int health;
    private int attack;
    private boolean frozen;
    private boolean usedTurn;

    /* Constructors */
    public Minion() {}
    public Minion(int mana, String description, ArrayList<String> colors, String name, int health,
                  int attack) {
        super(mana, description, colors, name);
        this.health = health;
        this.attack = attack;
        this.frozen = false;
        this.usedTurn = false;
        this.setType(3);
    }

    /**
     * @return row of the player's board where the Minion should be placed
     */
    public String getBoardRow() {
        if (this.getName().equals("The Ripper") || this.getName().equals("Miraj")
                || this.getName().equals("Goliath") || this.getName().equals("Warden")) {
            return "front";
        }

        return "back";
    }

    /* Getters and Setters */
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public int getAttack() {
        return attack;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }
    public boolean isFrozen() {
        return frozen;
    }
    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }
    public boolean isUsedTurn() {
        return usedTurn;
    }
    public void setUsedTurn(boolean usedTurn) {
        this.usedTurn = usedTurn;
    }

    @Override
    public String toString() {
        return "Minion{" +
                "mana=" + getMana() +
                ", description=" + getDescription() +
                ", colors=" + getColors()+
                ", name=" + getName()+
                ", health=" + health +
                ", attack=" + attack +
                '}';
    }
}
