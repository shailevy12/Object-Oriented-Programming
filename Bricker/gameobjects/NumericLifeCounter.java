package src.gameobjects;

// Imports

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class NumericLifeCounter extends GameObject {
    private final GameObjectCollection gameObjects;
    private final Vector2 dimensions;
    private final Vector2 topLeftCorner;
    private final Counter numericLifeCounterOfThis;
    private final Counter numericLifeCounterOfGame;
    private GameObject numericLifeCounterObject;

    /**
     * Construct a new NumericLifeCounter instance.
     *
     * @param livesCounter  the lives counter of the game
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param gameObjects   The collection of the objects in the game
     */
    public NumericLifeCounter(Counter livesCounter, Vector2 topLeftCorner, Vector2 dimensions,
                              GameObjectCollection gameObjects) {
        super(topLeftCorner, Vector2.ZERO, null);

        this.gameObjects = gameObjects;
        this.numericLifeCounterOfThis = new Counter(livesCounter.value());
        this.numericLifeCounterOfGame = livesCounter;
        this.dimensions = dimensions;
        this.topLeftCorner = topLeftCorner;

        numericLifeCounterObject = new GameObject(topLeftCorner, dimensions,
                new TextRenderable(Integer.toString(numericLifeCounterOfThis.value())));
        gameObjects.addGameObject(numericLifeCounterObject, Layer.BACKGROUND);

    }

    /**
     * this function called many times during the game and updating the game accordingly.
     * The function checking if there was decrement in the counter of lives in the game, and displays
     * accordingly the true number of lives on the window,
     *
     * @param deltaTime this function called each deltatime.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (numericLifeCounterOfGame.value() < numericLifeCounterOfThis.value()) {
            numericLifeCounterOfThis.decrement();
            gameObjects.removeGameObject(numericLifeCounterObject, Layer.BACKGROUND);
            numericLifeCounterObject = new GameObject(topLeftCorner, dimensions,
                    new TextRenderable(Integer.toString(numericLifeCounterOfThis.value())));
            gameObjects.addGameObject(numericLifeCounterObject, Layer.BACKGROUND);

        }
    }
}

