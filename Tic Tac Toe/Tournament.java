import java.lang.reflect.Array;

public class Tournament {

    private static final int NUM_VALID_OF_ARGS = 4;
    private static final String END_TOURNAMENT_MSG = "=== player 1: %d | player 2: %d | Draws: %d ===\r";

    public int rounds;
    public Renderer renderer;
    public Player[] players;

    /**
     * the constructor of the class
     *
     * @param rounds   round's number of this tournament, cannot be negative
     * @param renderer the renderer to show the tournament
     * @param players  players of the tournament, cannot be null
     */
    public Tournament(int rounds, Renderer renderer, Player[] players) {
        this.rounds = rounds;
        this.renderer = renderer;
        this.players = players;
    }

    /**
     * this method responsible for connects between the all classes.
     * it creates new game and switch between the players mark at each round.
     * also counting the number of wins for each player and number of draws.
     * at the end printing the score table of this tournament.
     */
    public void playTournament() {
        Mark winner;
        Game game;
        int[] scoreTable = {0, 0, 0};
        for (int i = 0; i < rounds; i++) {
            game = new Game(players[i % 2], players[Math.abs(i % 2 - 1)], renderer);
            winner = game.run();
            switch (winner) {
                case X:
                    scoreTable[i % 2]++;
                    break;
                case O:
                    scoreTable[Math.abs(i % 2 - 1)]++;
                    break;
                case BLANK:
                    scoreTable[2]++;
                    break;
            }
        }
        System.out.println(String.format(END_TOURNAMENT_MSG,
                scoreTable[0], scoreTable[1], scoreTable[2]));
    }


    public static void main(String[] args) {
        if (Array.getLength(args) != NUM_VALID_OF_ARGS) {
            return;
        }
        int rounds = Integer.parseInt(args[0]);
        PlayerFactory playerFactory = new PlayerFactory();
        Player player1 = playerFactory.buildPlayer(args[2]);
        Player player2 = playerFactory.buildPlayer(args[3]);
        RendererFactory rendererFactory = new RendererFactory();
        Renderer renderer = rendererFactory.buildRenderer(args[1]);
        if (player1 == null || player2 == null || renderer == null || rounds <= 0) {
            return;
        }

        Player[] players = {player1, player2};
        Tournament tournament = new Tournament(rounds, renderer, players);
        tournament.playTournament();
    }
}
