package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;


/**
 * representing the ball of the game.
 */

public class Ball extends GameObject {

    private final Counter ballNumOfCollisions;
    private final Sound collisionSound;


    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.ballNumOfCollisions = new Counter(0);
    }

    /**
     * handling with cases when there was a collision between the ball and other object of the game.
     * set the new velocity of the ball the flip its direction. for example: when the ball hits one of the
     * borders, or when it hits the paddle.
     * @param other the other object which the ball collide with.
     * @param collision object collision that handle with general cases of collisions.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        ballNumOfCollisions.increment();
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
    }

    /**
     * Ball object maintains a counter which keeps count of collisions from start of game.
     * This getter method allows access to the collision count in case any behavior should need to be
     * based on number of ball collisions.
     * @return number of times ball collided with an object since start of game
     */
    public int getCollisionCount() {
        return ballNumOfCollisions.value();
    }

}
