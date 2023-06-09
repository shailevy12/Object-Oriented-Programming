package src.gameobjects;


// Imports

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * representing the Paddle of the game.
 */
public class Paddle extends GameObject {

    private static final float MOVEMENT_SPEED = 300;

    // Constants
    private final Vector2 windowDimensions;
    private final UserInputListener inputListener;
    private final int minDistanceFromEdge;

    /**
     * Construct a new Paddle instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param inputListener representing the user' listening to his actions.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 windowDimensions, int minDistanceFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.minDistanceFromEdge = minDistanceFromEdge;
        this.windowDimensions = windowDimensions;
        this.inputListener = inputListener;
    }

    /**
     * this function called many times during the game and updating this game object accordingly.
     * this function sets the speed and the direction of movement of the paddle according to the
     * user, and also handle with borders so the paddle won't get out of the window.
     *
     * @param deltaTime this function called each deltatime.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        paddleAwayFromBorder();
        paddleControlledByUser();

    }

    /**
     * Sets the velocity of the paddle according to the user's commands.
     */
    private void paddleControlledByUser() {
        Vector2 movementDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
    }

    /**
     * Checking that the paddle has not passed the borders, if it is the method will update the
     * place of the paddle in window.
     */
    private void paddleAwayFromBorder() {

        // Left border
        if (getTopLeftCorner().x() < minDistanceFromEdge) {
            setTopLeftCorner(new Vector2(minDistanceFromEdge, getTopLeftCorner().y()));
        }

        // Right border
        if (getTopLeftCorner().x() > windowDimensions.x() -
                minDistanceFromEdge - getDimensions().x()) {
            setTopLeftCorner(new Vector2(windowDimensions.x() -
                    minDistanceFromEdge - getDimensions().x(), getTopLeftCorner().y()));
        }
    }

}
