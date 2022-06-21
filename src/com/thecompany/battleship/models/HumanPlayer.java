package com.thecompany.battleship.models;

import com.thecompany.battleship.BattleshipBoard;
import com.thecompany.battleship.interfaces.Coordinate;
import com.thecompany.battleship.interfaces.Player;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final Scanner scanInput;

    public HumanPlayer() {
        scanInput = new Scanner(System.in);
    }

    @Override
    public Coordinate getNextMove() {
        String nextMove = null;
        while(nextMove == null) {
            System.out.println("Enter next move: ");
            try {
                //ignore whitespace, allow any case
                nextMove = (scanInput.next("\\s*[a-jA-J]\\s*([1-9]|10)\\s*")).trim();
            } catch (NoSuchElementException e) {
                System.out.println("Invalid move. Try again.");
            }
        }
        return new BattleshipCoordinate(nextMove.substring(0,1), nextMove.substring(1));
    }
}
