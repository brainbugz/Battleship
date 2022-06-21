package com.thecompany.battleship;

import com.thecompany.battleship.enums.Position;
import com.thecompany.battleship.interfaces.Coordinate;
import com.thecompany.battleship.interfaces.Player;
import com.thecompany.battleship.interfaces.Ship;
import com.thecompany.battleship.models.BattleshipCoordinate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class BattleshipBoard {
    private final int[][] board;
    private final Map<Coordinate, Pair<Coordinate, Ship>> boardMap;
    private final List<Ship> ships;

    private BattleshipBoard(int size, List<Ship> ships) {
        this.board = new int[size][size];
        this.boardMap = new HashMap<Coordinate, Pair<Coordinate, Ship>>();
        this.ships = ships;

        initBoard();
    }

    private void initBoard() {
        for(Ship ship: ships) {
            placeShip(ship);
        }
    }

    private void placeShip(Ship ship) {
        int shipLength = ship.getSize();
        Position position = getRandomShipPosition();

        Pair<Integer, Integer> startLocation = findLocationForShip(ship, position);

        final int finalRow = startLocation.x.intValue();
        final int finalCol = startLocation.y.intValue();

        if (position.name().equals(Position.HORIZONTAL.name())) {
            IntStream.range(0, shipLength).forEach(value -> {
                Coordinate coordinate = getCoordinateWithColumnOffset(finalRow, finalCol, value);
                boardMap.put(coordinate, new Pair<>(coordinate, ship));
            });
        } else {
            IntStream.range(0, shipLength).forEach(value -> {
                Coordinate coordinate = getCoordinateWithRowOffset(finalRow, finalCol, value);
                boardMap.put(coordinate, new Pair<>(coordinate, ship));
            });
        }
    }

    private Pair<Integer, Integer> findLocationForShip(Ship ship, Position position) {
        int shipLength = ship.getSize();

        int rowUpperBound = position.name().equals(Position.HORIZONTAL.name()) ? 10 : 10 - shipLength;
        int columnUpperBound = position.name().equals(Position.HORIZONTAL.name()) ? 10 - shipLength : 10;

        boolean canPlaceShip = false;
        int startRow = 0;
        int startColumn = 0;

        while (!canPlaceShip) {
            int finalStartRow = ThreadLocalRandom.current().nextInt(0, rowUpperBound);
            int finalStartColumn = ThreadLocalRandom.current().nextInt(1, columnUpperBound+1);

            if (position.name().equals(Position.HORIZONTAL.name())) {
                canPlaceShip = IntStream.range(0, shipLength)
                                .allMatch(
                                        value ->
                                        boardMap.get(getCoordinateWithColumnOffset(finalStartRow, finalStartColumn, value)) == null);
            } else {
                canPlaceShip = IntStream.range(0, shipLength)
                                .allMatch(value -> boardMap.get(getCoordinateWithRowOffset(finalStartRow, finalStartColumn, value)) == null);
            }
            startRow = finalStartRow;
            startColumn = finalStartColumn;
        }

        return new Pair<Integer, Integer>(Integer.valueOf(startRow), Integer.valueOf(startColumn));
    }

    private static Coordinate getCoordinateWithColumnOffset(int row, int col, int offset) {
        return BattleshipCoordinate.of(intToCharacter(row), String.valueOf(col + offset));
    }

    private static Coordinate getCoordinateWithRowOffset(int row, int col, int offset) {
        return BattleshipCoordinate.of(intToCharacter(row + offset), String.valueOf(col));
    }

    private static String intToCharacter(int offset) {
        return String.valueOf((char) ('A' + offset));
    }

    private Position getRandomShipPosition() {
        return Math.abs(ThreadLocalRandom.current().nextInt()) % 2 == 0 ? Position.HORIZONTAL : Position.VERTICAL;
    }

    public void attack(Player attackingPlayer, Coordinate coordinate) {
        logMessage(attackingPlayer.getName() + " is attacking coordinate: " + coordinate.toString());

        if(boardMap.containsKey(coordinate) && !boardMap.get(coordinate).x.visited())
            handleHit(attackingPlayer, coordinate);
        else
            handleMiss(attackingPlayer);
    }

    private void handleHit(Player attackingPlayer, Coordinate coordinate) {
        boardMap.get(coordinate).x.visit();
        boardMap.get(coordinate).y.damage();

        boolean isShipDestroyed = boardMap.get(coordinate).y.isDestroyed();
        if(isShipDestroyed) {
            ships.remove(boardMap.get(coordinate).y);
            logMessage(attackingPlayer.getName() + " DESTROYED enemy ship!!!!!!!!!");
        } else
            logMessage(attackingPlayer.getName() + " DAMAGED enemy ship!!!!!!!!!!");
    }

    private void handleMiss(Player attackingPlayer) {
        List<String> missStrings = List.of("The kingdom of North Korea joins you in the eternal war against the ocean.",
                "Here lies water. May it rest in peace.",
                "You're just one more move away from destroying even more water.",
                "Leviathan stirs due to your constant onslaught against his realm.",
                "Fantastic news!!! You've discovered water.");
        int missIndex = ThreadLocalRandom.current().nextInt(0, 5);
        logMessage(attackingPlayer.getName() + ": " + missStrings.get(missIndex));
        logMessage("");
    }

    private static void logMessage(String message) {
        System.out.println(message);
    }

    public boolean isGameOver() {
        return ships.isEmpty();
    }

    public void draw() {
        drawHeader();

        for(int i = 0; i < 10; i++) {
            System.out.print(intToCharacter(i));
            for(int j = 0; j < 10; j++) {
                Coordinate target = BattleshipCoordinate.of(intToCharacter(i), String.valueOf(j+1));
                Pair<Coordinate,Ship> pair = boardMap.get(target);

                System.out.print( pair != null && pair.x.visited() && pair.y.isDamaged() ? "*" : "_");
            }
            logMessage("");
        }
    }

    private void drawHeader() {
        IntStream.range(0, 11).forEach(value -> {
            if (value == 0) {
                System.out.print(" ");
            } else {
                System.out.print(value);
            }
        });
        logMessage("");
    }

    public static BattleshipBoardBuilder builder() {
        return new BattleshipBoardBuilder();
    }

    public static class BattleshipBoardBuilder {
        private int size;
        private List<Ship> ships;

        public BattleshipBoardBuilder setSize(int size) {
            this.size = size;
            return this;
        }

        public BattleshipBoardBuilder setShips(List<Ship> ships) {
            this.ships = ships;
            return this;
        }

        public BattleshipBoard build() {
            return new BattleshipBoard(size, ships);
        }
    }

    private static class Pair<X, Y> {
        public final X x;
        public final Y y;
        public Pair(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }
}
