public class Board {

    private static final int LEFT = -1;
    private static final int RIGHT = 1;
    private static final int NO_MOVE = 0;
    public static final int SIZE = 6;
    public static final int WIN_STREAK = 4;

    public Mark[][] board;
    public Mark winner;
    public int emptyCellsInBoard;


    /**
     * constructor for the class
     */
    public Board() {
        this.emptyCellsInBoard = SIZE * SIZE;
        this.board = new Mark[SIZE][SIZE];
        this.winner = Mark.BLANK;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.board[i][j] = Mark.BLANK;
            }
        }
    }

    /**
     * put the given mark in (row, col) coordinates of the board.
     *
     * @param row  given row to put the mark on
     * @param col  given col to put the mark on
     * @param mark the mark to put in the (row,col) coordinates
     * @return true on success, false on failure.
     */
    public boolean putMark(Mark mark, int row, int col) {

        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            return false;
        }
        if (this.board[row][col] != Mark.BLANK) {
            return false;
        }
        this.board[row][col] = mark;
        this.emptyCellsInBoard--;
        checkingWin(row, col, mark);
        return true;
    }

    /**
     * @return true if the game ended (Draw or someone wins), false otherwise.
     */
    public boolean gameEnded() {
        return emptyCellsInBoard == 0 || winner != Mark.BLANK;
    }

    /**
     * @return X if player X wins
     * O if player O wins
     * BLACK if the game was ended in a draw.
     */
    public Mark getWinner() {
        return this.winner;
    }

    /**
     * gets place on the board to know which mark there is on this place (row,col)
     *
     * @param row (row,col) coordinate
     * @param col (row,col) coordinate
     * @return the mark that on those coordinates, BLANK if the coordinates are invalid.
     */
    public Mark getMark(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            return Mark.BLANK;
        }
        return this.board[row][col];
    }

    /**
     * receives place on the board and direction to go (left, right, diagonal) and returns the number of
     * the given mark in a row in this direction.
     *
     * @param row          (row, col) coordinate
     * @param col(row,col) coordinate
     * @param rowDelta     direction to go to horizontally
     * @param colDelta     direction to go to vertically
     * @param mark         the mark to check
     * @return The number of times there is the given mark in this direction, including the given position.
     */
    private int countMarkInDirection(int row, int col, int rowDelta, int colDelta, Mark mark) {
        int count = 0;
        while (row < SIZE && row >= 0 && col < SIZE && col >= 0 && board[row][col] == mark) {
            count++;
            row += rowDelta;
            col += colDelta;
        }
        return count;
    }

    /**
     * a private method called only by putMark method. checking if after we put mark on (row,col)
     * position, someone wins. doing it by checking the number of the given mark in all valid directions
     * (left, right, right diagonal, left diagonal). In case someone wins, the method will update the winner
     * field of the class.
     *
     * @param row  (row,col) coordinate
     * @param col  (row,col) coordinate
     * @param mark mark of the current player.
     */
    private void checkingWin(int row, int col, Mark mark) {
        int sumRow = countMarkInDirection(row, col, NO_MOVE, RIGHT, mark) +
                countMarkInDirection(row, col, NO_MOVE, LEFT, mark) - 1;

        int sumCol = countMarkInDirection(row, col, RIGHT, NO_MOVE, mark) +
                countMarkInDirection(row, col, LEFT, NO_MOVE, mark) - 1;

        int sumRightDiagonal = countMarkInDirection(row, col, RIGHT, LEFT, mark) +
                countMarkInDirection(row, col, LEFT, RIGHT, mark) - 1;

        int sumLeftDiagonal = countMarkInDirection(row, col, RIGHT, RIGHT, mark) +
                countMarkInDirection(row, col, LEFT, LEFT, mark) - 1;

        if (sumLeftDiagonal >= WIN_STREAK
                || sumRightDiagonal >= WIN_STREAK
                || sumCol >= WIN_STREAK
                || sumRow >= WIN_STREAK) {
            winner = mark;
        }
    }

}
