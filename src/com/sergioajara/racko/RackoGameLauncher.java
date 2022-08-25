package com.sergioajara.racko;

public class RackoGameLauncher {
    private static RackoGame theGame;

    public static void main(String[] args) {
        theGame = new RackoGame();
        theGame.run();
    }
}
