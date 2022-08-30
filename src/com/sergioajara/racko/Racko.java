package com.sergioajara.racko;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class Racko {
    private RackoDeck theDeck;
    private RackoDiscard theTrash;
    private ArrayList<RackoPlayer> thePlayers;
    private RackoPlayer theDealer;
    private RackoPlayer theWinner;
    private boolean endRound;
    private boolean endGame;
    private int roundNum;

    /**
     * Given a list of players a RackO game is initialized.
     * @param players The list of players for this game of RackO.
     */
    public Racko(ArrayList<RackoPlayer> players) {
        thePlayers = players;
        theDeck = new RackoDeck(thePlayers.size());
        theTrash = new RackoDiscard();
        endRound = false;
        endGame = false;
        roundNum = 0;
    }

    /**
     * @return endGame - activate the kill signal if it's the end of the game
     */
    public boolean getKillSignal() {
        return endGame;
    }

    /**
     * Starts a game of Racko by shuffling, setting up the decks and distributing cards to the players.
     */
    public void start() {
        theDeck.shuffle();
        chooseDealer();
        theDeck.shuffle();
        dealCards();

        //Setting up the discard pile
        setupTheTrash();
        //Check the draw pile for face up cards... dealer messes up sometimes
        organizeTheDeck();
    }

    /**
     * Deals 10 cards to all the players. Starts by using the Dealer's position and deals according to that position.
     */
    private void dealCards() {
        //Deal 10 cards to each player
        int dealerIndex = thePlayers.indexOf(theDealer);
        for(int x = 0; x < 10; x++) {
            dealerIndex = (dealerIndex == thePlayers.size() - 1 ? -1 : dealerIndex);
            for (dealerIndex += 1; dealerIndex <= thePlayers.size(); dealerIndex++) {
                if(dealerIndex == thePlayers.size())
                     dealerIndex = 0;
                RackoPlayer currPlayer = thePlayers.get(dealerIndex);
                currPlayer.addCard(theDeck.draw());
                if(currPlayer == theDealer)
                    break;
            }
            dealerIndex = thePlayers.indexOf(theDealer);
        }
    }

    /**
     * Picks a dealer based on the game's round number.
     */
    private void chooseDealer() {
        if(roundNum == 0) {
            //Deal 1 card to each player and check for the highest card.
            for (int x = 0; x < 1; x++) {
                for (RackoPlayer currPlayer : thePlayers) {
                    currPlayer.addCard(theDeck.draw());
                }
            }
            //Choosing the dealer by checking for the highest card.
            theDealer = thePlayers.get(0);
            for (RackoPlayer currPlayer : thePlayers) {
                if (theDealer != currPlayer && currPlayer.getRack().getPosition5().getFaceValue() > theDealer.getRack().getPosition5().getFaceValue()) {
                    theDealer = currPlayer;
                }
            }
            //Returning cards and shuffling.
            for (RackoPlayer currPlayer : thePlayers) {
                theDeck.add(currPlayer.getRack().removePosition5());
            }
        }
        else {
             int idxOfDealer = thePlayers.indexOf(theDealer);
             if(idxOfDealer == thePlayers.size() - 1)
                 idxOfDealer = -1;
             theDealer = thePlayers.get(idxOfDealer+1);
        }
    }

    /**
     * Makes sure the draw pile is face down.
     */
    private void organizeTheDeck() {
        for(RackoCard aCard : theDeck) {
            aCard.setFaceDown(true);
        }
    }

    /**
     * Takes turns between all players.
     * Checks for RackO to end the round and scores players.
     */
    public void takeTurns() {
        //If we got here with an endGame and endRound active return and kill the game.
        if(endGame && endRound)
            return;

        System.out.println("================================================================================");
        System.out.println("                            Starting Round " + (roundNum + 1));

        int dealerIndex = thePlayers.indexOf(theDealer);
        dealerIndex = (dealerIndex == thePlayers.size() - 1 ? -1 : dealerIndex);

        for (dealerIndex += 1; dealerIndex <= thePlayers.size(); dealerIndex++) {
            RackoPlayer currPlayer;
            try {
                currPlayer = thePlayers.get(dealerIndex);
                System.out.println("================================================================================");
                System.out.println(currPlayer.getName() + "'s Turn");
                System.out.println(currPlayer.toString());
                try {
                    TimeUnit.SECONDS.sleep(2);
                }
                catch(Exception ignored) {}

                if(checkForRacko(currPlayer))
                    endRound(currPlayer);
                else
                    takeTurn(currPlayer);

                if(checkForRacko(currPlayer) && !endRound)
                    endRound(currPlayer);

                if(endRound)
                    break;
                
                if(currPlayer == theDealer)
                    dealerIndex = thePlayers.indexOf(theDealer);
            } catch (IndexOutOfBoundsException e) {
                    //Put at negative, so it zeros out on the next loop.
                    dealerIndex = -1;
            }
        }
        if(endRound) {
            scorePlayers();
            checkForEndGame();
            if(endGame){
                printEndGameScreen();
            }
            else
                printEndRoundScreen();
        }
    }

    /**
     * Resets the round variables, updates the dealer and sets up all the cards for a new round.
     */
    private void resetRound() {
        endRound = false;
        chooseDealer();
        cleanUpCards();
        dealCards();
        setupTheTrash();
        organizeTheDeck();
    }

    /**
     * Sets up theTrash by drawing from theDeck.
     */
    private void setupTheTrash() {
        theTrash.discardCard(theDeck.draw());
    }

    /**
     * Cleans up all the cards on the field by adding them into theDeck then it organizes and shuffles the deck.
     */
    private void cleanUpCards() {
        for(RackoPlayer thePlayer : thePlayers) {
            Iterator<RackoCard> itr = thePlayer.getRack().iterator();
            while(itr.hasNext()) {
                RackoCard theCard = itr.next();
                theDeck.add(theCard);
                itr.remove();
            }
        }
        Iterator<RackoCard> itr = theTrash.iterator();
        while(itr.hasNext()) {
            RackoCard theCard = itr.next();
            theDeck.add(theCard);
            itr.remove();
        }
        organizeTheDeck();
        theDeck.shuffle();
    }

    /**
     * Prints out the end of the game screen.
     */
    private void printEndGameScreen() {
        System.out.println("********************************************************************************");
        System.out.println("********************************************************************************");
        System.out.println("**                                 WINNER of the Game!!!!");
        System.out.println("**                                 " + theWinner.getName());
        System.out.println("**                                 Score: " + theWinner.getScore());
        System.out.println("**                                 Rounds Won: " + theWinner.getRoundsWon() + " of " + roundNum);
        System.out.println("********************************************************************************");
        System.out.println("********************************************************************************");
        try {
            TimeUnit.SECONDS.sleep(30);
        }
        catch(Exception ignored) {}
    }

    /**
     * Checks the players to see if they've reached the end of the game based on points.
     */
    private void checkForEndGame() {
        for(RackoPlayer thePlayer : thePlayers) {
            if(thePlayer.getScore() >= 500 && !endGame) {
                endGame = true;
                theWinner = thePlayer;
            }
            else if(thePlayer.getScore() >= 500 && endGame) {
                if(thePlayer.getScore() > theWinner.getScore())
                    theWinner = thePlayer;
            }
        }
    }

    /**
     * Ends the round and activates flags and updates roundNum and roundsWon.
     * @param currPlayer - the winner of the round.
     */
    private void endRound(RackoPlayer currPlayer) {
        endRound = true;
        roundNum++;
        theWinner = currPlayer;
        theWinner.updateRoundsWon();
    }

    /**
     * Scores the players.
     * ToDo: Bonus points not implemented yet
     */
    private void scorePlayers() {
        for(RackoPlayer thePlayer : thePlayers) {
            int winningCards = 1;
            for(int i = 0; i < thePlayer.getRack().size(); i++) {
                try {
                    if (thePlayer.getRack().get(i + 1) != null && thePlayer.getRack().get(i).getFaceValue() < thePlayer.getRack().get(i + 1).getFaceValue()) {
                        winningCards++;
                    } else {
                        break;
                    }
                }
                catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
            if(winningCards == 10)
                thePlayer.updateScore(75);
            else
                thePlayer.updateScore(winningCards * 5);
        }
    }

    /**
     * Prints the end of the Round Screen and waits.
     */
    private void printEndRoundScreen() {
        System.out.println("********************************************************************************");
        System.out.println("*                                 WINNER of Round " + roundNum);
        System.out.println("*                                 " + theWinner.getName());
        System.out.println("*                                 Score: " + theWinner.getScore());
        System.out.println("*                                 Rounds Won: " + theWinner.getRoundsWon());
        System.out.println("********************************************************************************");
        try {
            TimeUnit.SECONDS.sleep(15);
        }
        catch(Exception ignored) {}
    }

    /**
     * Takes a turn for the currPlayer.
     * @param currPlayer - The player taking a turn.
     */
    private void takeTurn(RackoPlayer currPlayer) {
        //Check if the deck is empty before taking a turn. If it is reset the deck and discard pile.
        checkDecks();

        System.out.println(theDeck.toString());
        System.out.println(theTrash.toString());
        System.out.println("[1] Draw from Draw Pile");
        System.out.println("[2] Draw from Discard Pile");

        RackoCard drawnCard;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PlayerOptions playerTurnOpts = getTurnOption(reader, currPlayer);
        if(playerTurnOpts.getTurnOption().equals(TurnOption.DRAW)) {
            System.out.println("Drawing from the Draw Pile...");
            try {
                TimeUnit.SECONDS.sleep(2);
            }
            catch(Exception ignored) {}
            drawnCard = theDeck.draw();
            drawnCard.setFaceDown(false);
        }
        else {
            System.out.println("Drawing from the Discard Pile...");
            try {
                TimeUnit.SECONDS.sleep(2);
            }
            catch(Exception ignored) {}
            drawnCard = theTrash.draw();
        }

        System.out.println("Which position do you want to put the drawn card into? Drawn Card: " + drawnCard.toString());
        playerTurnOpts = getDrawOption(reader, currPlayer, drawnCard, playerTurnOpts);
        switch (playerTurnOpts.getDrawnOption()) {
            case 1 -> theTrash.discardCard(currPlayer.updateRack(0, drawnCard));
            case 2 -> theTrash.discardCard(currPlayer.updateRack(1, drawnCard));
            case 3 -> theTrash.discardCard(currPlayer.updateRack(2, drawnCard));
            case 4 -> theTrash.discardCard(currPlayer.updateRack(3, drawnCard));
            case 5 -> theTrash.discardCard(currPlayer.updateRack(4, drawnCard));
            case 6 -> theTrash.discardCard(currPlayer.updateRack(5, drawnCard));
            case 7 -> theTrash.discardCard(currPlayer.updateRack(6, drawnCard));
            case 8 -> theTrash.discardCard(currPlayer.updateRack(7, drawnCard));
            case 9 -> theTrash.discardCard(currPlayer.updateRack(8, drawnCard));
            case 10 -> theTrash.discardCard(currPlayer.updateRack(9, drawnCard));
            case 11 -> theTrash.discardCard(drawnCard);
        }
    }

    /**
     * Check the decks and reset them if theDeck is empty. Otherwise, do nothing.
     */
    private void checkDecks() {
        if(theDeck.isEmpty()) {
            Iterator<RackoCard> itr = theTrash.iterator();
            while(itr.hasNext()) {
                RackoCard theCard = itr.next();
                theDeck.add(theCard);
                itr.remove();
            }
            organizeTheDeck();
            setupTheTrash();
        }
    }

    /**
     * @return True if option 1 is selected, option 1 is draw from deck. Otherwise, false.
     */
    private PlayerOptions getTurnOption(BufferedReader reader, RackoPlayer thePlayer) {
        String turnOption = "0";
        PlayerOptions botPlayerOptions = null;
        try {
            while (Integer.parseInt(turnOption) < 1 || Integer.parseInt(turnOption) > 2) {
                System.out.print("Choose your option [1/2]: ");
                if(thePlayer.getBot() != null) {
                    botPlayerOptions = thePlayer.getBot(new RackoInfo(this, thePlayer)).getOptions(null,null);
                    System.out.println(botPlayerOptions.getTurnOption() == TurnOption.DRAW ? 1 : 2);
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    }
                    catch(Exception ignored) {}
                    turnOption = String.valueOf(botPlayerOptions.getTurnOption().equals(TurnOption.DRAW) ? 1 : 2);
                }
                else {
                    turnOption = reader.readLine();
                    if(turnOption.equals("1"))
                        return new PlayerOptions(TurnOption.DRAW, -1);
                    else if(turnOption.equals("2"))
                        return new PlayerOptions(TurnOption.DISCARD, -1);
                    else
                        throw new IOException();

                }
            }
        }
        catch (IOException e) {
            System.out.println();
            System.out.println("ERROR: Wrong input, try again.");
            return getTurnOption(reader, thePlayer);
        }
        catch (NumberFormatException e) {
            System.out.println();
            System.out.println("ERROR: Enter a number not a character, try again.");
            return getTurnOption(reader, thePlayer);
        }
        return botPlayerOptions;
    }

    private PlayerOptions getDrawOption(BufferedReader reader, RackoPlayer thePlayer, RackoCard drawnCard, PlayerOptions playerOpts) {
        PlayerOptions botPlayerOptions = null;
        String turnOption = "0";
        System.out.println("Player Rack: " + thePlayer.getRack().toString());
        try {
            while (Integer.parseInt(turnOption) < 1 || Integer.parseInt(turnOption) > 11) {
                if(playerOpts.getTurnOption().equals(TurnOption.DRAW))
                    System.out.print("Choose your option [1-10 or 11 For Discard]: ");
                else
                    System.out.print("Choose your option [1-10]: ");
                if(thePlayer.getBot() != null) {
                    botPlayerOptions = thePlayer.getBot(new RackoInfo(this, thePlayer)).getOptions(playerOpts.getTurnOption(), drawnCard);
                    System.out.println(botPlayerOptions.getDrawnOption());
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    }
                    catch(Exception ignored) {}
                    turnOption = String.valueOf(botPlayerOptions.getDrawnOption());
                    botPlayerOptions = new PlayerOptions(playerOpts.getTurnOption(), botPlayerOptions.getDrawnOption());
                }
                else {
                    turnOption = reader.readLine();
                    return new PlayerOptions(playerOpts.getTurnOption(), Integer.parseInt(turnOption));
                }
            }
        }
        catch (IOException e) {
            System.out.println();
            System.out.println("ERROR: Wrong input, try again.");
            return getDrawOption(reader, thePlayer, drawnCard, playerOpts);
        }
        catch (NumberFormatException e) {
            System.out.println();
            System.out.println("ERROR: Enter a number not a character, try again.");
            return getDrawOption(reader, thePlayer, drawnCard, playerOpts);
        }
        return botPlayerOptions;
    }

    /**
     * @param currPlayer The player to check for RackO.
     * @return True if all cards in the currPlayer's rack is in order.
     */
    private boolean checkForRacko(RackoPlayer currPlayer) {
        for(int x = 0; x < currPlayer.getRack().size(); x++) {
            try {
                if (currPlayer.getRack().get(x + 1) != null && currPlayer.getRack().get(x).getFaceValue() > currPlayer.getRack().get(x + 1).getFaceValue())
                    return false;
            }
            catch(IndexOutOfBoundsException iOBE) {
                //DO NOTHING
            }
        }
        return true;
    }

    public RackoDeck getTheDeck() {
        return theDeck;
    }

    public RackoDiscard getTheTrash() {
        return theTrash;
    }

    public ArrayList<RackoPlayer> getThePlayers() {
        return thePlayers;
    }

    /**
     * Starts a game if it's the first game, otherwise it asks to end the game or continue. If continue it resets the
     * round.
     */
    public void setupGame() {
        if(endRound) {
            requestEndGame();
            if(endGame && endRound){
                updateWinnerForEndGame();
                printEndGameScreen();
                return;
            }
            resetRound();
        }
        else {
            start();
        }
    }

    /**
     * Sets theWinner to the player with the highest score.
     */
    private void updateWinnerForEndGame() {
        RackoPlayer theRealWinner = null;

        for(RackoPlayer aPlayer : thePlayers) {
            if(theRealWinner == null || aPlayer.getScore() > theRealWinner.getScore())
                theRealWinner = aPlayer;
        }

        theWinner = theRealWinner;
    }

    private void requestEndGame() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Would you like to continue the game?");
        try {
            String option = "";
            while (!option.equals("C") && !option.equals("Q")) {
                System.out.print("[C]ontinue [Q]uit: ");
                if(isAllBots()){
                    option = "C";
                    System.out.println(option);
                }
                else {
                    option = reader.readLine();
                    option = option.toUpperCase();
                }
            }

            if(option.equals("Q")) {
                endGame = true;
                endRound = true;
            }
        }
        catch (IOException e) {
            System.out.println();
            System.out.println("ERROR: Wrong input, try again.");
            requestEndGame();
        }
    }

    /**
     * @return TRUE if all players are bots.
     */
    private boolean isAllBots() {
        boolean allBots = true;
        for(RackoPlayer aPlayer : thePlayers) {
            if(!aPlayer.isBot())
                allBots = false;
        }
        return allBots;
    }

    /**
     * RackoInfo holds any data that is essential to know about a Racko game.
     */
    public static class RackoInfo {
        private RackoDeck theGameDeck;
        private RackoDiscard theGameTrash;
        private RackoCard theTrashCard;
        private RackoCard theGameCard;
        private int numOfPlayers;
        private RackoPlayer theGamePlayer;

        /**
         * Sets up the object's fields
         * @param aGame The game to grab the fields from
         * @param thePlayer The current player
         */
        public RackoInfo(Racko aGame, RackoPlayer thePlayer) {
            theGameDeck = aGame.getTheDeck();
            theGameTrash = aGame.getTheTrash();
            theTrashCard = theGameTrash.isEmpty() ? null : theGameTrash.get(theGameTrash.size() - 1);
            theGameCard = theGameDeck.get(theGameDeck.size() - 1);
            numOfPlayers = aGame.getThePlayers().size();
            theGamePlayer = thePlayer;
        }

        public RackoDeck getTheGameDeck() {
            return theGameDeck;
        }

        public RackoDiscard getTheGameTrash() {
            return theGameTrash;
        }

        public RackoCard getTheTrashCard() {
            return theTrashCard;
        }

        public RackoCard getTheGameCard() {
            return theGameCard;
        }

        public int getNumOfPlayers() {
            return numOfPlayers;
        }

        public RackoPlayer getTheGamePlayer() {
            return theGamePlayer;
        }

    }
}
