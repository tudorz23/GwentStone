package printers;

import cards.minion.Minion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gameplay.Board;
import gameplay.Player;

import static utils.Constants.MAX_ROW_SIZE;

public class ErrorPrinter {
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Checks for errors during placeCard() operation
     * @return false if there is no error, true if there is an error
     * @param currPlayer player who is at turn
     * @param board game's playing board
     * @param index position of the card in player's hand
     * @param output for printing in JSON format
     */
    public boolean errorPlaceCard(Player currPlayer, Board board, int index, ArrayNode output) {
        if (currPlayer.getHand().get(index).getType() == 4) {
            printErrorPlaceCard(output, index, "Cannot place environment card on table.");
            return true;
        }

        if (currPlayer.getHand().get(index).getMana() > currPlayer.getMana()) {
            printErrorPlaceCard(output, index, "Not enough mana to place card on table.");
            return true;
        }

        // We know for sure the Card is a Minion
        int rowToCheck = currPlayer.getRowToPlace(index);

        // Check whether the row where it should be placed is full
        if (board.row[rowToCheck].elems.size() == MAX_ROW_SIZE) {
            printErrorPlaceCard(output, index, "Cannot place card on table since row is full.");
            return true;
        }

        return false;
    }

    /**
     * Helper to errorPlaceCard() method.
     * @param message the error message
     */
    private void printErrorPlaceCard(ArrayNode output, int index, String message) {
        ObjectNode msg = mapper.createObjectNode();
        msg.put("command", "placeCard");
        msg.put("handIdx", index);
        msg.put("error", message);

        output.add(msg);
    }

    /**
     * @return true if there is an error, false if there is no error
     */
    public boolean errorGetCardAtPosition(Board board, int x, int y, ArrayNode output) {
        if (board.row[x].elems.size() <= y) {
            printErrorGetCardAtPosition(x, y, output);
            return true;
        }
        return false;
    }

    /**
     * Helper to errorGetCardAtPosition() method.
     */
    private void printErrorGetCardAtPosition(int x, int y, ArrayNode output) {
        ObjectNode msg = mapper.createObjectNode();
        msg.put("command", "getCardAtPosition");
        msg.put("x", x);
        msg.put("y", y);
        msg.put("output", "No card available at that position.");
    }

    /**
     * Checks for errors during useEnvironmentCard() operation.
     * @return true if there is an error, false if there is not
     */
    public boolean errorUseEnvironmentCard(Player player, Board board, int handIdx, int affectedRow,
                                           ArrayNode output) {
        if (player.getHand().get(handIdx).getType() != 4) {
            printErrorUseEnvironmentCard(output, handIdx, affectedRow,
                    "Chosen card is not of type environment.");
            return true;
        }

        if (player.getHand().get(handIdx).getMana() > player.getMana()) {
            printErrorUseEnvironmentCard(output, handIdx, affectedRow,
                    "Not enough mana to use environment card.");
            return true;
        }

        if ((player.getIndex() == 1 && (affectedRow != 0 && affectedRow != 1)) ||
                (player.getIndex() == 2 && (affectedRow != 2 && affectedRow != 3))) {
            printErrorUseEnvironmentCard(output, handIdx, affectedRow,
                    "Chosen row does not belong to the enemy.");
            return true;
        }

        if (player.getHand().get(handIdx).getName().equals("Heart Hound")) {
            // Check if the mirrored row is full
            int mirroredRow = 3 - affectedRow;
            if (board.row[mirroredRow].isFull()) {
                printErrorUseEnvironmentCard(output, handIdx, affectedRow,
                        "Cannot steal enemy card since the player's row is full.");
                return true;
            }
        }

        return false;
    }

    /**
     * Helper to errorUseEnvironmentCard() method.
     * @param message the error message
     */
    private void printErrorUseEnvironmentCard(ArrayNode output, int handIdx, int affectedRow, String message) {
        ObjectNode msg = mapper.createObjectNode();
        msg.put("command", "useEnvironmentCard");
        msg.put("handIdx", handIdx);
        msg.put("affectedRow", affectedRow);
        msg.put("error", message);

        output.add(msg);
    }
}
