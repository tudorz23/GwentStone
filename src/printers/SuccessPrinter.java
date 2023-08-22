package printers;

import cards.Card;
import cards.hero.Hero;
import cards.minion.Minion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gameplay.Board;
import gameplay.Player;

import java.util.ArrayList;

import static utils.Constants.ROWS;

public class SuccessPrinter {
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Prints the Cards in the hand of the player.
     * @param player Player whose hand should be printed
     * @param output for printing in JSON format
     */
    public void printCardsInHand(Player player, ArrayNode output) {
        ObjectNode msg = mapper.createObjectNode();
        msg.put("command", "getCardsInHand");
        msg.put("playerIdx", player.getIndex());

        printCardArray(player.getHand(), msg);
        output.add(msg);
    }

    /**
     * Prints the Cards in the deck of the player.
     * @param player Player whose deck should be printed
     * @param output for printing in JSON format
     */
    public void printCardsInDeck(Player player, ArrayNode output) {
        ObjectNode msg = mapper.createObjectNode();
        msg.put("command", "getPlayerDeck");
        msg.put("playerIdx", player.getIndex());

        printCardArray(player.getDeck().getCardSet(), msg);
        output.add(msg);
    }

    /**
     * Prints the Cards from the table.
     * @param output for printing in JSON format
     */
    public void printCardsOnTable(Board board, ArrayNode output) {
        ObjectNode msg = mapper.createObjectNode();
        msg.put("command", "getCardsOnTable");

        ArrayNode bigArray = mapper.createArrayNode();
        for (int i = 0; i < ROWS; i++) {
            printMinionArray(board.row[i].elems, bigArray);
        }

        msg.set("output", bigArray);
        output.add(msg);
    }

    /**
     * Prints the player at turn in JSON format.
     */
    public void printPlayerTurn(int index, ArrayNode output) {
        ObjectNode msg = mapper.createObjectNode();
        msg.put("command", "getPlayerTurn");
        msg.put("output", index);

        output.add(msg);
    }

    /**
     * Prints the player's Hero.
     */
    public void printPlayerHero(Player player, ArrayNode output) {
        ObjectNode msg = mapper.createObjectNode();
        msg.put("command", "getPlayerHero");
        msg.put("playerIdx", player.getIndex());

        printHeroJson(player.getHero(), msg);

        output.add(msg);
    }

    /**
     * Prints an ArrayList of Card in JSON format
     * @param cards the card list that should be printed
     * @param message the whole object that should be written for the respective command
     */
    public void printCardArray(ArrayList<Card> cards, ObjectNode message) {
        // The array of cards in JSON format
        ArrayNode cardListPrint = mapper.createArrayNode();

        // Iterate through the array
        for (Card card : cards) {
            ObjectNode printCard = mapper.createObjectNode();

            printCard.put("mana", card.getMana());

            if (card.getType() == 3) {
                // Minion Card
                printCard.put("attackDamage", ((Minion)card).getAttack());
                printCard.put("health", ((Minion)card).getHealth());
            }

            printCard.put("description", card.getDescription());

            // Print the colors string array
            ArrayNode colorPrint = mapper.createArrayNode();
            for (String color : card.getColors()) {
                colorPrint.add(color);
            }

            printCard.set("colors", colorPrint);
            printCard.put("name", card.getName());

            cardListPrint.add(printCard);
        }

        message.set("output", cardListPrint);
    }

    /**
     * Prints an ArrayList of Minion in JSON format
     * @param minions the card list that should be printed
     * @param message the whole object that should be written for the respective command
     */
    public void printMinionArray(ArrayList<Minion> minions, ArrayNode bigArray) {
        // The array of cards in JSON format
        ArrayNode cardListPrint = mapper.createArrayNode();

        // Iterate through the array
        for (Minion minion : minions) {
            ObjectNode printCard = mapper.createObjectNode();

            printCard.put("mana", minion.getMana());
            printCard.put("attackDamage", minion.getAttack());
            printCard.put("health", minion.getHealth());
            printCard.put("description", minion.getDescription());

            // Print the colors string array
            ArrayNode colorPrint = mapper.createArrayNode();
            for (String color : minion.getColors()) {
                colorPrint.add(color);
            }

            printCard.set("colors", colorPrint);
            printCard.put("name", minion.getName());

            cardListPrint.add(printCard);
        }

        bigArray.add(cardListPrint);
    }

    /**
     * Prints a Hero to JSON.
     */
    public void printHeroJson(Hero hero, ObjectNode message) {
        ObjectNode printHero = mapper.createObjectNode();

        printHero.put("mana", hero.getMana());

        printHero.put("description", hero.getDescription());

        // Print the colors string array
        ArrayNode colorPrint = mapper.createArrayNode();
        for (String color : hero.getColors()) {
            colorPrint.add(color);
        }

        printHero.set("colors", colorPrint);
        printHero.put("name", hero.getName());
        printHero.put("health", hero.getHealth());

        message.set("output", printHero);
    }
}
