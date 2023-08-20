package cards;

import java.util.ArrayList;

/**
 * General Card class that will be extended by Minion
 * and Environment type of classes.
 * What these classes have in common is that their objects will be kept
 * in the deck and the "hands" of the player, as a List of Card objects.
 */
public class Card {
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;

    public Card() {}
    public Card(int mana, String description, ArrayList<String> colors, String name) {
        this.mana = mana;
        this.description = description;
        this.colors = colors;
        this.name = name;
    }

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
}
