package com.thecompany.battleship.models;

import com.thecompany.battleship.BattleshipBoard;
import com.thecompany.battleship.interfaces.Coordinate;
import com.thecompany.battleship.interfaces.Player;

import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

public class AIPlayer implements Player {
    @Override
    public Coordinate getNextMove() {
        String rowCoordinate = String.valueOf((char)('a' + Math.abs(ThreadLocalRandom.current().nextInt() % 10)));
        String columnCoordinate = String.valueOf(Math.abs(ThreadLocalRandom.current().nextInt() % 10) + 1);
        return new BattleshipCoordinate(rowCoordinate, columnCoordinate);
    }

}
