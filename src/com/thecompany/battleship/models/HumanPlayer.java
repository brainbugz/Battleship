package com.thecompany.battleship.models;

import com.thecompany.battleship.interfaces.Coordinate;
import com.thecompany.battleship.interfaces.Player;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class HumanPlayer implements Player {
    private final Scanner scanInput;

    public HumanPlayer() {
        scanInput = new Scanner(System.in);
    }

    @Override
    public Coordinate getNextMove() {
        String nextMove = null;
        Pattern inputPattern = Pattern.compile("\\s*[a-jA-J]\\s*([1-9]|10)");
        System.out.println("Enter next move: ");

        while(nextMove == null) {
                try {
                    nextMove = (scanInput.next(inputPattern)).trim();
                    scanInput.nextLine();
                } catch (NoSuchElementException e) {
                    System.out.println("Invalid move. Try again. " + e.getMessage());
                    scanInput.nextLine();
                }
        }
        return new BattleshipCoordinate(nextMove.substring(0,1).toUpperCase(), nextMove.substring(1));
    }

    @Override
    public String getName() {
        return "Human player";
    }
}
