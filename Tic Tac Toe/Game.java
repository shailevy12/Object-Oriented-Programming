public class Game {

    public Player playerX;
    public Player playerO;
    public Renderer renderer;

    /**
     * constructor for this class
     *
     * @param playerX  player who plays with X mark
     * @param playerO  player who plays with O mark.
     * @param renderer renderer object to show the board.
     */
    public Game(Player playerX, Player playerO, Renderer renderer) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;

    }

    /**
     * the main method of this class.
     * keeps calling all the relevant methods until the game ended (in a draw or one of the players wins)
     *
     * @return Mark.BLANK if the game ended in a draw.
     * Mark.X if PlayerX wins
     * Mark.O if PlayerO wins.
     */
    public Mark run() {
        Board board = new Board();
        int counter = 0;
        renderer.renderBoard(board);
        while (!board.gameEnded()) {
            if (counter % 2 == 0) {
                playerX.playTurn(board, Mark.X);
            } else {
                playerO.playTurn(board, Mark.O);
            }
            counter++;
            this.renderer.renderBoard(board);
        }
        return board.getWinner();
    }

}
