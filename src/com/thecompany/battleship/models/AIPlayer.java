package com.thecompany.battleship.models;

import com.thecompany.battleship.interfaces.Coordinate;
import com.thecompany.battleship.interfaces.Player;

import java.util.concurrent.ThreadLocalRandom;

public class AIPlayer implements Player {
    @Override
    public Coordinate getNextMove() {
        String rowCoordinate = String.valueOf((char)('A' + Math.abs(ThreadLocalRandom.current().nextInt() % 10)));
        String columnCoordinate = String.valueOf(Math.abs(ThreadLocalRandom.current().nextInt() % 10) + 1);
        return new BattleshipCoordinate(rowCoordinate, columnCoordinate);
    }

    @Override
    public String getName() {
        return "Computer player";
    }

}
