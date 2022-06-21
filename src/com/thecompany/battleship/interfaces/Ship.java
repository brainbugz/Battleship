package com.thecompany.battleship.interfaces;


import java.util.List;

public interface Ship {
    public int getSize();
//    public void setLocation(List<Coordinate> coordinates);
//    public List<Coordinate> getLocation();
    public boolean isDestroyed();
    public void damage();
//    public boolean isDamaged(Coordinate coordinate);
}
