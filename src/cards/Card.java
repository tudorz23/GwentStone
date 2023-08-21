package cards;

import java.util.ArrayList;

/**
 * General Card class that will be extended by Minion, Hero
 * and Environment type of classes.
 */
public class Card {
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;

    /**
     * -1 for Minion card in initial state.
     * 0, 1, 2, 3 for Minion card placed on board.
     * 4 for Environment card.
     * 5 for Hero card.
     */
    private int row;

    /* Constructors */
    public Card() {}
    public Card(int mana, String description, ArrayList<String> colors, String name) {
        this.mana = mana;
        this.description = description;
        this.colors = colors;
        this.name = name;
    }

    /* Getters and Setters */
    public int getMana() {
        return mana;
    }
    public void setMana(int mana) {
        this.mana = mana;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public ArrayList<String> getColors() {
        return colors;
    }
    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "Card{" +
                "mana=" + mana +
                ", description='" + description + '\'' +
                ", colors=" + colors +
                ", name='" + name + '\'' +
                ", row=" + row +
                '}';
    }
}
