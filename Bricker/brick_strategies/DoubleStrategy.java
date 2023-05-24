package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public class DoubleStrategy implements CollisionStrategy {

    private final CollisionStrategy collisionStrategy2;
    private final CollisionStrategy collisionStrategy1;

    /**
     * Constructs the Double strategy.
     * @param collisionStrategy1 one of the two strategies that the Double strategy has.
     * @param collisionStrategy2 one of the two strategies that the Double strategy has.
     */
    DoubleStrategy(CollisionStrategy collisionStrategy1, CollisionStrategy collisionStrategy2) {
        this.collisionStrategy1 = collisionStrategy1;
        this.collisionStrategy2 = collisionStrategy2;
    }


    /**
     * @return The collection of all game objects in the game.
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return collisionStrategy1.getGameObjectCollection();
    }


    /**
     * Handle with cases when two objects of the game had collided with each other.
     * In this case handle with cases when the brick collide with other GameObject.
     *
     * @param thisObj  the brick in our case
     * @param otherObj the object which the brick collide with
     * @param counter  the number of bricks in the game.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        collisionStrategy1.onCollision(thisObj, otherObj, counter);
        collisionStrategy2.onCollision(thisObj, otherObj, counter);
        // both of the methods calling are trying to remove the brick, only in success the counter
        // will be decreased.
    }
}
