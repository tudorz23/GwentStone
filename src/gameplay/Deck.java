package gameplay;

import java.util.ArrayList;
import cards.Card;

/**
 * Contains the cards that form a deck.
 */
public class Deck {
    private ArrayList<Card> cardSet;
    private int nrCards;

    public Deck(int nrCards) {
        this.cardSet = new ArrayList<>();
        this.nrCards = nrCards;
    }

    public void setCardSet(ArrayList<Card> cardSet) {
        this.cardSet = cardSet;
    }
    public ArrayList<Card> getCardSet() {
        return cardSet;
    }
    public int getNrCards() {
        return nrCards;
    }
    public void setNrCards(int nrCards) {
        this.nrCards = nrCards;
    }

    @Override
    public String toString() {
        return "Deck{" +
                "cardSet=" + cardSet +
                ", nrCards=" + nrCards +
                '}';
    }
}
