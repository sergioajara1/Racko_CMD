package com.sergioajara.racko;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RackoGame {
    private Racko theGame;

    public RackoGame() {}

    /**
     * Output's the starting screen and waits for ENTER to be hit.
     * @param reader The reader used to wait for ENTER to be hit.
     */
    private void outputStart(BufferedReader reader) {
        System.out.println("-----------------------");
        System.out.println("   Welcome to Racko!");
        System.out.println("-----------------------");
        System.out.println("  Press Enter To Start");
        try {
            reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets up the number of players and if they are a bot.
     * @param reader The reader used to get input.
     * @return A list of RackoPlayers, setup for a game.
     */
    private ArrayList<RackoPlayer> setupPlayers(BufferedReader reader) {
        try {
            ArrayList<RackoPlayer> thePlayers;
            String numOfPlayers = "0";
            while (Integer.parseInt(numOfPlayers) < 2 || Integer.parseInt(numOfPlayers) > 4) {
                System.out.print("Choose the number of players [2-4]: ");
                numOfPlayers = reader.readLine();
            }

            thePlayers = new ArrayList<>();
            for (int x = 0; x < Integer.parseInt(numOfPlayers); x++) {
                System.out.print("Enter the name of player " + (x + 1) + ": ");
                String playerName = reader.readLine();
                if(playerName.isEmpty())
                    playerName = "Player" + (x+1);
                System.out.print("Should the player be a bot? [Y/n]: ");
                String playerBot = reader.readLine();
                playerBot = playerBot.toUpperCase();
                if(playerBot.isEmpty() || playerBot.equals("Y"))
                    thePlayers.add(new RackoPlayer(playerName, true));
                else
                    thePlayers.add(new RackoPlayer(playerName,false));
            }
            return thePlayers;
        }
        catch (IOException e) {
            System.out.println();
            System.out.println("ERROR: Wrong input, try again.");
            setupPlayers(reader);
        }
        catch (NumberFormatException e) {
            System.out.println();
            System.out.println("ERROR: Enter a number not a character, try again.");
            setupPlayers(reader);
        }
        System.out.println("Error! Try again.");
        return setupPlayers(reader);
    }

    /**
     * Asks the player if they would want to reset the game and start fresh.
     * @param reader The reader used to get input.
     * @return True if the player chooses "Y"
     */
    private boolean askResetGame(BufferedReader reader) {
        try {
            String option = "";
            while(!option.equals("Y") && !option.equals("N")) {
                System.out.print("Do you want to reset the game? [Y/n]: ");
                option = reader.readLine();
                option = option.toUpperCase();
            }

            return option.equals("Y") || option.isBlank();
        } catch (IOException e) {
            System.out.println();
            System.out.println("ERROR: Wrong input, try again.");
            return askResetGame(reader);
        }
    }

    /**
     * Runs the game and loops
     */
    public void run()  {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        outputStart(reader);

        do {
            theGame = new Racko(setupPlayers(reader));

            do {
                theGame.setupGame();
                theGame.takeTurns();
            } while (!theGame.getKillSignal());
        } while(askResetGame(reader));

    }
}
