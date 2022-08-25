package com.sergioajara.racko;

public class PlayerOptions {
    private TurnOption turnOption;
    private int drawnOption;

    public PlayerOptions(TurnOption turnOp, int drawnOp) {
        turnOption = turnOp;
        drawnOption = drawnOp;
    }

    public TurnOption getTurnOption() {
        return turnOption;
    }

    public int getDrawnOption() {
            return drawnOption;
        }
}