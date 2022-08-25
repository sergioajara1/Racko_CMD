package com.sergioajara.racko;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

    public boolean getKillSignal() {
        return endGame;
    }

    /**
     * Starts a game of Racko by shuffling, setting up the decks and distributing cards to the players.
     */
    public void start() {
        theDeck.shuffle();

        //Deal 1 card to each player and check for the highest card.
        for(int x = 0; x < 1; x++) {
            for(RackoPlayer currPlayer: thePlayers) {
                currPlayer.addCard(theDeck.draw());
            }
        }
        //Choosing the dealer by checking for the highest card.
        theDealer = thePlayers.get(0);
        for(RackoPlayer currPlayer : thePlayers) {
            if(theDealer != currPlayer && currPlayer.getRack().getPosition5().getFaceValue() > theDealer.getRack().getPosition5().getFaceValue()) {
                theDealer = currPlayer;
            }
        }
        //Returning cards and shuffling.
        for(RackoPlayer currPlayer : thePlayers) {
            theDeck.add(currPlayer.getRack().removePosition5());
        }
        theDeck.shuffle();

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


        //Setting up the discard pile
        theTrash.discardCard(theDeck.draw());
        //Check the draw pile for face up cards... dealer messes up sometimes
        organizeTheDeck();
    }

    /**
     * Makes sure the draw pile is face down.
     */
    private void organizeTheDeck() {
        for(RackoCard aCard : theDeck) {
            aCard.setFaceDown(true);
        }
    }

    public void takeTurns() {
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
                catch(Exception e) {}
                if(checkForRacko(currPlayer))
                    endRound(currPlayer);
                else
                    takeTurn(currPlayer);

                if(checkForRacko(currPlayer) && endRound != true)
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
            if(endGame)
                printEndGameScreen();
            else
                printEndRoundScreen();
        }
    }

    private void printEndGameScreen() {
        System.out.println("********************************************************************************");
        System.out.println("********************************************************************************");
        System.out.println("**                                 WINNER of the Game!!!!");
        System.out.println("**                                 " + theWinner.getName());
        System.out.println("**                                 Score: " + theWinner.getScore());
        System.out.println("**                                 Rounds Won: " + theWinner.getRoundsWon() + " of " + roundNum);
        System.out.println("********************************************************************************");
        System.out.println("********************************************************************************");
    }

    private void checkForEndGame() {
        for(RackoPlayer thePlayer : thePlayers) {
            if(thePlayer.getScore() >= 500 && endGame == false) {
                endGame = true;
                theWinner = thePlayer;
            }
            else if(thePlayer.getScore() >= 500 && endGame) {
                if(thePlayer.getScore() > theWinner.getScore())
                    theWinner = thePlayer;
            }
        }
    }

    private void endRound(RackoPlayer currPlayer) {
        endRound = true;
        roundNum++;
        theWinner = currPlayer;
        theWinner.updateRoundsWon();

    }

    private void scorePlayers() {
        for(RackoPlayer thePlayer : thePlayers) {
            int winningCards = 1;
            for(int i = 0; i < thePlayer.getRack().size(); i++) {
                if(thePlayer.getRack().get(i+1) != null && thePlayer.getRack().get(i).getFaceValue() < thePlayer.getRack().get(i+1).getFaceValue()) {
                    winningCards++;
                }
                else {
                    break;
                }
            }
            if(winningCards == 10)
                thePlayer.updateScore(75);
            else
                thePlayer.updateScore(winningCards * 5);
        }
    }

    private void printEndRoundScreen() {
        System.out.println("********************************************************************************");
        System.out.println("*                                 WINNER of Round " + roundNum);
        System.out.println("*                                 " + theWinner.getName());
        System.out.println("*                                 Score: " + theWinner.getScore());
        System.out.println("*                                 Rounds Won: " + theWinner.getRoundsWon());
        System.out.println("********************************************************************************");
    }

    private void takeTurn(RackoPlayer currPlayer) {
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
            catch(Exception e) {}
            drawnCard = theDeck.draw();
            drawnCard.setFaceDown(false);
        }
        else {
            System.out.println("Drawing from the Discard Pile...");
            try {
                TimeUnit.SECONDS.sleep(2);
            }
            catch(Exception e) {}
            drawnCard = theTrash.draw();
        }

        System.out.println("Which position do you want to put the drawn card into? Drawn Card: " + drawnCard.toString());
        playerTurnOpts = getDrawOption(reader, currPlayer, drawnCard, playerTurnOpts);
        switch(playerTurnOpts.getDrawnOption()) {
            case 1:
                theTrash.discardCard(currPlayer.updateRack(0, drawnCard));
                break;
            case 2:
                theTrash.discardCard(currPlayer.updateRack(1, drawnCard));
                break;
            case 3:
                theTrash.discardCard(currPlayer.updateRack(2, drawnCard));
                break;
            case 4:
                theTrash.discardCard(currPlayer.updateRack(3, drawnCard));
                break;
            case 5:
                theTrash.discardCard(currPlayer.updateRack(4, drawnCard));
                break;
            case 6:
                theTrash.discardCard(currPlayer.updateRack(5, drawnCard));
                break;
            case 7:
                theTrash.discardCard(currPlayer.updateRack(6, drawnCard));
                break;
            case 8:
                theTrash.discardCard(currPlayer.updateRack(7, drawnCard));
                break;
            case 9:
                theTrash.discardCard(currPlayer.updateRack(8, drawnCard));
                break;
            case 10:
                theTrash.discardCard(currPlayer.updateRack(9, drawnCard));
                break;
            case 11:
                theTrash.discardCard(drawnCard);
                break;
        }
    }

    /**
     * @return True if option 1 is selected, option 1 is draw from deck. Otherwise, false.
     */
    private PlayerOptions getTurnOption(BufferedReader reader, RackoPlayer thePlayer) {
        String turnOption = "0";
        PlayerOptions botPlayerOptions = null;
        try {
            while (Integer.valueOf(turnOption) < 1 || Integer.valueOf(turnOption) > 2) {
                System.out.print("Choose your option [1/2]: ");
                if(thePlayer.getBot() != null) {
                    botPlayerOptions = thePlayer.getBot(new RackoInfo(this, thePlayer)).getOptions(null,null);
                    System.out.println(botPlayerOptions.getTurnOption() == TurnOption.DRAW ? 1 : 2);
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    }
                    catch(Exception e) {}
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
            while (Integer.valueOf(turnOption) < 1 || Integer.valueOf(turnOption) > 11) {
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
                    catch(Exception e) {}
                    turnOption = String.valueOf(botPlayerOptions.getDrawnOption());
                }
                else {
                    turnOption = reader.readLine();
                    return new PlayerOptions(playerOpts.getTurnOption(), Integer.valueOf(turnOption));
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
            if (currPlayer.getRack().get(x+1) != null && currPlayer.getRack().get(x).getFaceValue() > currPlayer.getRack().get(x + 1).getFaceValue())
                return false;
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
