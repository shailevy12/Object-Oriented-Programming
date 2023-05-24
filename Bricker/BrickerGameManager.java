package src;

import src.brick_strategies.*;
import src.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

/**
 * manager of the Bricker game.
 */
public class BrickerGameManager extends GameManager {

    // constants

    private static final String WINDOW_TITLE = "Bricker";

    // messages
    private static final String LOST_MSG = "You lost! ";
    private static final String WIN_MSG = "You win! ";
    private static final String PLAY_AGAIN_MSG = "play again?";

    // paths
    private static final String BALL_IMAGE_PATH = "assets/ball.png";
    private static final String BACKGROUND_IMAGE_PATH = "assets/DARK_BG2_small.JPEG";
    private static final String HEART_IMAGE_PATH = "assets/heart.png";
    private static final String BRICK_IMAGE_PATH = "assets/brick.png";
    private static final String SOUND_FILE_PATH = "assets/blop_cut_silenced.wav";
    private static final String PADDLE_IMAGE_PATH = "assets/paddle.png";

    // ball constants
    private static final float BALL_SPEED = 200;
    private static final float BALL_WIDTH = 20;
    private static final float BALL_HEIGHT = 20;
    private static final float BALL_PLACEMENT_ON_WINDOW = 0.5f;

    // life counters constants
    private static final int NUM_OF_LIVES = 4;
    private static final float GRAPHIC_LIFE_COUNTER_WIDTH = 30;
    private static final float GRAPHIC_LIFE_COUNTER_HEIGHT = 25;
    private static final float NUMERIC_LIFE_COUNTER_WIDTH = 30;
    private static final float NUMERIC_LIFE_COUNTER_HEIGHT = 30;
    private static final float NUMERIC_DIST_FROM_WINDOW_WIDTH = 45;
    private static final float NUMERIC_DIST_FROM_WINDOW_HEIGHT = 85;
    private static final float GRAPHIC_DIST_FROM_WINDOW_WIDTH = 40;
    private static final float GRAPHIC_DIST_FROM_WINDOW_HEIGHT = 45;


    // brick constants
    private static final int NUM_OF_BRICKS_IN_LINE = 8;
    private static final int NUM_OF_BRICKS_IN_COL = 5;
    private static final float BRICK_HEIGHT = 15;

    // windows constants
    private static final float WINDOW_WIDTH = 700;
    private static final float WINDOW_HEIGHT = 500;

    // paddle constants
    private static final float PADDLE_WIDTH = 100;
    private static final float PADDLE_HEIGHT = 15;
    private static final float PADDLE_DIST_FROM_WINDOW_HEIGHT = 30;
    private static final int MIN_DIST_FROM_EDGE = 10;

    // borders constants
    public static final int BORDER_WIDTH = 20;
    private static final float DIST_FROM_BORDER = 5;


    // Fields
    private Counter numberOfBricks;
    private Counter lives;
    private Ball ball;
    private Vector2 windowDimensions;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;
    private WindowController windowController;

    /**
     * constructs a new instance of BrickerGameManager.
     *
     * @param windowTitle      the title of the game (in our case - Bricker)
     * @param windowDimensions the height and width of the game window.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }


    /**
     * initialize a new game, calling helper functions to create all the necessary objects to create
     * and play the game.
     *
     * @param imageReader      reading images paths and presenting them as objects.
     * @param soundReader      reading wav files and using them in the game, for example
     *                         when there is a collision.
     * @param inputListener    representing the user, listen to his actions and act accordingly.
     * @param windowController controller of the game window,
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        this.numberOfBricks = new Counter(0);
        this.imageReader = imageReader;
        this.windowDimensions = windowController.getWindowDimensions();
        this.lives = new Counter(NUM_OF_LIVES);
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;

        creatingBackground();
        creatingBorders();
        creatingGraphicLifeCounter();
        creatingNumericLifeCounter();
        creatingBall();
        creatingPaddle();
        creatingBricks();
    }

    /**
     * creating the bricks objects.
     */
    private void creatingBricks() {
        Renderable brickImage = imageReader.readImage(BRICK_IMAGE_PATH, false);

        for (int i = 0; i < NUM_OF_BRICKS_IN_LINE; i++) {

            for (int j = 0; j < NUM_OF_BRICKS_IN_COL; j++) {
                BrickStrategyFactory collisionStrategyFactory = new BrickStrategyFactory(gameObjects(),
                        this, imageReader, soundReader, inputListener,
                        windowController, windowDimensions);

                GameObject brick = new Brick(Vector2.ZERO, new Vector2((windowDimensions.x() - 2 *
                        BORDER_WIDTH - 2 * DIST_FROM_BORDER - (NUM_OF_BRICKS_IN_LINE - 1)) /
                        NUM_OF_BRICKS_IN_LINE, BRICK_HEIGHT), brickImage,
                        collisionStrategyFactory.getStrategy(), numberOfBricks);

                brick.setTopLeftCorner(
                        new Vector2(BORDER_WIDTH + DIST_FROM_BORDER + i * brick.getDimensions().x() + i,
                                BORDER_WIDTH + DIST_FROM_BORDER + j * brick.getDimensions().y() + j));


                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
                numberOfBricks.increment();
            }
        }
    }

    /**
     * creating the paddle object.
     */
    private void creatingPaddle() {
        Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE_PATH, true);

        GameObject paddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage, inputListener, windowDimensions, MIN_DIST_FROM_EDGE);

        paddle.setCenter(new Vector2(windowDimensions.x() / 2,
                windowDimensions.y() - PADDLE_DIST_FROM_WINDOW_HEIGHT));
        gameObjects().addGameObject(paddle);
    }

    /**
     * Creating the ball object.
     */
    private void creatingBall() {
        Renderable ballImage = imageReader.readImage(BALL_IMAGE_PATH, true);
        Sound collisionSound = soundReader.readSound(SOUND_FILE_PATH);
        this.ball = new Ball(Vector2.ZERO, new Vector2(BALL_WIDTH, BALL_HEIGHT), ballImage, collisionSound);
        gameObjects().addGameObject(ball);
        setVelOfBall();
    }

    /**
     * sets the velocity of the ball object.
     */
    private void setVelOfBall() {
        ball.setCenter(windowDimensions.mult(BALL_PLACEMENT_ON_WINDOW));
        float ballVelx = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            ballVelx *= -1;
        }
        if (rand.nextBoolean()) {
            ballVelY *= -1;
        }
        ball.setVelocity(new Vector2(ballVelx, ballVelY));
    }

    /**
     * sets the background of the game.
     */
    private void creatingBackground() {
        GameObject background = new GameObject(
                Vector2.ZERO, windowDimensions,
                imageReader.readImage(BACKGROUND_IMAGE_PATH, false));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /**
     * creating borders so the all objects will always stay in the window.
     */
    private void creatingBorders() {
        // left border
        gameObjects().addGameObject(new GameObject(Vector2.ZERO,
                new Vector2(BORDER_WIDTH, windowDimensions.y()), null));
        // upper border
        gameObjects().addGameObject(new GameObject(Vector2.ZERO,
                new Vector2(windowDimensions.x(), BORDER_WIDTH), null));
        // right border
        gameObjects().addGameObject(new GameObject(new Vector2(windowDimensions.x() - BORDER_WIDTH, 0),
                new Vector2(BORDER_WIDTH, windowDimensions.y()), null));
    }


    /**
     * creating the graphic life counter of the game
     */
    private void creatingGraphicLifeCounter() {
        GameObject graphicLifeCounter =
                new GraphicLifeCounter(new Vector2(windowDimensions.x() - GRAPHIC_DIST_FROM_WINDOW_WIDTH
                        , windowDimensions.y() - GRAPHIC_DIST_FROM_WINDOW_HEIGHT),
                        new Vector2(GRAPHIC_LIFE_COUNTER_WIDTH, GRAPHIC_LIFE_COUNTER_HEIGHT), lives,
                        imageReader.readImage(HEART_IMAGE_PATH, true),
                        gameObjects(), NUM_OF_LIVES);

        gameObjects().addGameObject(graphicLifeCounter, Layer.BACKGROUND);
    }


    /**
     * creating the numeric life number of the game.
     */
    private void creatingNumericLifeCounter() {
        GameObject numLivesObject = new NumericLifeCounter(lives, (new Vector2(windowDimensions.x() -
                NUMERIC_DIST_FROM_WINDOW_WIDTH, windowDimensions.y() - NUMERIC_DIST_FROM_WINDOW_HEIGHT)),
                new Vector2(NUMERIC_LIFE_COUNTER_WIDTH, NUMERIC_LIFE_COUNTER_HEIGHT), gameObjects());
        gameObjects().addGameObject(numLivesObject, Layer.BACKGROUND);
    }

    /**
     * checking if there was win or lost.
     * case 1: there was a win, which means that the number of bricks is zero.
     * case 2: the ball fell down but there are more lives, so we will decrease the number of lives and go
     * on the game.
     * case 3: the number of lives is zero which means there was a lost.
     * if the game was ended the function will call the method playAgain.
     */
    private void isGameEnded() {
        float ballHeight = ball.getCenter().y();
        if (numberOfBricks.value() <= 0) {
            playMoreGame(WIN_MSG);
        }
        if (lives.value() <= 0) {
            playMoreGame(LOST_MSG);
        }
        if (ballHeight > windowDimensions.y()) {
            lives.decrement();
            setVelOfBall();
        }
    }

    /**
     * asking the player if he wants to play again and acts accordingly.
     *
     * @param promt the string to show the user.
     */
    private void playMoreGame(String promt) {
        promt += PLAY_AGAIN_MSG;
        if (windowController.openYesNoDialog(promt)) {
            windowController.resetGame();
        } else {
            windowController.closeWindow();
        }
    }

    private void checkPuckGotOutOfWindow() {
        for (GameObject obj : gameObjects()) {
            if (obj.getClass() == Puck.class && obj.getCenter().y() > windowDimensions.y()) {
                gameObjects().removeGameObject(obj);
            }
        }
    }

    /**
     * this function called many times during the game and updating the game accordingly.
     * For now this function only checking if the game was ended, if there was a win or lost.
     *
     * @param deltaTime this function called each deltatime.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        isGameEnded();
        checkPuckGotOutOfWindow();
    }


    public static void main(String[] args) {
        new BrickerGameManager(WINDOW_TITLE, new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT)).run();
    }
}

