package gameplay;

import java.util.ArrayList;
import cards.Card;
import cards.environment.*;
import cards.minion.*;

/**
 * Contains the cards that form a deck.
 */
public class Deck {
    private ArrayList<Card> cardSet;
    private int nrCards;

    /* Constructor */
    public Deck(int nrCards) {
        this.cardSet = new ArrayList<>();
        this.nrCards = nrCards;
    }

    /**
     * Only need to copy Minion and Environment Cards.
     * Used only in deckDeepCopy() method, for the start of a mini-game.
     * @param card Card of type Minion or Environment
     * @return deep copy of the card
     */
    private Card cardDeepCopy(Card card) {
        // Create deep copies of common fields
        // String object is immutable, so there is no need to deep-copy it
        String copyDescription = card.getDescription();

        ArrayList<String> copyColors = new ArrayList<>();
        for (String color : card.getColors()) {
            copyColors.add(color);
        }

        String copyName = card.getName();

        return switch (card.getName()) {
            case "The Ripper" -> new TheRipper(card.getMana(), copyDescription, copyColors,
                    copyName, ((Minion) card).getHealth(), ((Minion) card).getAttack());
            case "Miraj" -> new Miraj(card.getMana(), copyDescription, copyColors,
                    copyName, ((Minion) card).getHealth(), ((Minion) card).getAttack());
            case "The Cursed One" -> new TheCursedOne(card.getMana(), copyDescription, copyColors,
                    copyName, ((Minion) card).getHealth(), ((Minion) card).getAttack());
            case "Disciple" -> new Disciple(card.getMana(), copyDescription, copyColors,
                    copyName, ((Minion) card).getHealth(), ((Minion) card).getAttack());
            case "Firestorm" -> new Firestorm(card.getMana(), copyDescription, copyColors, copyName);
            case "Winterfell" -> new Winterfell(card.getMana(), copyDescription, copyColors, copyName);
            case "Heart Hound" -> new HeartHound(card.getMana(), copyDescription, copyColors, copyName);
            default -> new Minion(card.getMana(), copyDescription, copyColors,
                    copyName, ((Minion) card).getHealth(), ((Minion) card).getAttack());
        };
    }

    /**
     * Creates a deep Copy for a deck from the player's packDeck.
     * The copy is of the self instance.
     * @return deep copy of the deck
     */
    public Deck deckDeepCopy() {
        Deck copyDeck = new Deck(this.nrCards);
        //copyDeck.cardSet = new ArrayList<>();

        // Deep copy each of the cards.
        for (Card card : this.getCardSet()) {
            Card copyCard = cardDeepCopy(card);
            copyDeck.cardSet.add(copyCard);
        }

        return copyDeck;
    }

    /* Getters and Setters */
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
}
