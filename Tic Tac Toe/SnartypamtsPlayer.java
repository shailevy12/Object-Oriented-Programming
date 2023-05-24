public class SnartypamtsPlayer implements Player {

    /**
     * this function goes col by col (start from the second col) on the board and tries to put
     * the given mark on the board until it finds a place which is valid.
     *
     * @param board the board to play on.
     * @param mark  the mark of the current player.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        for (int i = 0; i < Board.SIZE * 10 + Board.SIZE; i++) {
            int row = i % 10;
            int col = i / 10 + 1;
            if (board.putMark(mark, row, col)) {
                break;

            }
        }
    }
}

