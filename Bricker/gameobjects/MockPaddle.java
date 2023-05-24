package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class MockPaddle extends Paddle {
    public static boolean isInstantiated;
    private final Counter counter;
    private final GameObjectCollection gameObjects;

    /**
     * Construct a new Paddle instance.
     *
     * @param topLeftCorner       Position of the object, in window coordinates (pixels).
     *                            Note that (0,0) is the top-left corner of the window.
     * @param dimensions          Width and height in window coordinates.
     * @param renderable          The renderable representing the object. Can be null, in which case
     * @param inputListener       representing the user' listening to his actions.
     * @param windowDimensions    Dimensions of the window.
     * @param minDistanceFromEdge Min Distance of Paddle from the edge.
     */
    public MockPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener
            inputListener, Vector2 windowDimensions, GameObjectCollection gameObjects,
                      int minDistanceFromEdge, int numCollisionsToDisappear) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions, minDistanceFromEdge);

        this.counter = new Counter(numCollisionsToDisappear);
        this.gameObjects = gameObjects;
    }


    /**
     * Handling with cases when there was a collision between the MockPaddle and other object of the game.
     * Checking if the counter equals to zero, then remove this mock paddle from the game objects collection.
     *
     * @param other     the other object which the clock collide with.
     * @param collision object collision that handle with general cases of collisions.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        counter.decrement();
        if (counter.value() == 0) {
            gameObjects.removeGameObject(this);
            isInstantiated = false;
        }
    }
}

