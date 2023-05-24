package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Clock extends StatusDefiner {

    private static final float SLOW_TIMESCALE = 0.9f;
    private static final float QUICK_TIMESCALE = 1.1f;
    private final boolean typeOfClock;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public Clock(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 GameObjectCollection gameObjects, WindowController windowController, boolean typeOfClock) {
        super(topLeftCorner, dimensions, renderable, gameObjects, windowController);
        this.typeOfClock = typeOfClock;
    }


    /**
     * handling with cases when there was a collision between the clock and other object
     * (in our case can be only paddle) of the game.
     * Sets the timescale of the window according to the type of the clock and removes the clock( this
     * object) from the game objects' collection.
     *
     * @param other     the other object which the clock collide with.
     * @param collision object collision that handle with general cases of collisions.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (shouldCollideWith(other)) {
            if (typeOfClock) {
                windowController.setTimeScale(SLOW_TIMESCALE);
            } else {
                windowController.setTimeScale(QUICK_TIMESCALE);
            }
            gameObjects.removeGameObject(this);
        }
    }


}
