package gameplay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Input;
import utils.Converter;

public class Game {
    private Player player1;
    private Player player2;
    public Input input;
    public ArrayNode output = (new ObjectMapper()).createArrayNode();

    public Game(Input input) {
        this.player1 = new Player();
        this.player2 = new Player();
        this.input = input;
    }

    public void parsePlayerDecks() {
        Converter converter = new Converter();
        player1.setPackDeck(converter.convertDeck(input.getPlayerOneDecks()));
        player2.setPackDeck(converter.convertDeck(input.getPlayerTwoDecks()));

        player1.setNrDecks(input.getPlayerOneDecks().getNrDecks());
        player2.setNrDecks(input.getPlayerTwoDecks().getNrDecks());
    }

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
