package com.sergioajara.racko;

/**
 * RackoBot - Holds all the logic for the bot's plays.
 */
public class RackoBot {
    RackoRack theRack;
    RackoCard discardCard;
    RackoPlayer thePlayer;
    RackoCard drawCard;
    RackoDeck drawDeck;
    int numOfPlayers;

    /**
     * Constructor for the RackoBot. Will create object without setting values if the secretFiles are null.
     * @param secretFiles The information needed to setup the bot's turn. Game's snapshot of the field.
     */
    public RackoBot(Racko.RackoInfo secretFiles) {
        if(secretFiles == null)
            return;
        theRack = secretFiles.getTheGamePlayer().getRack();
        discardCard = secretFiles.getTheTrashCard();
        thePlayer = secretFiles.getTheGamePlayer();
        numOfPlayers = secretFiles.getNumOfPlayers();
        if(secretFiles.getTheGamePlayer().getLevel() >= 2) {
            drawCard = secretFiles.getTheGameCard();
        }
        if(secretFiles.getTheGamePlayer().getLevel() >= 3) {
            drawDeck = secretFiles.getTheGameDeck();
        }
    }

    /**
     * Has the logic for the turn's options the bot can take.
     * @param drawnCard If given this is the card that was drawn.
     * @return The BotOptions indicating whether to draw or use discard and in what position it should use it.
     */
    public PlayerOptions getOptions(TurnOption turnOpt, RackoCard drawnCard) {
        int theCardValue = drawnCard == null ? discardCard.getFaceValue() : drawnCard.getFaceValue();
        if(thePlayer.getLevel() == 1) {
            if(numOfPlayers == 2) {
                //40 cards
                //1-4, 5-8, 9-12, 13-16, 17-20, 21-24, 25-28, 29-32, 33-36, 37-40
                if(theCardValue >= 1 && theCardValue <= 4) {
                    if(theRack.getPosition5().getFaceValue() > 4) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 1);
                    }
                    else if(theCardValue < theRack.getPosition10().getFaceValue() && (theRack.getPosition10().getFaceValue() <= 8 || theRack.getPosition10().getFaceValue() > 4)) {
                        return new PlayerOptions(TurnOption.DRAW, 1);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 5 && theCardValue <= 8) {
                    if(theRack.getPosition10().getFaceValue() > 8 || theRack.getPosition10().getFaceValue() <= 4) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 2);
                    }
                    else if(theCardValue < theRack.getPosition15().getFaceValue() && (theRack.getPosition15().getFaceValue() <= 12 || theRack.getPosition15().getFaceValue() > 8)) {
                        return new PlayerOptions(TurnOption.DRAW, 2);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 9 && theCardValue <= 12) {
                    if(theRack.getPosition15().getFaceValue() > 12 || theRack.getPosition15().getFaceValue() <= 8) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 3);
                    }
                    else if(theCardValue < theRack.getPosition20().getFaceValue() && (theRack.getPosition20().getFaceValue() <= 16 || theRack.getPosition20().getFaceValue() > 12)) {
                        return new PlayerOptions(TurnOption.DRAW, 3);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 13 && theCardValue <= 16) {
                    if(theRack.getPosition20().getFaceValue() > 16 || theRack.getPosition20().getFaceValue() <= 12) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 4);
                    }
                    else if(theCardValue < theRack.getPosition25().getFaceValue() && (theRack.getPosition25().getFaceValue() <= 20 || theRack.getPosition25().getFaceValue() > 16)) {
                        return new PlayerOptions(TurnOption.DRAW, 4);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 17 && theCardValue <= 20) {
                    if(theRack.getPosition25().getFaceValue() > 20 || theRack.getPosition25().getFaceValue() <= 16) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 5);
                    }
                    else if(theCardValue < theRack.getPosition30().getFaceValue() && (theRack.getPosition30().getFaceValue() <= 24 || theRack.getPosition30().getFaceValue() > 20)) {
                        return new PlayerOptions(TurnOption.DRAW, 5);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 21 && theCardValue <= 24) {
                    if(theRack.getPosition30().getFaceValue() > 24 || theRack.getPosition30().getFaceValue() <= 20) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 6);
                    }
                    else if(theCardValue < theRack.getPosition35().getFaceValue() && (theRack.getPosition35().getFaceValue() <= 28 && theRack.getPosition35().getFaceValue() > 24)) {
                        return new PlayerOptions(TurnOption.DRAW, 6);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 25 && theCardValue <= 28) {
                    if(theRack.getPosition35().getFaceValue() > 28 || theRack.getPosition35().getFaceValue() <= 24) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 7);
                    }
                    else if(theCardValue < theRack.getPosition40().getFaceValue() && (theRack.getPosition40().getFaceValue() <= 32 || theRack.getPosition40().getFaceValue() > 28)) {
                        return new PlayerOptions(TurnOption.DRAW, 7);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 29 && theCardValue <= 32) {
                    if(theRack.getPosition40().getFaceValue() > 32 || theRack.getPosition40().getFaceValue() <= 28) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 8);
                    }
                    else if(theCardValue < theRack.getPosition45().getFaceValue() && (theRack.getPosition45().getFaceValue() <= 36 || theRack.getPosition45().getFaceValue() > 32)) {
                        return new PlayerOptions(TurnOption.DRAW, 8);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 33 && theCardValue <= 36) {
                    if(theRack.getPosition45().getFaceValue() > 36 || theRack.getPosition45().getFaceValue() <= 32) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 9);
                    }
                    else if(theCardValue < theRack.getPosition50().getFaceValue() && (theRack.getPosition50().getFaceValue() <= 50 || theRack.getPosition50().getFaceValue() > 36)) {
                        return new PlayerOptions(TurnOption.DRAW, 9);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 37 && theCardValue <= 40) {
                    if(theRack.getPosition50().getFaceValue() <= 36) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 10);
                    }
                    else if(theCardValue > theRack.getPosition45().getFaceValue() && (theRack.getPosition45().getFaceValue() <= 36 || theRack.getPosition45().getFaceValue() > 32)) {
                        return new PlayerOptions(TurnOption.DRAW, 10);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                return new PlayerOptions(TurnOption.DRAW, -1);
            }
            else if(numOfPlayers == 3) {
                //50 cards
                //1-5, 6-10, 11-15, 16-20, 21-25, 26-30, 31-35, 36-40, 41-45, 46-50
                if(theCardValue >= 1 && theCardValue <= 5) {
                    if(theRack.getPosition5().getFaceValue() > 5) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 1);
                    }
                    else if(theCardValue < theRack.getPosition10().getFaceValue() && (theRack.getPosition10().getFaceValue() <= 10 || theRack.getPosition10().getFaceValue() > 5)) {
                        return new PlayerOptions(TurnOption.DRAW, 1);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 6 && theCardValue <= 10) {
                    if(theRack.getPosition10().getFaceValue() > 10 || theRack.getPosition10().getFaceValue() <= 5) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 2);
                    }
                    else if(theCardValue < theRack.getPosition15().getFaceValue() && (theRack.getPosition15().getFaceValue() <= 15 || theRack.getPosition15().getFaceValue() > 10)) {
                        return new PlayerOptions(TurnOption.DRAW, 2);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 11 && theCardValue <= 15) {
                    if(theRack.getPosition15().getFaceValue() > 15 || theRack.getPosition15().getFaceValue() <= 10) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 3);
                    }
                    else if(theCardValue < theRack.getPosition20().getFaceValue() && (theRack.getPosition20().getFaceValue() <= 20 || theRack.getPosition20().getFaceValue() > 15)) {
                        return new PlayerOptions(TurnOption.DRAW, 3);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 16 && theCardValue <= 20) {
                    if(theRack.getPosition20().getFaceValue() > 20 || theRack.getPosition20().getFaceValue() <= 15) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 4);
                    }
                    else if(theCardValue < theRack.getPosition25().getFaceValue() && (theRack.getPosition25().getFaceValue() <= 25 || theRack.getPosition25().getFaceValue() > 20)) {
                        return new PlayerOptions(TurnOption.DRAW, 4);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 21 && theCardValue <= 25) {
                    if(theRack.getPosition25().getFaceValue() > 25 || theRack.getPosition25().getFaceValue() <= 20) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 5);
                    }
                    else if(theCardValue < theRack.getPosition30().getFaceValue() && (theRack.getPosition30().getFaceValue() <= 30 || theRack.getPosition30().getFaceValue() > 25)) {
                        return new PlayerOptions(TurnOption.DRAW, 5);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 26 && theCardValue <= 30) {
                    if(theRack.getPosition30().getFaceValue() > 30 || theRack.getPosition30().getFaceValue() <= 25) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 6);
                    }
                    else if(theCardValue < theRack.getPosition35().getFaceValue() && (theRack.getPosition35().getFaceValue() <= 35 && theRack.getPosition35().getFaceValue() > 30)) {
                        return new PlayerOptions(TurnOption.DRAW, 6);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 31 && theCardValue <= 35) {
                    if(theRack.getPosition35().getFaceValue() > 35 || theRack.getPosition35().getFaceValue() <= 30) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 7);
                    }
                    else if(theCardValue < theRack.getPosition40().getFaceValue() && (theRack.getPosition40().getFaceValue() <= 40 || theRack.getPosition40().getFaceValue() > 35)) {
                        return new PlayerOptions(TurnOption.DRAW, 7);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 36 && theCardValue <= 40) {
                    if(theRack.getPosition40().getFaceValue() > 40 || theRack.getPosition40().getFaceValue() <= 35) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 8);
                    }
                    else if(theCardValue < theRack.getPosition45().getFaceValue() && (theRack.getPosition45().getFaceValue() <= 45 || theRack.getPosition45().getFaceValue() > 40)) {
                        return new PlayerOptions(TurnOption.DRAW, 8);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 41 && theCardValue <= 45) {
                    if(theRack.getPosition45().getFaceValue() > 45 || theRack.getPosition45().getFaceValue() <= 40) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 9);
                    }
                    else if(theCardValue < theRack.getPosition50().getFaceValue() && (theRack.getPosition50().getFaceValue() <= 50 || theRack.getPosition50().getFaceValue() > 45)) {
                        return new PlayerOptions(TurnOption.DRAW, 9);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 46 && theCardValue <= 50) {
                    if(theRack.getPosition50().getFaceValue() <= 45) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 10);
                    }
                    else if(theCardValue > theRack.getPosition45().getFaceValue() && (theRack.getPosition45().getFaceValue() <= 45 || theRack.getPosition45().getFaceValue() > 40)) {
                        return new PlayerOptions(TurnOption.DRAW, 10);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                return new PlayerOptions(TurnOption.DRAW, -1);
            }
            else if(numOfPlayers == 4) {
                //60 cards
                //1-6, 7-12, 13-18, 19-24, 25-30, 31-36, 37-42, 43-48, 49-54, 55-60
                if(theCardValue >= 1 && theCardValue <= 6) {
                    if(theRack.getPosition5().getFaceValue() > 6) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 1);
                    }
                    else if(theCardValue < theRack.getPosition10().getFaceValue() && (theRack.getPosition10().getFaceValue() <= 12 || theRack.getPosition10().getFaceValue() > 6)) {
                        return new PlayerOptions(TurnOption.DRAW, 1);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 7 && theCardValue <= 12) {
                    if(theRack.getPosition10().getFaceValue() > 12 || theRack.getPosition10().getFaceValue() <= 6) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 2);
                    }
                    else if(theCardValue < theRack.getPosition15().getFaceValue() && (theRack.getPosition15().getFaceValue() <= 18 || theRack.getPosition15().getFaceValue() > 12)) {
                        return new PlayerOptions(TurnOption.DRAW, 2);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 13 && theCardValue <= 18) {
                    if(theRack.getPosition15().getFaceValue() > 18 || theRack.getPosition15().getFaceValue() <= 12) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 3);
                    }
                    else if(theCardValue < theRack.getPosition20().getFaceValue() && (theRack.getPosition20().getFaceValue() <= 24 || theRack.getPosition20().getFaceValue() > 18)) {
                        return new PlayerOptions(TurnOption.DRAW, 3);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 19 && theCardValue <= 24) {
                    if(theRack.getPosition20().getFaceValue() > 24 || theRack.getPosition20().getFaceValue() <= 18) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 4);
                    }
                    else if(theCardValue < theRack.getPosition25().getFaceValue() && (theRack.getPosition25().getFaceValue() <= 30 || theRack.getPosition25().getFaceValue() > 25)) {
                        return new PlayerOptions(TurnOption.DRAW, 4);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 25 && theCardValue <= 30) {
                    if(theRack.getPosition25().getFaceValue() > 30 || theRack.getPosition25().getFaceValue() <= 24) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 5);
                    }
                    else if(theCardValue < theRack.getPosition30().getFaceValue() && (theRack.getPosition30().getFaceValue() <= 36 || theRack.getPosition30().getFaceValue() > 30)) {
                        return new PlayerOptions(TurnOption.DRAW, 5);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 31 && theCardValue <= 36) {
                    if(theRack.getPosition30().getFaceValue() > 36 || theRack.getPosition30().getFaceValue() <= 30) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 6);
                    }
                    else if(theCardValue < theRack.getPosition35().getFaceValue() && (theRack.getPosition35().getFaceValue() <= 42 && theRack.getPosition35().getFaceValue() > 36)) {
                        return new PlayerOptions(TurnOption.DRAW, 6);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 37 && theCardValue <= 42) {
                    if(theRack.getPosition35().getFaceValue() > 42 || theRack.getPosition35().getFaceValue() <= 36) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 7);
                    }
                    else if(theCardValue < theRack.getPosition40().getFaceValue() && (theRack.getPosition40().getFaceValue() <= 48 || theRack.getPosition40().getFaceValue() > 42)) {
                        return new PlayerOptions(TurnOption.DRAW, 7);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 43 && theCardValue <= 48) {
                    if(theRack.getPosition40().getFaceValue() > 48 || theRack.getPosition40().getFaceValue() <= 42) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 8);
                    }
                    else if(theCardValue < theRack.getPosition45().getFaceValue() && (theRack.getPosition45().getFaceValue() <= 54 || theRack.getPosition45().getFaceValue() > 48)) {
                        return new PlayerOptions(TurnOption.DRAW, 8);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 49 && theCardValue <= 54) {
                    if(theRack.getPosition45().getFaceValue() > 54 || theRack.getPosition45().getFaceValue() <= 48) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 9);
                    }
                    else if(theCardValue < theRack.getPosition50().getFaceValue() && (theRack.getPosition50().getFaceValue() <= 60 || theRack.getPosition50().getFaceValue() > 54)) {
                        return new PlayerOptions(TurnOption.DRAW, 9);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                else if(theCardValue >= 55 && theCardValue <= 60) {
                    if(theRack.getPosition50().getFaceValue() <= 54) {
                        return new PlayerOptions(drawnCard == null ? TurnOption.DISCARD : TurnOption.DRAW, 10);
                    }
                    else if(theCardValue > theRack.getPosition45().getFaceValue() && (theRack.getPosition45().getFaceValue() <= 54 || theRack.getPosition45().getFaceValue() > 48)) {
                        return new PlayerOptions(TurnOption.DRAW, 10);
                    }
                    else if(turnOpt != null && turnOpt.equals(TurnOption.DRAW)) {
                        return new PlayerOptions(TurnOption.DISCARD, 11);
                    }
                }
                return new PlayerOptions(TurnOption.DRAW, -1);
            }
        }
        else if(thePlayer.getLevel() == 2) {
            return null;
        }
        else if(thePlayer.getLevel() == 3) {
            return null;
        }

        return null;
    }

}
