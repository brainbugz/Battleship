package com.thecompany.battleship.models;

import com.thecompany.battleship.interfaces.Coordinate;

import java.util.Objects;

public class BattleshipCoordinate implements Coordinate<String, String> {
    private final String row;
    private final String column;

    public BattleshipCoordinate(String row, String column) {
        this.row = row;
        this.column = column;
    }

    public static BattleshipCoordinate of(String row, String column) {
        return new BattleshipCoordinate(row, column);
    }

    @Override
    public String getRow() {
        return row;
    }

    @Override
    public String getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BattleshipCoordinate that = (BattleshipCoordinate) o;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
