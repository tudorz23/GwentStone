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
import static utils.Constants.*;

public final class SuccessPrinter {
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Prints the Cards in the hand of the player.
     * @param player Player whose hand should be printed
     * @param output for printing in JSON format
     */
    public void printCardsInHand(final Player player, final ArrayNode output) {
        ObjectNode msg = mapper.createObjectNode();
        msg.put("command", "getCardsInHand");
        msg.put("playerIdx", player.getIndex());

        helperPrintCardArray(player.getHand(), msg);
        output.add(msg);
    }

    /**
     * Prints the Cards in the deck of the player.
     * @param player Player whose deck should be printed
     * @param output for printing in JSON format
     */
    public void printCardsInDeck(final Player player, final ArrayNode output) {
        ObjectNode msg = mapper.createObjectNode();
        msg.put("command", "getPlayerDeck");
        msg.put("playerIdx", player.getIndex());

        helperPrintCardArray(player.getDeck().getCardSet(), msg);
        output.add(msg);
    }

    /**
     * Prints the Cards from the table.
     * @param output for printing in JSON format
     */
    public void printCardsOnTable(final Board board, final ArrayNode output) {
        ObjectNode msg = mapper.createObjectNode();
        msg.put("command", "getCardsOnTable");

        ArrayNode rowArray = mapper.createArrayNode();
        for (int i = 0; i < ROWS; i++) {
            helperAppendToRowArray(board.row[i].elems, rowArray);
        }

        msg.set("output", rowArray);
        output.add(msg);
    }

    /**
     * Prints the player at turn.
     */
    public void printPlayerTurn(final int index, final ArrayNode output) {
        ObjectNode msg = mapper.createObjectNode();
        msg.put("command", "getPlayerTurn");
        msg.put("output", index);

        output.add(msg);
    }

    /**
     * Prints the player's Hero.
     */
    public void printPlayerHero(final Player player, final ArrayNode output) {
        ObjectNode msg = mapper.createObjectNode();
        msg.put("command", "getPlayerHero");
        msg.put("playerIdx", player.getIndex());
        helperCardPrinterJson(player.getHero(), msg);

        output.add(msg);
    }

    /**
     * Prints the card from the board at the position specified.
     */
    public void printCardAtPosition(final Board board, final int x, final int y,
                                    final ArrayNode output) {
        Card card = board.row[x].elems.get(y);

        ObjectNode msg = mapper.createObjectNode();
        msg.put("command", "getCardAtPosition");
        msg.put("x", x);
        msg.put("y", y);
        helperCardPrinterJson(card, msg);

        output.add(msg);
    }

    /**
     * Prints the player's mana.
     */
    public void printPlayerMana(final Player player, final ArrayNode output) {
         ObjectNode msg = mapper.createObjectNode();
         msg.put("command", "getPlayerMana");
         msg.put("playerIdx", player.getIndex());
         msg.put("output", player.getMana());

         output.add(msg);
    }

    /**
     * Prints the Environment cards that the @player has in his hand.
     */
    public void printEnvironmentCardInHand(final Player player, final ArrayNode output) {
        ObjectNode msg = mapper.createObjectNode();
        msg.put("command", "getEnvironmentCardsInHand");
        msg.put("playerIdx", player.getIndex());

        ArrayList<Card> environmentInHand = new ArrayList<>();
        for (Card card : player.getHand()) {
            if (card.getType() == ENVIRONMENT) {
                environmentInHand.add(card);
            }
        }
        helperPrintCardArray(environmentInHand, msg);
        output.add(msg);
    }

    /**
     * Prits the frozen cards from the board.
     */
    public void printFrozenCardsOnTable(final Board board, final ArrayNode output) {
        ObjectNode msg = mapper.createObjectNode();
        msg.put("command", "getFrozenCardsOnTable");

        ArrayList<Card> frozenCards = new ArrayList<>();
        for (int i = 0; i < ROWS; i++) {
            for (Minion card : board.row[i].elems) {
                // We know for sure Card is a Minion (since it is on the table)
                if (card.isFrozen()) {
                    frozenCards.add(card);
                }
            }
        }

        helperPrintCardArray(frozenCards, msg);
        output.add(msg);
    }

    /**
     * Prints the winning player.
     */
    public void printWinner(final int winnerIdx, final ArrayNode output) {
        ObjectNode msg = mapper.createObjectNode();

        if (winnerIdx == 1) {
            msg.put("gameEnded", "Player one killed the enemy hero.");
        } else {
            msg.put("gameEnded", "Player two killed the enemy hero.");
        }

        output.add(msg);
    }

    /**
     * Prints the total number of games played at that moment.
     */
    public void printTotalGamesPlayed(final int gamesPlayed, final ArrayNode output) {
        ObjectNode msg = mapper.createObjectNode();

        msg.put("command", "getTotalGamesPlayed");
        msg.put("output", gamesPlayed);

        output.add(msg);
    }

    /**
     * Prints the number of wins of the @player.
     */
    public void printPlayerWins(final Player player, final ArrayNode output) {
        ObjectNode msg = mapper.createObjectNode();

        if (player.getIndex() == 1) {
            msg.put("command", "getPlayerOneWins");
        } else {
            msg.put("command", "getPlayerTwoWins");
        }

        msg.put("output", player.getWins());

        output.add(msg);
    }

    /* Helpers for printing Cards in JSON format. */
    /**
     * Prints an ArrayList of Cards in JSON format. Cards are of Minion and Environment
     * types (the Hero card is never placed with other cards).
     * @param cards the card list that should be printed
     * @param message the whole object that should be written for the respective command
     */
    public void helperPrintCardArray(final ArrayList<Card> cards, final ObjectNode message) {
        // The array of cards in JSON format
        ArrayNode cardListPrint = mapper.createArrayNode();

        // Iterate through the array
        for (Card card : cards) {
            ObjectNode printCard = mapper.createObjectNode();

            printCard.put("mana", card.getMana());

            if (card.getType() == MINION) {
                // Minion Card
                printCard.put("attackDamage", ((Minion) card).getAttack());
                printCard.put("health", ((Minion) card).getHealth());
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
     * Appends a row from the board to the rowArray.
     * Helper for printing the Cards from the board.
     * @param minions the card list that should be appended
     * @param rowArray ArrayNode of the current row from the board
     */
    public void helperAppendToRowArray(final ArrayList<Minion> minions, final ArrayNode rowArray) {
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

        // Add the Card array (representing a row) to the row array
        rowArray.add(cardListPrint);
    }

    /**
     * General Card printer in JSON format function.
     * Appends to 'message' the Card in JSON format.
     * @param card Card to print
     * @param message ObjectNode that the message should be appended to
     */
    public void helperCardPrinterJson(final Card card, final ObjectNode message) {
        ObjectNode cardPrint = mapper.createObjectNode();
        cardPrint.put("mana", card.getMana());

        if (card.getType() == MINION) {
            // Minion Card
            cardPrint.put("attackDamage", ((Minion) card).getAttack());
            cardPrint.put("health", ((Minion) card).getHealth());
        }

        cardPrint.put("description", card.getDescription());

        // Print the colors string array
        ArrayNode colorPrint = mapper.createArrayNode();
        for (String color : card.getColors()) {
            colorPrint.add(color);
        }
        cardPrint.set("colors", colorPrint);
        cardPrint.put("name", card.getName());

        if (card.getType() == HERO) {
            // Hero Card
            cardPrint.put("health", ((Hero) card).getHealth());
        }

        message.set("output", cardPrint);
    }
}
