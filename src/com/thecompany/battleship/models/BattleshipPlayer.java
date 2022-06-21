package com.thecompany.battleship.models;

import com.thecompany.battleship.BattleshipBoard;
import com.thecompany.battleship.interfaces.Player;

public abstract class BattleshipPlayer implements Player {
    private final BattleshipBoard board;

    private BattleshipPlayer() {
        throw new AssertionError("private default constructor; stop using reflection to call me");
    }

    public BattleshipPlayer(BattleshipBoard board) {
        this.board = board;
    }

    public BattleshipBoard getBoard() {
        return board;
    }
}
