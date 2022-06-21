package com.thecompany.battleship.interfaces;

public interface Coordinate <X, Y> {
    public String getRow();
    public String getColumn();
    public void visit();
    public boolean visited();
}
