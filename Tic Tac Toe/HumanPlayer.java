import java.util.Scanner;

public class HumanPlayer implements Player {


    static final private String INVALID_INPUT_MSG = "Invalid coordinates, type again: ";
    static final private String ASKING_INPUT_MSG = "Player %s, type coordinates:  ";

    /**
     * default constructor for the class
     */
    public HumanPlayer() {
    }

    /**
     * this func gets input from the user and calling putMark method until it will return true, which
     * means that input is valid and the player played his turn.
     *
     * @param board the board of the game
     * @param mark  the mark of the current player
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        int[] coordinates = gettingInputFromUser(mark);
        while (!board.putMark(mark, coordinates[0], coordinates[1])) {
            System.out.println(INVALID_INPUT_MSG);
            coordinates = gettingInputFromUser(mark);
        }
    }

    /**
     * helper private method for playTurn that getting input from user puts (row,col) in coordinate array.
     *
     * @return coordinate - [row,col]
     */
    private int[] gettingInputFromUser(Mark mark) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(String.format(ASKING_INPUT_MSG, mark));
        int input = scanner.nextInt();
        int row = input / 10 - 1;
        int col = input % 10 - 1;
        return new int[]{row, col};
    }
}
