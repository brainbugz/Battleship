package com.thecompany.battleship;

import com.thecompany.battleship.enums.ShipType;
import com.thecompany.battleship.interfaces.Coordinate;
import com.thecompany.battleship.interfaces.Player;
import com.thecompany.battleship.models.AIPlayer;
import com.thecompany.battleship.models.HumanPlayer;

import java.util.List;

public class BattleshipGame {
    public static void main(String[] args) {
        List<String> introText = List.of( "----------------------------------------------------------------------------------",
                                          "Welcome to Battleship!",
                                          "The objective is to destroy all hidden enemy ships before they destroy yours.",
                                          "To play, simply enter coordinates (A-J, 1-10) to attack your enemy.",
                                          "----------------------------------------------------------------------------------");
        introText.forEach(System.out::println);
        BattleshipGame battleshipGame = new BattleshipGame(10);


    }

    public BattleshipGame(int grid) {
        GameManager gameManager =
                GameManager.builder()
                .playerOne(new HumanPlayer())
                .playerTwo(new AIPlayer())
                .playerOneBoard(BattleshipBoard.builder()
                                    .setSize(grid)
                                    .setShips(ShipFactory.buildShips(List.of(ShipType.BATTLESHIP, ShipType.CARRIER, ShipType.CRUISER, ShipType.DESTROYER)))
                                    .build())
                .playerTwoBoard(BattleshipBoard.builder()
                                    .setSize(grid)
                                    .setShips(ShipFactory.buildShips(List.of(ShipType.BATTLESHIP, ShipType.CARRIER, ShipType.CRUISER, ShipType.DESTROYER)))
                                    .build())
                .build();

        enterGameLoop(gameManager);
    }

    private void enterGameLoop(GameManager gameManager) {
        while(!gameManager.isGameOver()) {
            gameManager.playerOneAttack();
            gameManager.playerTwoAttack();
        }
        System.out.println(gameManager.playerTwoBoard.isGameOver() ? gameManager.player1.getName() + " WINS!!!!" : gameManager.player2.getName() + " WINS!!!!");
    }

    private static class GameManager{
        private Player player1;
        private Player player2;
        private BattleshipBoard playerOneBoard;
        private BattleshipBoard playerTwoBoard;

        private GameManager(Player player1, Player player2, BattleshipBoard playerOneBoard, BattleshipBoard playerTwoBoard) {
            this.player1 = player1;
            this.player2 = player2;
            this.playerOneBoard = playerOneBoard;
            this.playerTwoBoard = playerTwoBoard;
        }

        public void playerOneAttack() {
            Coordinate playerOneMove = player1.getNextMove();
            playerTwoBoard.attack(player1, playerOneMove);
            playerTwoBoard.draw();
        }

        public void playerTwoAttack() {
            Coordinate playerTwoMove = player2.getNextMove();
            playerOneBoard.attack(player2, playerTwoMove);
        }

        public boolean isGameOver() {
            return playerTwoBoard.isGameOver() || playerOneBoard.isGameOver();
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
