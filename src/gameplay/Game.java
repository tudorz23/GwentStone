package gameplay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.GameInput;
import fileio.Input;
import fileio.StartGameInput;
import utils.Converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {
    private Player player1;
    private Player player2;
    private Player currPlayer;
    private int turn; // which players' turn it is
    public Input input;
    public ArrayNode output;
    private Board board;

    /* Constructor */
    public Game(Input input) {
        this.player1 = new Player();
        this.player2 = new Player();
        this.input = input;
        this.output = (new ObjectMapper()).createArrayNode();
        this.board = new Board();
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
     * @param miniGame returned from a loop that iterates through all the mini-games
     */
    public void prepareMiniGame(StartGameInput miniGame) {
        // Set the shuffleSeed
        // Changes from one mini-game to the other
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
    }

    /**
     * This shall be the starting point of the game.
     */
    public void startGame() {
        // Players get their decks
        parsePlayerDecks();

        // Play the games
        for (GameInput game : input.getGames()) {
            prepareMiniGame(game.getStartGame());
            //playGame();
        }
    }

    public void playGame() {

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
