package printers;

import cards.Card;
import cards.hero.Hero;
import cards.minion.Minion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PrinterJson {
    private ObjectMapper mapper = new ObjectMapper();
    public void cardPrinter(Card card, ArrayNode output) {
        ObjectNode cardPrint = mapper.createObjectNode();
        cardPrint.put("mana", card.getMana());

        if (card.getRow() != 4 && card.getRow() != 5) {
            // Minion Card
            cardPrint.put("attackDamage", ((Minion)card).getAttack());
            cardPrint.put("health", ((Minion)card).getHealth());
        }

        cardPrint.put("description", card.getDescription());

        // Print the colors string array
        ArrayNode colorPrint = mapper.createArrayNode();
        for (String color : card.getColors()) {
            colorPrint.add(color);
        }
        cardPrint.set("colors", colorPrint);
        cardPrint.put("name", card.getName());

        if (card.getRow() == 5) {
            // Hero Card
            cardPrint.put("health", ((Hero)card).getDescription());
        }

        output.add(cardPrint);
    }
}
