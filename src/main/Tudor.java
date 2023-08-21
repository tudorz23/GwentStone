package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import fileio.Input;
import gameplay.Game;
import printers.PrinterJson;

import java.io.File;
import java.io.IOException;

public class Tudor {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Input input = mapper.readValue(new File("input/test18_big_game.json"), Input.class);
        Game game = new Game(input);

        game.parsePlayerDecks();
//        System.out.println(game.getPlayer1());
//        System.out.println(game.getPlayer2());

        PrinterJson printerJson = new PrinterJson();
        printerJson.cardPrinter(game.getPlayer1().getPackDeck().get(0).getCardSet().get(0), game.output);
        ObjectWriter objectWriter = mapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File("tudor.json"), game.output);
    }

}
