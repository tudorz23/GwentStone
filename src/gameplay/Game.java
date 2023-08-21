package gameplay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Input;
import fileio.StartGameInput;
import utils.Converter;

public class Game {
    private Player player1;
    private Player player2;
    public Input input;
    public ArrayNode output;
    // Changes from one mini-game to the other
    private int shuffleSeed;

    /* Constructor */
    public Game(Input input) {
        this.player1 = new Player();
        this.player2 = new Player();
        this.input = input;
        this.output = (new ObjectMapper()).createArrayNode();
    }

    /**
     * Completes the packDeck field for both Player objects.
     */
    public void parsePlayerDecks() {
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
     * @param miniGame returned from a loop that iterates through all the mini-games
     */
    public void prepareMiniGame(StartGameInput miniGame) {
        // Set the shuffleSeed
        this.shuffleSeed = miniGame.getShuffleSeed();

        // Set the heroes
        Converter converter = new Converter();
        player1.setHero(converter.convertHero(miniGame.getPlayerOneHero()));
        player2.setHero(converter.convertHero(miniGame.getPlayerTwoHero()));


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
}
