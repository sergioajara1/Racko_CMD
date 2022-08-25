package com.sergioajara.racko;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * RackoDeck is a deck object for the Racko game.
 */
public class RackoDeck extends ArrayList<RackoCard>{

    /**
     * Takes the number of players in a game of RackO and creates the appropriate number of cards for the game.
     * @param deckSize The number of players corresponding to the deck size.
     */
    public RackoDeck(int deckSize) {
        switch (deckSize) {
            case 2:
                createCards(40);
                break;
            case 3:
                createCards(50);
                break;
            case 4:
                createCards(60);
                break;
        }
    }

    /**
     * Creates a RackoDeck of the size given in the parameter.
     * @param i Number of cards to create.
     */
    private void createCards(int i) {
        for(int x = 1; x <= i; x++) {
            this.add(new RackoCard(x, true));
        }
    }

    /**
     * Shuffles the deck.
     */
    public void shuffle() {
        Collections.shuffle(this);
    }

    /**
     * Draws from the deck by removing the last card.
     * @return The last RackoCard in the deck.
     */
    public RackoCard draw() {
        return this.remove(this.size()-1);
    }

    @Override
    public String toString() {
        Iterator<RackoCard> it = iterator();
        if (! it.hasNext())
            return "The Deck:";

        StringBuilder sb = new StringBuilder();
        sb.append("The Deck: ");
        for (;;) {
            RackoCard e = it.next();
            //Removed below two lines to only display the last card in the deck.
            //sb.append(e);
            if (! it.hasNext())
                return sb.append(e).toString();
            //sb.append(',').append(' ');
        }
    }
}
