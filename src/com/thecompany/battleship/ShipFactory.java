package com.thecompany.battleship;

import com.thecompany.battleship.enums.ShipType;
import com.thecompany.battleship.interfaces.Ship;
import com.thecompany.battleship.models.ships.Battleship;
import com.thecompany.battleship.models.ships.Carrier;
import com.thecompany.battleship.models.ships.Cruiser;
import com.thecompany.battleship.models.ships.Destroyer;

import java.util.List;
import java.util.stream.Collectors;

public class ShipFactory {
    public static List<Ship> buildShips(List<ShipType> shipTypes) {
        return shipTypes.stream().map(shipType -> buildShip(shipType)).collect(Collectors.toList());
    }

    public static Ship buildShip(ShipType shipType) {
        switch (shipType) {
            case CARRIER:
                return new Carrier();
            case CRUISER:
                return new Cruiser();
            case DESTROYER:
                return new Destroyer();
            case BATTLESHIP:
                return new Battleship();
            default:
                return null;
        }
    }
}
