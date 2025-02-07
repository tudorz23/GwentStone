package gameplay;

import cards.environment.Environment;
import cards.hero.Hero;
import cards.minion.Minion;
import cards.minion.SpecialMinion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.GameInput;
import fileio.Input;
import fileio.StartGameInput;
import printers.ErrorPrinter;
import printers.SuccessPrinter;
import utils.Converter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public final class Game {
    private Player player1;
    private Player player2;
    private Player currPlayer; // reference to the player at turn
    private Board board;
    private int round; // number of the current round
    private int turnChanges; // when it becomes 2, a new round begins
    public Input input;
    public ArrayNode output;
    private ErrorPrinter errorPrinter = new ErrorPrinter();
    private SuccessPrinter successPrinter = new SuccessPrinter();
    private int totalGames;


    /* Constructor */
    public Game(Input input) {
        this.player1 = new Player(1);
        this.player2 = new Player(2);
        this.input = input;
        this.output = (new ObjectMapper()).createArrayNode();
        this.totalGames = 0;
    }

    /**
     * Completes the packDeck and nrDecks fields for both Player objects.
     */
    private void parsePlayerDecks() {
        // Convert the input from the JSON file.
        Converter converter = new Converter();
        player1.setPackDeck(converter.convertDeck(input.getPlayerOneDecks()));
        player2.setPackDeck(converter.convertDeck(input.getPlayerTwoDecks()));

        player1.setNrDecks(input.getPlayerOneDecks().getNrDecks());
        player2.setNrDecks(input.getPlayerTwoDecks().getNrDecks());
    }

    /**
     * Sets the current deck for each player.
     * Sets the Hero for each player.
     * Selects the shuffleSeed.
     * Initializes players' hands.
     * Sets currPlayer to the starting player.
     * Initializes players' mana.
     * @param miniGame returned from a loop that iterates through all the mini-games
     */
    private void prepareMiniGame(StartGameInput miniGame) {
        // Set the shuffleSeed (changes from one mini-game to the other)
        int shuffleSeed = miniGame.getShuffleSeed();

        // Set the heroes
        Converter converter = new Converter();
        player1.setHero(converter.convertHero(miniGame.getPlayerOneHero()));
        player2.setHero(converter.convertHero(miniGame.getPlayerTwoHero()));

        // Set the chosen deck to the deck from index
        // "miniGame.getPlayerOneDeckIdx()" of player's packDeck
        player1.setDeck(player1.getPackDeck().get(miniGame.getPlayerOneDeckIdx()).deckDeepCopy());
        player2.setDeck(player2.getPackDeck().get(miniGame.getPlayerTwoDeckIdx()).deckDeepCopy());

        // Shuffle the deck of each player, using the shuffleSeed
        Collections.shuffle(player1.getDeck().getCardSet(), new Random(shuffleSeed));
        Collections.shuffle(player2.getDeck().getCardSet(), new Random(shuffleSeed));

        // Initialize hands
        player1.setHand(new ArrayList<>());
        player2.setHand(new ArrayList<>());

        // Set starting player
        if (miniGame.getStartingPlayer() == 1) {
            currPlayer = player1;
        } else {
            currPlayer = player2;
        }

        // Initialize players' mana
        player1.setMana(0);
        player2.setMana(0);

        // Initialize the game board
        board = new Board();

        //Initialize the round
        round = 0;
    }

    /**
     * This shall be the starting point of the game.
     */
    public void startGame() {
        // Players get their decks
        parsePlayerDecks();

        // Play the games
        for (GameInput game : input.getGames()) {
            // Make the initial settings to start a mini-game
            prepareMiniGame(game.getStartGame());
            totalGames++;
            playGame(game);
        }
    }

    /**
     * Controls the Actual gameplay.
     * @param game Current game of the loop from startGame() method
     */
    private void playGame(GameInput game) {
        // Start the first round
        changeRound();

        // Iterate through the actions
        for (ActionsInput action : game.getActions()) {
            switch (action.getCommand()) {
                case "endPlayerTurn" -> changeTurn();
                case "placeCard" -> placeCard(action.getHandIdx());
                case "getCardsInHand" -> getCardsInHand(action.getPlayerIdx());
                case "getPlayerDeck" -> getPlayerDeck(action.getPlayerIdx());
                case "getCardsOnTable" -> getCardsOnTable();
                case "getPlayerTurn" -> getPlayerTurn();
                case "getPlayerHero" -> getPlayerHero(action.getPlayerIdx());
                case "getCardAtPosition" -> getCardAtPosition(action.getX(), action.getY());
                case "getPlayerMana" -> getPlayerMana(action.getPlayerIdx());
                case "getEnvironmentCardsInHand" -> getEnvironmentCardsInHand(action.getPlayerIdx());
                case "getFrozenCardsOnTable" -> getFrozenCardsOnTable();
                case "useEnvironmentCard" -> useEnvironmentCard(action.getHandIdx(), action.getAffectedRow());
                case "cardUsesAttack" -> cardUsesAttack(action.getCardAttacker().getX(),
                        action.getCardAttacker().getY(), action.getCardAttacked().getX(),
                        action.getCardAttacked().getY());
                case "cardUsesAbility" -> cardUsesAbility(action.getCardAttacker().getX(),
                        action.getCardAttacker().getY(), action.getCardAttacked().getX(),
                        action.getCardAttacked().getY());
                case "useAttackHero" -> useAttackHero(action.getCardAttacker().getX(),
                        action.getCardAttacker().getY());
                case "useHeroAbility" -> useHeroAbility(action.getAffectedRow());
                case "getTotalGamesPlayed" -> getTotalGamesPlayed();
                case "getPlayerOneWins" -> getPlayerOneWins();
                case "getPlayerTwoWins" -> getPlayerTwoWins();
            }
        }
    }

    /**
     * Makes the necessary changes when one of the players' turn is over.
     */
    private void changeTurn() {
        // Unfreeze the frozen Cards of currPlayer and set Used turn to false
        if (currPlayer == player1) {
            // Unfreeze player1's frozen Cards (rows 2 and 3 on the board)
            for (int i = 2; i <= 3; i++) {
                for (Minion minion : board.row[i].elems) {
                    if (minion.isFrozen()) {
                        minion.setFrozen(false);
                    }
                    minion.setUsedTurn(false);
                }
            }
        } else {
            // Unfreeze player2's frozen Cards (rows 0 and 1 on the board)
            for (int i = 0; i <= 1; i++) {
                for (Minion minion : board.row[i].elems) {
                    if (minion.isFrozen()) {
                        minion.setFrozen(false);
                    }
                    minion.setUsedTurn(false);
                }
            }
        }
        // set Used turn for Hero to false
        currPlayer.getHero().setUsedTurn(false);

        // Change currPlayer
        if (currPlayer == player1) {
            currPlayer = player2;
        } else {
            currPlayer = player1;
        }

        turnChanges++;
        // Check if both players took their turns during the current round
        if (turnChanges == 2) {
            changeRound();
            turnChanges = 0;
        }
    }

    /**
     * When turnChanges is equal to 2, a new round should begin.
     * In every round, both players take a new Card in their hand from their deck
     * and gain as much mana as the round number.
     */
    private void changeRound() {
        round++;
        // Add the first card from the deck to the player's hand
        if (!player1.getDeck().getCardSet().isEmpty()) {
            player1.getHand().add(player1.getDeck().getCardSet().remove(0));
        }

        if (!player2.getDeck().getCardSet().isEmpty()) {
            player2.getHand().add(player2.getDeck().getCardSet().remove(0));
        }

        // Mana gaining
        if (round < 10) {
            player1.addMana(round);
            player2.addMana(round);
        } else {
            // From round 10 onwards, players only gain 10 mana
            player1.addMana(10);
            player2.addMana(10);
        }
    }

    /* *** In-game actions begin here. *** */
    /**
     * Places a Card from player's hand on the board.
     * @param index position of the card in player's hand
     */
    private void placeCard(int index) {
        if (errorPrinter.errorPlaceCard(currPlayer, board, index, output)) {
            return;
        }
        // Row where the card should be placed
        int rowIndex = currPlayer.getRowToPlace(index);

        // Spend player's mana for the operation
        currPlayer.decreaseMana(currPlayer.getHand().get(index).getMana());

        // Place the Minion on the board
        board.row[rowIndex].elems.add((Minion) (currPlayer.getHand().remove(index)));
    }

    /**
     * Implements the usage of an Environment Card.
     * @param handIdx position of the card in player's hand
     * @param affectedRow board's row that will be affected by the Environment Card
     */
    private void useEnvironmentCard(int handIdx, int affectedRow) {
        if (errorPrinter.errorUseEnvironmentCard(currPlayer, board, handIdx, affectedRow, output)) {
            return;
        }
        // Spend player mana
        currPlayer.decreaseMana(currPlayer.getHand().get(handIdx).getMana());

        // Use ability
        ((Environment) currPlayer.getHand().get(handIdx)).useAbility(board, affectedRow);

        // Remove the card from the player's hand
        currPlayer.getHand().remove(handIdx);
    }

    /**
     * Implements the attack of a Card.
     */
    private void cardUsesAttack(int attackerX, int attackerY, int attackedX, int attackedY) {
        if (errorPrinter.errorCardUsesAttack(currPlayer, board, attackerX, attackerY,
                                            attackedX, attackedY, output)) {
            return;
        }

        Minion attackerCard = board.row[attackerX].elems.get(attackerY);
        Minion attackedCard = board.row[attackedX].elems.get(attackedY);

        // Decrease attacked Card's health
        attackedCard.setHealth(attackedCard.getHealth() - attackerCard.getAttack());

        // Check if attacked Card is dead
        if (attackedCard.getHealth() <= 0) {
            board.row[attackedX].elems.remove(attackedCard);
        }

        // Mark attacker Card as used during this turn
        attackerCard.setUsedTurn(true);
    }

    /**
     * Implements the usage of ability of a Card (Special Minion).
     */
    private void cardUsesAbility(int attackerX, int attackerY, int attackedX, int attackedY) {
        if (errorPrinter.errorCardUsesAbility(currPlayer, board, attackerX, attackerY,
                                            attackedX, attackedY, output)) {
            return;
        }

        Minion attackerCard = board.row[attackerX].elems.get(attackerY);
        Minion attackedCard = board.row[attackedX].elems.get(attackedY);

        ((SpecialMinion) attackerCard).useAbility(attackedCard);

        // Check if attacked minion is dead
        if (attackedCard.getHealth() <= 0) {
            board.row[attackedX].elems.remove(attackedCard);
        }

        // Mark attacker Card as used during this turn
        attackerCard.setUsedTurn(true);
    }

    /**
     * Implements the attack on the enemy's Hero
     */
    private void useAttackHero(int attackerX, int attackerY) {
        Hero enemyHero;
        if (currPlayer.getIndex() == 1) {
            enemyHero = player2.getHero();
        } else {
            enemyHero = player1.getHero();
        }

        if (errorPrinter.errorUseAttackHero(currPlayer, board, attackerX, attackerY, output)) {
            return;
        }

        Minion attackerCard = board.row[attackerX].elems.get(attackerY);

        // Decrease Hero's health
        enemyHero.setHealth(enemyHero.getHealth() - attackerCard.getAttack());

        // Check if enemy's Hero is dead
        if (enemyHero.getHealth() <= 0) {
            // Determine the winner
            int winnerIdx = currPlayer.getIndex();

            successPrinter.printWinner(winnerIdx, output);
            currPlayer.setWins(currPlayer.getWins() + 1);
        }

        // Mark attacker Card as used during this turn
        attackerCard.setUsedTurn(true);
    }

    /**
     * Implements the usage of Hero's ability
     */
    private void useHeroAbility(int affectedRow) {
        if (errorPrinter.errorUseHeroAbility(currPlayer, affectedRow, output)) {
            return;
        }

        Hero hero = currPlayer.getHero();

        // Spend player mana
        currPlayer.decreaseMana(hero.getMana());

        hero.useAbility(board, affectedRow);

        // Mark hero as used during this turn
        hero.setUsedTurn(true);
    }

    /* *** Debug commands methods begin here. *** */
    /**
     * Prints the Card from player [playerIdx] 's hand
     * @param playerIdx index of the player whose hand should be printed.
     */
    private void getCardsInHand(int playerIdx) {
        if (playerIdx == 1) {
            successPrinter.printCardsInHand(player1, output);
        } else {
            successPrinter.printCardsInHand(player2, output);
        }
    }

    /**
     * Prints the Card from player [playerIdx] 's deck
     * @param playerIdx index of the player whose deck should be printed.
     */
    private void getPlayerDeck(int playerIdx) {
        if (playerIdx == 1) {
            successPrinter.printCardsInDeck(player1, output);
        } else {
            successPrinter.printCardsInDeck(player2, output);
        }
    }

    /**
     * Prints all the Cards from the board.
     */
    private void getCardsOnTable() {
        successPrinter.printCardsOnTable(board, output);
    }

    /**
     * Prints the index of the player at turn.
     */
    private void getPlayerTurn() {
        successPrinter.printPlayerTurn(currPlayer.getIndex(), output);
    }


    /**
     * Prints the Hero card of the player specified by the index.
     */
    private void getPlayerHero(int playerIdx) {
        if (playerIdx == 1) {
            successPrinter.printPlayerHero(player1, output);
        } else {
            successPrinter.printPlayerHero(player2, output);
        }
    }

    /**
     * Gets the Card at position [x, y] from the board
     * @param x row
     * @param y column
     */
    private void getCardAtPosition(int x, int y) {
        if (!errorPrinter.errorGetCardAtPosition(board, x, y, output)) {
            successPrinter.printCardAtPosition(board, x, y, output);
        }
    }

    /**
     * Prints the mana of a player.
     */
    private void getPlayerMana(int playerIdx) {
        if (playerIdx == 1) {
            successPrinter.printPlayerMana(player1, output);
        } else {
            successPrinter.printPlayerMana(player2, output);
        }
    }

    /**
     * Prints all the Environment Cards from a player's hand.
     */
    private void getEnvironmentCardsInHand(int playerIdx) {
        if (playerIdx == 1) {
            successPrinter.printEnvironmentCardInHand(player1, output);
        } else {
            successPrinter.printEnvironmentCardInHand(player2, output);
        }
    }

    /**
     * Prints all the frozen cards from the board.
     */
    private void getFrozenCardsOnTable() {
        successPrinter.printFrozenCardsOnTable(board, output);
    }

    /* *** Statistics command methods begin here. *** */

    private void getTotalGamesPlayed() {
        successPrinter.printTotalGamesPlayed(totalGames, output);
    }

    private void getPlayerOneWins() {
        successPrinter.printPlayerWins(player1, output);
    }

    private void getPlayerTwoWins() {
        successPrinter.printPlayerWins(player2, output);
    }

    /* Getters and Setters */
    public Player getPlayer1() {
        return player1;
    }
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }
    public Player getPlayer2() {
        return player2;
    }
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
    public Player getCurrPlayer() {
        return currPlayer;
    }
    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }
    public Board getBoard() {
        return board;
    }
    public void setBoard(Board board) {
        this.board = board;
    }
    public int getTotalGames() {
        return totalGames;
    }
    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }
}
