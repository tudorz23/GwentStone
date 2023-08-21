package gameplay;

import cards.Card;
import cards.hero.Hero;
import cards.minion.Minion;

import java.util.ArrayList;

/**
 * Describes the characteristics of a player.
 */
public class Player {
    private ArrayList<Deck> packDeck;
    private int nrDecks;
    private Deck deck;  // the deck chosen for current game
    private ArrayList<Card> hand;
    private int wins;
    private Hero hero;
    private int mana;
    private int index; // to know which player he is

    /* Constructor */
    public Player(int index) {
        this.wins = 0;
        this.mana = 0;
        this.index = index;
    }

    /**
     * Must be used only when it is known the Card is a Minion!
     * @param index position of the card in player's hand
     * @return Board's row where a Minion should be placed based on player and Minion name
     */
    public int getRowToPlace(int index) {
        // Front row of player1
        if (((Minion)(this.getHand().get(index))).getBoardRow().equals("front")
                && this.getIndex() == 1) {
            return 2;
        }

        // Front row of player2
        if (((Minion)(this.getHand().get(index))).getBoardRow().equals("front")
                && this.getIndex() == 2) {
            return 1;
        }

        // Back row of player1
        if (((Minion)(this.getHand().get(index))).getBoardRow().equals("back")
                && this.getIndex() == 1) {
            return 3;
        }

        // Back row of player2
        return 0;
    }

    /* Getters and Setters */
    public ArrayList<Deck> getPackDeck() {
        return packDeck;
    }
    public void setPackDeck(ArrayList<Deck> packDeck) {
        this.packDeck = packDeck;
    }
    public int getNrDecks() {
        return nrDecks;
    }
    public void setNrDecks(int nrDecks) {
        this.nrDecks = nrDecks;
    }
    public Deck getDeck() {
        return deck;
    }
    public void setDeck(Deck deck) {
        this.deck = deck;
    }
    public ArrayList<Card> getHand() {
        return hand;
    }
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }
    public int getWins() {
        return wins;
    }
    public void setWins(int wins) {
        this.wins = wins;
    }
    public Hero getHero() {
        return hero;
    }
    public void setHero(Hero hero) {
        this.hero = hero;
    }
    public int getMana() {
        return mana;
    }
    public void setMana(int mana) {
        this.mana = mana;
    }
    public void addMana(int mana) {
        this.mana += mana;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Player{" +
                "packDeck=" + packDeck + "\n" +
                ", nrDecks=" + nrDecks + "\n" +
                ", deck=" + deck +
                ", hand=" + hand +
                ", wins=" + wins +
                ", hero=" + hero +
                '}';
    }
}
