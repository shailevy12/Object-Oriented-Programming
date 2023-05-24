public interface Player {

    /**
     * general method which plays the current player turn. this method will be implements by each class
     * according to the type of the player.
     *
     * @param board the board to play on.
     * @param mark  the mark of the current player.
     */
    void playTurn(Board board, Mark mark);
}
