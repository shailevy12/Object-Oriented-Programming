import java.util.Random;

public class WhateverPlayer implements Player {

    /**
     * this function choose randomly place on board and tries to put a mark on this place using putMark
     * method until it finds a place which is valid.
     *
     * @param board the board of the game
     * @param mark  the mark of the current player
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        Random rand = new Random();
        int row = rand.nextInt(Board.SIZE);
        int col = rand.nextInt(Board.SIZE);
        while (!board.putMark(mark, row, col)) {
            row = rand.nextInt(Board.SIZE);
            col = rand.nextInt(Board.SIZE);
        }
    }

}

