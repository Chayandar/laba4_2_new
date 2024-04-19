package com.example.laba4_2_new;

import java.io.Serializable;

public class GameSettingsModel implements Serializable {
    private int numberOfPlayers;
    private int skipLimit;
    private int spinLimit;

    public GameSettingsModel(int numberOfPlayers, int skipLimit, int spinLimit) {
        this.numberOfPlayers = numberOfPlayers;
        this.skipLimit = skipLimit;
        this.spinLimit = spinLimit;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getSkipLimit() {
        return skipLimit;
    }

    public int getSpinLimit() {
        return spinLimit;
    }
}
