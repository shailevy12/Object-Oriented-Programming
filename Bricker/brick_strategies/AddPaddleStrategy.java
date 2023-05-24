package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.MockPaddle;

public class AddPaddleStrategy extends RemoveBrickStrategyDecorator {
    // paddle parameters
    private static final String PADDLE_IMAGE_PATH = "assets/paddle.png";
    private static final float PADDLE_WIDTH = 100;
    private static final float PADDLE_HEIGHT = 15;
    private static final int MIN_DIST_FROM_EDGE = 15;
    private static final float PADDLE_PLACE_FACTOR_OF_WIND = 0.5f;
    private static final int NUM_OF_COLLISIONS_UNTIL_REMOVE = 3;

    // Fields
    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;


    /**
     * Constructs the adding mock paddle strategy.
     *
     * @param toBeDecorated    the collision strategy to decorate using RemoveBrickStrategyDecorator class.
     * @param imageReader      image reader to create renderable object of the GameObject to add.
     * @param inputListener    input listener so the paddle can be controlled by the user.
     * @param windowDimensions dimension of the window, so we can place the mock paddle to add.
     */
    AddPaddleStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader,
                      UserInputListener inputListener, Vector2 windowDimensions) {
        super(toBeDecorated);
        MockPaddle.isInstantiated = false;
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Handle with cases when two objects of the game had collided with each other.
     * In this case handle with cases when the brick collide with other GameObject, and
     * then adding a Mock paddle at the window until it collides NUM_OF_COLLISIONS_UNTIL_REMOVE
     * times with game objects.
     *
     * @param thisObj  the brick in our case
     * @param otherObj the object which the brick collide with
     * @param counter  the number of bricks in the game.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        if (!MockPaddle.isInstantiated) {
            creatingMockPaddle();
            MockPaddle.isInstantiated = true;
        }
    }

    /**
     * Private method for generating the paddle object.
     */
    private void creatingMockPaddle() {
        Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE_PATH, true);
        GameObject mockPaddle = new MockPaddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage, inputListener, windowDimensions, getGameObjectCollection(),
                MIN_DIST_FROM_EDGE, NUM_OF_COLLISIONS_UNTIL_REMOVE);
        mockPaddle.setCenter(windowDimensions.mult(PADDLE_PLACE_FACTOR_OF_WIND));
        getGameObjectCollection().addGameObject(mockPaddle);

    }


}