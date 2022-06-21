package com.thecompany.battleship;

import com.thecompany.battleship.enums.Position;
import com.thecompany.battleship.interfaces.Coordinate;
import com.thecompany.battleship.interfaces.Player;
import com.thecompany.battleship.interfaces.Ship;
import com.thecompany.battleship.models.BattleshipCoordinate;
import com.thecompany.battleship.models.BattleshipPlayer;
import com.thecompany.battleship.models.HumanPlayer;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class BattleshipBoard {
    private final int[][] board;
    private final Map<Coordinate, Ship> boardMap;
    private final List<Ship> ships;

    private BattleshipBoard(int size, List<Ship> ships) {
        this.board = new int[size][size];
        this.boardMap = new HashMap<Coordinate, Ship>();
        this.ships = ships;

        initBoard();
    }

    private void initBoard() {
        for(Ship ship: ships) {
            placeShip(ship);
        }

//        for(int i = 5; i >= 2; i--) {
//            Position position = Math.abs(ThreadLocalRandom.current().nextInt()) % 2 == 0 ? Position.HORIZONTAL : Position.VERTICAL;
//            int rowFudgeFactor = position.name().equals(Position.HORIZONTAL.name()) ? 10 : 11-i;
//            int startRow = Math.abs(ThreadLocalRandom.current().nextInt() % rowFudgeFactor);
//
//            int colFudgeFactor = position.name().equals(Position.HORIZONTAL.name()) ? 11-i : 10;
//            int startColumn = Math.abs(ThreadLocalRandom.current().nextInt() % colFudgeFactor);
//
//            if(position.name().equals(Position.HORIZONTAL.name())) {
//                for(int j = startColumn; j < startColumn+i; j++) {
//                    if(board[startRow][j] == 1) {
//                        i++;
//                        break;
//                    }
//                }
//            } else {
//                for(int j = startRow; j < startRow+i; j++) {
//                    if(board[j][startColumn] == 1) {
//                        i++;
//                        break;
//                    }
//                }
//            }
//        }
    }

    private void placeShip(Ship ship) {
        int shipLength = ship.getSize();
        Position position = getRandomShipPosition();

        int rowLimitConstant = position.name().equals(Position.HORIZONTAL.name()) ? 10 : 11 - shipLength;
        int columnLimitConstant = position.name().equals(Position.HORIZONTAL.name()) ? 11 - shipLength : 10;


        boolean canPlaceShip = false;
        int row = 0; int column = 0;

        while (!canPlaceShip) {
            int startRow = Math.abs(ThreadLocalRandom.current().nextInt() % rowLimitConstant);
            int startColumn = Math.abs(ThreadLocalRandom.current().nextInt() % columnLimitConstant);

            if (position.name().equals(Position.HORIZONTAL.name())) {
                canPlaceShip = IntStream.of(shipLength).allMatch(value -> boardMap.get(BattleshipCoordinate.of(String.valueOf('A' + startRow), String.valueOf(startColumn + value))) == null);
            } else {
                canPlaceShip = IntStream.of(shipLength).allMatch(value -> boardMap.get(BattleshipCoordinate.of(String.valueOf('A' + startRow + value), String.valueOf(startColumn))) == null);
            }
            row = startRow; column = startColumn;
        }

        final int finalRow = row; final int finalCol = column;
        if (position.name().equals(Position.HORIZONTAL.name())) {
            IntStream.of(shipLength).forEach(value -> boardMap.put(BattleshipCoordinate.of(String.valueOf('A' + finalRow), String.valueOf(finalCol + value)), ship));
        } else {
            IntStream.of(shipLength).forEach(value -> boardMap.put(BattleshipCoordinate.of(String.valueOf('A' + finalRow + value), String.valueOf(finalCol)), ship));
        }
    }

    private Position getRandomShipPosition() {
        return Math.abs(ThreadLocalRandom.current().nextInt()) % 2 == 0 ? Position.HORIZONTAL : Position.VERTICAL;
    }

    public boolean attack(Coordinate coordinate) {
        if(boardMap.containsKey(coordinate)) {
            boardMap.get(coordinate).damage();
            boolean isShipDestroyed = boardMap.get(coordinate).isDestroyed();
            if(isShipDestroyed) {
                ships.remove(boardMap.get(coordinate));
            }
            return isShipDestroyed;
        }

        return false;
    }

    public boolean isGameOver() {
        return ships.isEmpty();
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
}
