package com.sergioajara.racko;

import java.util.ArrayList;
import java.util.Iterator;

public class RackoDiscard extends ArrayList<RackoCard> {

    public RackoDiscard() {
        super();
    }

    /**
     * Taking a card from this deck.
     * @return The card that is being taken from the deck.
     */
    public RackoCard draw() {
        return this.remove(this.size()-1);
    }

    /**
     * Use this when you're sending a card to be discarded, adding a card to this deck.
     * This function automatically turns the card face up.
     * @param theCard The card that's being added.
     */
    public void discardCard(RackoCard theCard) {
        theCard.setFaceDown(false);
        this.add(theCard);
    }

    @Override
    public String toString() {
        Iterator<RackoCard> it = iterator();
        if (! it.hasNext())
            return "Discard Deck:";

        StringBuilder sb = new StringBuilder();
        sb.append("Discard Deck: ");
        for (;;) {
            RackoCard e = it.next();
            //Removed below two lines to only display the last card in the discard pile.
            //sb.append(e);
            if (! it.hasNext())
                return sb.append(e).toString();
            //sb.append(',').append(' ');
        }
    }
}
