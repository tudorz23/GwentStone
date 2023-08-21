package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import fileio.GameInput;
import fileio.Input;
import gameplay.Game;
import printers.PrinterJson;

import java.io.File;
import java.io.IOException;

public class Tudor {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Input input = mapper.readValue(new File("input/test02_place_card.json"), Input.class);
        Game game = new Game(input);

        game.parsePlayerDecks();
//        System.out.println(game.getPlayer1());
//        System.out.println(game.getPlayer2());

        for (GameInput games : input.getGames()) {
            // Make the initial settings to start a mini-game
            game.prepareMiniGame(games.getStartGame());
        }


        PrinterJson printerJson = new PrinterJson();
        printerJson.cardPrinter(game.getPlayer2().getDeck().getCardSet().get(0), game.output);
        ObjectWriter objectWriter = mapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File("tudor.json"), game.output);
    }

}
