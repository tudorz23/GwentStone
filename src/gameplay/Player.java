package gameplay;

import cards.Card;
import cards.hero.Hero;
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

    /* Constructor */
    public Player() {
        this.wins = 0;
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
