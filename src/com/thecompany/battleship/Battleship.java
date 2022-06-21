package com.thecompany.battleship;

import com.thecompany.battleship.enums.ShipType;
import com.thecompany.battleship.interfaces.Coordinate;
import com.thecompany.battleship.interfaces.Player;
import com.thecompany.battleship.models.*;

import java.util.List;
import java.util.Map;

public class Battleship {
    public static void main(String[] args) {

    }

    public Battleship(int grid) {
        GameManager gameManager =
                GameManager.builder().playerOne(new HumanPlayer())
                .playerTwo(new AIPlayer())
                .playerOneBoard(BattleshipBoard.builder().setSize(grid).setShips(ShipFactory.buildShips(List.of(ShipType.BATTLESHIP, ShipType.CARRIER, ShipType.CRUISER, ShipType.DESTROYER))).build())
                .playerTwoBoard(BattleshipBoard.builder().setSize(grid).setShips(ShipFactory.buildShips(List.of(ShipType.BATTLESHIP, ShipType.CARRIER, ShipType.CRUISER, ShipType.DESTROYER))).build())
                .build();
        enterGameLoop(gameManager);
    }

    private void enterGameLoop(GameManager gameManager) {
        while(true) {
            gameManager.playerBoards.keySet().forEach(player -> {
                Coordinate move = player.getNextMove();
                gameManager.playerBoards.get(player).attack(move);
            });
        }
    }

    private static class GameManager{
        private final Map<Player, BattleshipBoard> playerBoards;

        private GameManager(Player player1, Player player2, BattleshipBoard playerOneBoard, BattleshipBoard playerTwoBoard) {
            playerBoards = Map.of(player1, playerOneBoard, player2, playerTwoBoard);
        }

        public static GameManagerBuilder builder() {
            return new GameManagerBuilder();
        }
    }

    private static class GameManagerBuilder {
        private Player player1;
        private Player player2;
        private BattleshipBoard playerOneBoard;
        private BattleshipBoard playerTwoBoard;
        private GameManagerBuilder() {

        }

        public GameManagerBuilder playerOne(Player playerOne) {
            this.player1 = playerOne;
            return this;
        }

        public GameManagerBuilder playerTwo(Player playerTwo) {
            this.player2 = playerTwo;
            return this;
        }

        public GameManagerBuilder playerOneBoard(BattleshipBoard playerOneBoard) {
            this.playerOneBoard = playerOneBoard;
            return this;
        }

        public GameManagerBuilder playerTwoBoard(BattleshipBoard playerTwoBoard) {
            this.playerTwoBoard = playerTwoBoard;
            return this;
        }

        public GameManager build() {
            return new GameManager(player1, player2, playerOneBoard, playerTwoBoard);
        }
    }
}
