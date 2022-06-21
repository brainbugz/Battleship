package com.thecompany.battleship.interfaces;


public interface Ship {
    public int getSize();
    public boolean isDestroyed();
    public void damage();
    public boolean isDamaged();
}
