package utils;

import cards.Card;
import cards.environment.*;
import cards.hero.*;
import cards.minion.*;
import fileio.CardInput;

public class Converter {
    /**
     * Converts CardInput type to Card type
     * @param cardInput Standard input from the JSON file.
     * @return Card object (instance of Minion or Environment)
     */
    public Card convertCard(CardInput cardInput) {
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
}
