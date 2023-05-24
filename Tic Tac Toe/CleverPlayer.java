public class CleverPlayer implements Player {

    /**
     * this function goes row by row on the board and tries to put the given mark on the board until
     * it finds a place which is valid.
     *
     * @param board the board of the game
     * @param mark  the mark of the current player
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        for (int i = 0; i < Board.SIZE * 10 + Board.SIZE; i++) { // doing use only in one loop because it
                                                                 // is easier to use break that way.
            int row = i / 10;
            int col = i % 10;
            if (board.putMark(mark, row, col)) {
                break;
            }
        }
    }
}
