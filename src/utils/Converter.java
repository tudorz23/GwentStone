package utils;

import cards.Card;
import cards.environment.*;
import cards.hero.*;
import cards.minion.*;
import fileio.CardInput;
import fileio.DecksInput;
import gameplay.Deck;
import java.util.ArrayList;

public class Converter {
    /**
     * Converts CardInput type to Card type.
     * Private because it is only used in convertDeck() method.
     * @param cardInput Standard input from the JSON file.
     * @return Card object (instance of Minion or Environment)
     */
    private Card convertCard(CardInput cardInput) {
        return switch (cardInput.getName()) {
            case "Sentinel", "Berserker", "Goliath", "Warden" ->
                    new Minion(cardInput.getMana(), cardInput.getDescription(),
                            cardInput.getColors(), cardInput.getName(), cardInput.getHealth(),
                            cardInput.getAttackDamage());
            case "The Ripper" -> new TheRipper(cardInput.getMana(), cardInput.getDescription(),
                    cardInput.getColors(), cardInput.getName(), cardInput.getHealth(),
                    cardInput.getAttackDamage());
            case "Miraj" -> new Miraj(cardInput.getMana(), cardInput.getDescription(),
                    cardInput.getColors(), cardInput.getName(), cardInput.getHealth(),
                    cardInput.getAttackDamage());
            case "The Cursed One" -> new TheCursedOne(cardInput.getMana(), cardInput.getDescription(),
                    cardInput.getColors(), cardInput.getName(), cardInput.getHealth(),
                    cardInput.getAttackDamage());
            case "Disciple" -> new Disciple(cardInput.getMana(), cardInput.getDescription(),
                    cardInput.getColors(), cardInput.getName(), cardInput.getHealth(),
                    cardInput.getAttackDamage());
            case "Firestorm" -> new Firestorm(cardInput.getMana(), cardInput.getDescription(),
                    cardInput.getColors(), cardInput.getName());
            case "Winterfell" -> new Winterfell(cardInput.getMana(), cardInput.getDescription(),
                    cardInput.getColors(), cardInput.getName());
            default -> new HeartHound(cardInput.getMana(), cardInput.getDescription(),
                    cardInput.getColors(), cardInput.getName());
        };
    }

    /**
     * Converts CardInput type to Hero type
     * @param cardInput Standard input from the JSON file.
     * @return Hero object (instance of a specific hero)
     */
    public Hero convertHero(CardInput cardInput) {
        return switch (cardInput.getName()) {
            case "Lord Royce" -> new LordRoyce(cardInput.getMana(), cardInput.getDescription(),
                    cardInput.getColors(), cardInput.getName());
            case "Empress Thorina" -> new EmpressThorina(cardInput.getMana(), cardInput.getDescription(),
                    cardInput.getColors(), cardInput.getName());
            case "King Mudface" -> new KingMudface(cardInput.getMana(), cardInput.getDescription(),
                    cardInput.getColors(), cardInput.getName());
            default -> new GeneralKocioraw(cardInput.getMana(), cardInput.getDescription(),
                    cardInput.getColors(), cardInput.getName());
        };
    }

    /**
     * Converts the array of decks from JSON input to an array of decks
     * containing Card objects.
     * @param decksInput Set of decks from JSON input.
     * @return ArrayList of Deck classes to be stored in Player.packDeck.
     */
    public ArrayList<Deck> convertDeck(DecksInput decksInput) {
        // The Array of Decks to be returned
        ArrayList<Deck> packDeck = new ArrayList<>();

        // Iterate through the input array of decks
        for (ArrayList<CardInput> cards : decksInput.getDecks()) {
            Deck deck = new Deck(decksInput.getNrCardsInDeck());
            ArrayList<Card> cardSet = new ArrayList<>();

            // Iterate through the cards of the input deck
            for (CardInput cardInput : cards) {
                Card toAddCard = convertCard(cardInput);
                cardSet.add(toAddCard);
            }

            deck.setCardSet(cardSet);
            packDeck.add(deck);
        }
        return packDeck;
    }
}
