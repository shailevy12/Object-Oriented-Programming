package src.brick_strategies;

// Imports

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import src.BrickerGameManager;

import java.util.Random;

public class BrickStrategyFactory {

    private static final int ALL_STRATEGIES_NUM = 6;
    private static final int BASIC_STRATEGIES_NUM = 5;


    // Fields
    private final GameObjectCollection gameObjects;
    private final BrickerGameManager gameManager;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final UserInputListener inputListener;
    private final WindowController windowController;
    private final Vector2 windowDimensions;


    /**
     * constructs a Brick Strategy Factory object.
     *
     * @param gameObjects      the collections of all game objects in the game.
     * @param imageReader      reading images paths and presenting them as objects.
     * @param soundReader      reading wav files and using them in the game, for example
     *                         when there is a collision.
     * @param inputListener    representing the user, listen to his actions and act accordingly.
     * @param windowController controller of the game window
     * @param windowDimensions dimensions of the window
     */
    public BrickStrategyFactory(GameObjectCollection gameObjects, BrickerGameManager gameManager,
                                ImageReader imageReader, SoundReader soundReader,
                                UserInputListener inputListener, WindowController windowController,
                                Vector2 windowDimensions) {
        this.gameObjects = gameObjects;
        this.gameManager = gameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;

    }


    /**
     * method randomly selects between 5 strategies and returns one CollisionStrategy object which
     * is a RemoveBrickStrategy decorated by one of the decorator strategies, or decorated by two
     * randomly selected strategies, or decorated by one of the decorator strategies and a pair
     * of additional two decorator strategies using private helper method build()
     *
     * @return CollisionStrategy to fit some brick.
     */
    public CollisionStrategy getStrategy() {
        return build(ALL_STRATEGIES_NUM, false);
    }


    /**
     * Create collision Strategy for some brick in the game.
     *
     * @param bound                 pick randomly a number between 0-bound.
     * @param doubleStrategyCreated true if Double Strategy already created,false otherwise.
     * @return new instance of Collision Strategy.
     */
    private CollisionStrategy build(int bound, boolean doubleStrategyCreated) {

        Random rand = new Random();
        int result = Math.abs(rand.nextInt(bound));

        switch (result) {

            case 0:
                return new RemoveBrickStrategy(gameObjects);

            case 1:
                return new AddPaddleStrategy(new RemoveBrickStrategy(gameObjects), imageReader,
                        inputListener, windowDimensions);

            case 2:
                return new PuckStrategy(new RemoveBrickStrategy(gameObjects), imageReader, soundReader);

            case 3:
                return new ChangeCameraStrategy(new RemoveBrickStrategy(gameObjects),
                        windowController, gameManager);

            case 4:
                return new ChangePaceOfGameStrategy(new RemoveBrickStrategy(gameObjects),
                        windowController, imageReader);

            case 5:
                CollisionStrategy collisionStrategy1 = build(BASIC_STRATEGIES_NUM, true);
                CollisionStrategy collisionStrategy2;
                if (doubleStrategyCreated) {
                    collisionStrategy2 = build(BASIC_STRATEGIES_NUM, true);
                } else {
                    collisionStrategy2 = build(ALL_STRATEGIES_NUM, true);
                }
                return new DoubleStrategy(collisionStrategy1, collisionStrategy2);
        }
        return null;
    }
}



