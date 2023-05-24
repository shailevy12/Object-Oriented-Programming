package src.gameobjects;

import danogl.util.Counter;
import src.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * representing the Brick of the game.
 */
public class Brick extends GameObject {

    private final CollisionStrategy collisionStrategy;
    private final Counter numOfBricks;


    /**
     * Construct a new Brick instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, CollisionStrategy
            collisionStrategy, Counter numOfBricks) {
        super(topLeftCorner, dimensions, renderable);
        this.numOfBricks = numOfBricks;
        this.collisionStrategy = collisionStrategy;
    }


    /**
     * handling with cases when there was a collision between the brick and other object of the game.
     *
     * @param other     the other object which the brick collide with.
     * @param collision object collision that handle with general cases of collisions.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other, numOfBricks);
    }
}
