package com.sergioajara.racko;

import java.util.ArrayList;

public class RackoPlayer {
    private String name;
    private int score;
    private int roundsWon;
    private RackoRack theRack;
    private RackoBot theBot;
    private boolean botFlag;
    private int level;

    /**
     * Constructor for RackoPlayer
     * @param playerName The name of the player
     * @param flag The flag to activate a bot player
     */
    public RackoPlayer(String playerName, boolean flag){
        name = playerName;
        resetPlayer();
        botFlag = flag;
        theRack = new RackoRack();
        level = 1;
        if(botFlag)
            theBot = new RackoBot(null);
    }

    /**
     * Resets the rack and score of the player.
     */
    public void resetPlayer() {
        score = 0;
        roundsWon = 0;
    }

    /**
     * Used for adding new cards to the rack. Won't add more than 10 cards.
     * @param theCard The card to be added to the rack.
     */
    public void addCard(RackoCard theCard) {
        theCard.setFaceDown(false);
        if(theRack.size() < 10)
            theRack.add(0, theCard);
    }

    /**
     * Replaces a card in the rack with the given RackoCard.
     * @param position The position of the card in the rack to be replaced.
     * @param theCard The card that will replace a card in the rack.
     * @return The card that was in the rack. This will go to the discard pile.
     */
    public RackoCard updateRack(int position, RackoCard theCard) {
        RackoCard theHand = theRack.remove(position);
        theRack.add(position, theCard);
        return theHand;
    }

    /**
     * Removes and returns a card from the rack at the given position.
     * @param position The position to remove the card from.
     * @return The card that was removed from the rack.
     */
    public RackoCard removeCard(int position) {
        return theRack.remove(position);
    }

    /**
     * Updates the old score by adding the newScore to it.
     * @param newScore The score to be added to the current score.
     */
    public void updateScore(int newScore) {
        score += newScore;
    }

    /**
     * @return The latest score for this player.
     */
    public int getScore() {
        return score;
    }

    /**
     * @return The rack used by this player.
     */
    public RackoRack getRack() {
        return theRack;
    }

    /**
     * @return The name of this player.
     */
    public String getName() {
        return name;
    }


    public RackoBot getBot() {
        return theBot;
    }

    public RackoBot getBot(Racko.RackoInfo secretFiles) {
        theBot = new RackoBot(secretFiles);
        return theBot;
    }

    public void setBot(RackoBot theBot) {
        this.theBot = theBot;
    }

    public boolean isBot() {
        return botFlag;
    }

    public void setBotFlag(boolean botFlag) {
        this.botFlag = botFlag;
    }

    public void setLevel(int theLevel) {
        level = theLevel;
    }

    public int getLevel() {
        return level;
    }

    /**
     * Adds one round won to the total roundsWon for this RackoPlayer
     */
    public void updateRoundsWon() {
        roundsWon++;
    }

    public int getRoundsWon() {
        return roundsWon;
    }

    public void setRoundsWon(int roundsWon) {
        this.roundsWon = roundsWon;
    }

    @Override
    public String toString() {
        return "Player Name: " + name + "\n" +
                "Player Score: " + score + "\n" +
                "Player Rack: " + theRack;
    }
}
