package gameplay;

import cards.minion.Minion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.GameInput;
import fileio.Input;
import fileio.StartGameInput;
import printers.ErrorPrinter;
import utils.Converter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Game {
    private Player player1;
    private Player player2;
    private Player currPlayer; // reference to the player at turn
    private Board board;
    private int round; // number of the current round
    private int turnChanges; // when it becomes 2, a new round begins
    public Input input;
    public ArrayNode output;
    private ErrorPrinter erorrPrinter = new ErrorPrinter();


    /* Constructor */
    public Game(Input input) {
        this.player1 = new Player(1);
        this.player2 = new Player(2);
        this.input = input;
        this.output = (new ObjectMapper()).createArrayNode();
        //this.board = new Board();
        //this.round = 0;
    }


    /**
     * Completes the packDeck and nrDecks fields for both Player objects.
     */
    public void parsePlayerDecks() {
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
    public void prepareMiniGame(StartGameInput miniGame) {
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
            playGame(game);
        }
    }

    /**
     * Controls the Actual gameplay.
     * @param game Current game of the loop from startGame() method
     */
    public void playGame(GameInput game) {
        // Start the first round
        changeRound();

        // Iterate through the actions
        for (ActionsInput action : game.getActions()) {
            if (action.getCommand().equals("endPlayerTurn")) {
                changeTurn();
            } else if (action.getCommand().equals("placeCard")) {
                placeCard(action.getHandIdx());
            }
        }

    }

    /**
     * Makes the necessary changes when one of the players' turn is over.
     */
    public void changeTurn() {
        // TODO: add unfreeze functionality before this
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
    public void changeRound() {
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

    /**
     * Places a Card from player's hand on the board.
     * @param index position of the card in player's hand
     */
    public void placeCard(int index) {
        if (!erorrPrinter.errorPlaceCard(currPlayer, board, index, output)) {
            // Row where the card should be placed
            int rowIndex = currPlayer.getRowToPlace(index);

            // Spend player's mana for the operation
            currPlayer.decreaseMana(currPlayer.getHand().get(index).getMana());

            // Place the Minion on the board
            board.row[rowIndex].elems.add((Minion)(currPlayer.getHand().remove(index)));

        }
    }

    /* Getters and Setters*/
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
}
