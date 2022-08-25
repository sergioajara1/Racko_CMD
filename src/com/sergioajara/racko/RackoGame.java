package com.sergioajara.racko;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class RackoGame {

    private Racko theGame;
    private int numOfRound;

    public RackoGame() {}

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

    private ArrayList<RackoPlayer> setupPlayers(BufferedReader reader) {
        try {
            ArrayList<RackoPlayer> thePlayers;
            String numOfPlayers = "0";
            while (Integer.valueOf(numOfPlayers) < 2 || Integer.valueOf(numOfPlayers) > 4) {
                System.out.print("Choose the number of players [2-4]: ");
                numOfPlayers = reader.readLine();
            }

            thePlayers = new ArrayList<RackoPlayer>();
            for (int x = 0; x < Integer.valueOf(numOfPlayers); x++) {
                System.out.print("Enter the name of player " + (x + 1) + ": ");
                String playerName = reader.readLine();
                if(playerName.isEmpty())
                    playerName = "Player" + (x+1);
                System.out.print("Should the player be a bot? [Y/n]: ");
                String playerBot = reader.readLine();
                if(!playerBot.isEmpty() || !playerBot.equals("\n"))
                    playerBot = playerBot.toUpperCase().substring(0);
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

    public void run()  {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        outputStart(reader);

        theGame = new Racko(setupPlayers(reader));

        System.out.println("Starting the game, shuffling and distributing cards...");
        theGame.start();

        do {
            theGame.takeTurns();
        }while(theGame.getKillSignal());

        //Ask if player would like to play again... if so then reset variables and players
    }
}
