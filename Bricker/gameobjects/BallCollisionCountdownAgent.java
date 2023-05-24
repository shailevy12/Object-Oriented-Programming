package src.gameobjects;

// Imports

import danogl.GameObject;
import danogl.util.Vector2;
import src.brick_strategies.ChangeCameraStrategy;

public class BallCollisionCountdownAgent extends GameObject {

    // Fields
    private final int firstBallCollisionCounter;
    private final ChangeCameraStrategy cameraOwner;
    private final Ball ball;
    private final int countDownValue;

    /**
     * Construct a new BallCollisionCountAgent instance.
     */
    public BallCollisionCountdownAgent(Ball ball, ChangeCameraStrategy owner, int countDownValue) {
        super(Vector2.ZERO, Vector2.ZERO, null); // should not be rendered.
        this.ball = ball;
        this.firstBallCollisionCounter = ball.getCollisionCount();
        this.cameraOwner = owner;
        this.countDownValue = countDownValue;
    }

    /**
     * This function called many times during the game and updating this game object accordingly.
     * Checking if the ball had collided with other game objects for countDownValue times, and
     * acts accordingly.
     *
     * @param deltaTime this function called each deltatime.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (ball.getCollisionCount() == firstBallCollisionCounter + countDownValue) {
            cameraOwner.turnOffCameraChange();
        }
    }
}
