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
     * Helper to errorPlaceCard() method
     * @param message the specific error
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

    private void printErrorGetCardAtPosition(int x, int y, ArrayNode output) {
        ObjectNode msg = mapper.createObjectNode();
        msg.put("command", "getCardAtPosition");
        msg.put("x", x);
        msg.put("y", y);
        msg.put("output", "No card available at that position.");
    }
}
