package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class RemoveBrickStrategy implements CollisionStrategy {

    /**
     * handling cases when there was collision between two game objects.
     */
    private final GameObjectCollection gameObjects;

    /**
     * the constructor of the class.
     * @param gameObjects the collection of the game objects.
     */
    public RemoveBrickStrategy(GameObjectCollection gameObjects) {
        this.gameObjects = gameObjects;
    }

    /**
     * @return The collection of all game objects in the game.
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return gameObjects;
    }

    /**
     * This function called when there was a collision between two game objects.
     * it gets two game objects and then remove thisObj from the game.
     * @param thisObj the object to remove from the game.
     * @param otherObj the other object that collided with thisObj.
     */
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        if(gameObjects.removeGameObject(thisObj, Layer.STATIC_OBJECTS)) {
            counter.decrement();
        }
    }
}
