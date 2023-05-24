package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public interface CollisionStrategy {

    /**
     * @return the game object collection of the game.
     */
    GameObjectCollection getGameObjectCollection();

    /**
     * doing some behavior when two game objects collide.
     * @param thisObj this object
     * @param otherObj the object which thisObj collide with.
     */
    void onCollision(GameObject thisObj, GameObject otherObj, Counter counter);
}
