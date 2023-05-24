package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public abstract class StatusDefiner extends GameObject {

    protected final WindowController windowController;
    protected final GameObjectCollection gameObjects;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public StatusDefiner(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                         GameObjectCollection gameObjects, WindowController windowController) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjects = gameObjects;
        this.windowController = windowController;
    }


    /**
     * @param other the other object which thisObject collide with.
     * @return true if something suppose to happen when the StatusDefiner collides with other,
     * false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return (other instanceof Paddle);

    }

    /**
     * This function called many times during the game and updating this game object accordingly.
     * Checks if the StatusDefiner got out of the window, and then remove this from the collection of
     * all the game objects.
     *
     * @param deltaTime this function called each deltatime.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.getCenter().y() > windowController.getWindowDimensions().y()) {
            gameObjects.removeGameObject(this);
        }
    }

    /**
     * The direction is always down, so we only need to set the speed.
     * @param speed the speed to be set.
     */
    public void setSpeed(float speed) {
        this.setVelocity(Vector2.DOWN.mult(speed));
    }
}
