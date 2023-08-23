package printers;

import cards.hero.Hero;
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

        output.add(msg);
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

    /**
     * Checks for errors during cardUsesAttack() operation.
     * @return true if there is an error, false if there is not
     */
    public boolean errorCardUsesAttack(Player player, Board board, int attackerX, int attackerY,
                                       int attackedX, int attackedY, ArrayNode output) {
        if ((player.getIndex() == 1 && (attackedX == 2 || attackedX == 3))
            || (player.getIndex() == 2 && (attackedX == 0 || attackedX == 1))) {
            printErrorCardUsesAttack(output, attackerX, attackerY, attackedX, attackedY,
                                    "Attacked card does not belong to the enemy.");
            return true;
        }

        if (board.row[attackerX].elems.get(attackerY).getUsedTurn()) {
            printErrorCardUsesAttack(output, attackerX, attackerY, attackedX, attackedY,
                                    "Attacker card has already attacked this turn.");
            return true;
        }

        if (board.row[attackerX].elems.get(attackerY).isFrozen()) {
            printErrorCardUsesAttack(output, attackerX, attackerY, attackedX, attackedY,
                                    "Attacker card is frozen.");
            return true;
        }

        // Check if enemy has tanks on the front row.
        int enemyFrontRow;
        if (player.getIndex() == 1) {
            enemyFrontRow = 1;
        } else {
            enemyFrontRow = 2;
        }

        if (board.row[enemyFrontRow].hasTank() && !board.row[attackedX].elems.get(attackedY).isTank()) {
            printErrorCardUsesAttack(output, attackerX, attackerY, attackedX, attackedY,
                                    "Attacked card is not of type 'Tank'.");
            return true;
        }

        return false;
    }

    /**
     * Helper to errorCardUsesAttack() method.
     * @param message the error message
     */
    private void printErrorCardUsesAttack(ArrayNode output, int attackerX, int attackerY,
                                       int attackedX, int attackedY, String message) {
        ObjectNode msg = mapper.createObjectNode();
        msg.put("command", "cardUsesAttack");

        ObjectNode cardAttacker = mapper.createObjectNode();
        cardAttacker.put("x", attackerX);
        cardAttacker.put("y", attackerY);
        msg.set("cardAttacker", cardAttacker);

        ObjectNode cardAttacked = mapper.createObjectNode();
        cardAttacked.put("x", attackedX);
        cardAttacked.put("y", attackedY);
        msg.set("cardAttacked", cardAttacked);

        msg.put("error", message);

        output.add(msg);
    }

    /**
     * Checks for errors during cardUsesAbility() operation.
     * @return true if there is an error, false if there is not
     */
    public boolean errorCardUsesAbility(Player player, Board board, int attackerX, int attackerY,
                                       int attackedX, int attackedY, ArrayNode output) {
        if (board.row[attackerX].elems.get(attackerY).isFrozen()) {
            printErrorCardUsesAbility(output, attackerX, attackerY, attackedX, attackedY,
                                    "Attacker card is frozen.");
            return true;
        }

        if (board.row[attackerX].elems.get(attackerY).getUsedTurn()) {
            printErrorCardUsesAbility(output, attackerX, attackerY, attackedX, attackedY,
                                    "Attacker card has already attacked this turn.");
            return true;
        }

        Minion attackerCard = board.row[attackerX].elems.get(attackerY);
        Minion attackedCard = board.row[attackedX].elems.get(attackedY);

        if (attackerCard.getName().equals("Disciple")) {
            if ((player.getIndex() == 1 && (attackedX == 0 || attackedX == 1))
                    || (player.getIndex() == 2 && (attackedX == 2 || attackedX == 3))) {
                printErrorCardUsesAbility(output, attackerX, attackerY, attackedX, attackedY,
                                    "Attacked card does not belong to the current player.");
                return true;
            }
        }

        if (attackerCard.getName().equals("The Ripper") || attackerCard.getName().equals("Miraj")
            || attackerCard.getName().equals("The Cursed One")) {
            if ((player.getIndex() == 1 && (attackedX == 2 || attackedX == 3))
                    || (player.getIndex() == 2 && (attackedX == 0 || attackedX == 1))) {
                printErrorCardUsesAbility(output, attackerX, attackerY, attackedX, attackedY,
                                    "Attacked card does not belong to the enemy.");
                return true;
            }

            // Check if enemy has tanks on the front row.
            int enemyFrontRow;
            if (player.getIndex() == 1) {
                enemyFrontRow = 1;
            } else {
                enemyFrontRow = 2;
            }

            if (board.row[enemyFrontRow].hasTank() && !attackedCard.isTank()) {
                printErrorCardUsesAbility(output, attackerX, attackerY, attackedX, attackedY,
                        "Attacked card is not of type 'Tank'.");
                return true;
            }
        }

        return false;
    }

    /**
     * Helper to errorCardUsesAbility() method
     * @param message the error message
     */
    private void printErrorCardUsesAbility(ArrayNode output, int attackerX, int attackerY,
                                       int attackedX, int attackedY, String message) {
        ObjectNode msg = mapper.createObjectNode();
        msg.put("command", "cardUsesAbility");

        ObjectNode cardAttacker = mapper.createObjectNode();
        cardAttacker.put("x", attackerX);
        cardAttacker.put("y", attackerY);
        msg.set("cardAttacker", cardAttacker);

        ObjectNode cardAttacked = mapper.createObjectNode();
        cardAttacked.put("x", attackedX);
        cardAttacked.put("y", attackedY);
        msg.set("cardAttacked", cardAttacked);

        msg.put("error", message);

        output.add(msg);
    }

    /**
     * Checks for errors during useAttackHero() operation.
     * @return true if there is an error, false if there is not
     */
    public boolean errorUseAttackHero(Player player, Board board, Hero enemyHero,
                                      int attackerX, int attackerY, ArrayNode output) {
        Minion attackerCard = board.row[attackerX].elems.get(attackerY);

        if (attackerCard.isFrozen()) {
            printErrorUseAttackHero(output, attackerX, attackerY, "Attacker card is frozen.");
            return true;
        }

        if (attackerCard.getUsedTurn()) {
            printErrorUseAttackHero(output, attackerX, attackerY,
                            "Attacker card has already attacked this turn.");
            return true;
        }

        // Check if enemy has tanks on the front row.
        int enemyFrontRow;
        if (player.getIndex() == 1) {
            enemyFrontRow = 1;
        } else {
            enemyFrontRow = 2;
        }

        if (board.row[enemyFrontRow].hasTank()) {
            printErrorUseAttackHero(output, attackerX, attackerY, "Attacked card is not of type 'Tank'.");
            return true;
        }

        return false;
    }

    /**
     * Helper to errorUseAttackHero() method
     * @param message the error message
     */
    private void printErrorUseAttackHero(ArrayNode output, int attackerX, int attackerY, String message) {
        ObjectNode msg = mapper.createObjectNode();
        msg.put("command", "useAttackHero");

        ObjectNode cardAttacker = mapper.createObjectNode();
        cardAttacker.put("x", attackerX);
        cardAttacker.put("y", attackerY);
        msg.set("cardAttacker", cardAttacker);

        msg.put("error", message);

        output.add(msg);
    }

    /**
     * Checks for errors during useHeroAbility() operation.
     * @return true if there is an error, false if there is not
     */
    public boolean errorUseHeroAbility(Player player, Board board, int affectedRow,  ArrayNode output) {
        Hero hero = player.getHero();

        if (player.getMana() < hero.getMana()) {
            printErrorUseHeroAbility(output, affectedRow, "Not enough mana to use hero's ability.");
            return true;
        }

        if (hero.getUsedTurn()) {
            printErrorUseHeroAbility(output, affectedRow, "Hero has already attacked this turn.");
            return true;
        }

        if (hero.getName().equals("Lord Royce") || hero.getName().equals("Empress Thorina")) {
            if ((player.getIndex() == 1 && (affectedRow == 2 || affectedRow == 3))
                || (player.getIndex() == 2 && (affectedRow == 0 || affectedRow == 1))) {
                printErrorUseHeroAbility(output, affectedRow, "Selected row does not belong to the enemy.");
                return true;
            }
        }

        if (hero.getName().equals("General Kocioraw") || hero.getName().equals("King Mudface")) {
            if ((player.getIndex() == 1 && (affectedRow == 0 || affectedRow == 1))
                || (player.getIndex() == 2 && (affectedRow == 2 || affectedRow == 3))) {
                printErrorUseHeroAbility(output, affectedRow,
                                "Selected row does not belong to the current player.");
                return true;
            }
        }

        return false;
    }

    /**
     * Helper to errorUseHeroAbility() method
     * @param message the error message
     */
    private void printErrorUseHeroAbility(ArrayNode output, int affectedRow, String message) {
        ObjectNode msg = mapper.createObjectNode();
        msg.put("command", "useHeroAbility");
        msg.put("affectedRow", affectedRow);
        msg.put("error", message);

        output.add(msg);
    }
}
