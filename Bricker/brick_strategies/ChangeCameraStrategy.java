package src.brick_strategies;

// Imports

import danogl.GameObject;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.BallCollisionCountdownAgent;

public class ChangeCameraStrategy extends RemoveBrickStrategyDecorator {

    private static final int COLLISIONS_NUM_UNTIL_CAMERA_TURN_OFF = 4;
    private static final float CAMERA_SET_ON_BALL_FACTOR = 1.2f;

    // Fields
    private final BrickerGameManager gameManager;
    private final WindowController windowController;

    /**
     * Constructs the Change Camera Strategy of the brick.
     *
     * @param toBeDecorated    the Collision Strategy to be Decorated
     * @param windowController the window controller of the game
     * @param gameManager      the Bricker game manager.
     */
    ChangeCameraStrategy(CollisionStrategy toBeDecorated, WindowController windowController,
                         BrickerGameManager gameManager) {
        super(toBeDecorated);
        this.gameManager = gameManager;
        this.windowController = windowController;
    }

    /**
     * Handle with cases when two objects of the game had collided with each other.
     * In this case handle with cases when the brick collide with other GameObject, and
     * then changing the camera to focus on the main ball of the game until it hits
     * COLLISIONS_NUM_UNTIL_CAMERA_TURN_OFF times.
     *
     * @param thisObj  the brick in our case
     * @param otherObj the object which the brick collide with
     * @param counter  the number of bricks in the game.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        if (gameManager.getCamera() == null) {

            GameObject ball = getTheBallObject();

            if (ball != null) {
                gameManager.setCamera(new Camera(ball, ball.getCenter(),
                        windowController.getWindowDimensions().mult(CAMERA_SET_ON_BALL_FACTOR),
                        windowController.getWindowDimensions()));

                GameObject ballCollisionCountdownAgent = new BallCollisionCountdownAgent((Ball) ball,
                        this, COLLISIONS_NUM_UNTIL_CAMERA_TURN_OFF);
                getGameObjectCollection().addGameObject(ballCollisionCountdownAgent);

            }
        }
    }

    /**
     * private method to find the ball in the gameObjects collection.
     *
     * @return the ball object.
     */
    private GameObject getTheBallObject() {
        for (GameObject obj : getGameObjectCollection()) {
            if (obj.getClass() == Ball.class) {
                return obj;
            }
        }
        return null; // will never return null cause the ball always exists.
    }


    /**
     * return the camera not to focus on any object.
     */
    public void turnOffCameraChange() {
        gameManager.setCamera(null);
    }
}
