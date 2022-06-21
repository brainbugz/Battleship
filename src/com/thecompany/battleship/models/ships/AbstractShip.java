package com.thecompany.battleship.models.ships;

import com.thecompany.battleship.interfaces.Ship;

public abstract class AbstractShip implements Ship {
    private int health;

    public AbstractShip() {
        this.health = getSize();
    }

    @Override
    public boolean isDestroyed() {
        return health <= 0;
    }

    @Override
    public void damage() {
        health--;
    }

    @Override
    public boolean isDamaged() {
        return health < getSize();
    }
}
