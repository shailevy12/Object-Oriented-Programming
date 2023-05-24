package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class GraphicLifeCounter extends GameObject {
    private static final float HEART_DIST_FROM_WINDOW_WIDTH = 40;

    // Fields
    private final Counter graphicLifeCounterOfThis;
    private final Counter graphicLifeCounterOfGame;
    private final GameObject[] heartWidgets;
    private final GameObjectCollection gameObjects;

    /**
     * Construct a new GraphicLifeCounter instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param livesCounter  The counter of lives in the game
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param gameObjects   The collection of all the objects in the game.
     * @param numOfLives    The number of lives at the beginning of the game.
     */
    public GraphicLifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Counter livesCounter,
                              Renderable renderable, GameObjectCollection gameObjects,
                              int numOfLives) {
        super(topLeftCorner, Vector2.ZERO, null);
        this.gameObjects = gameObjects;
        graphicLifeCounterOfGame = livesCounter;
        graphicLifeCounterOfThis = new Counter(numOfLives);
        heartWidgets = new GameObject[numOfLives];

        for (int i = 0; i < numOfLives; i++) {
            heartWidgets[i] = new GameObject(Vector2.ZERO, dimensions, renderable);
            heartWidgets[i].setTopLeftCorner(new Vector2(topLeftCorner.x() -
                    HEART_DIST_FROM_WINDOW_WIDTH * i, topLeftCorner.y()));
            gameObjects.addGameObject(heartWidgets[i], Layer.BACKGROUND);
        }
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
        if (graphicLifeCounterOfGame.value() < graphicLifeCounterOfThis.value()) {
            graphicLifeCounterOfThis.decrement();
            gameObjects.removeGameObject(heartWidgets[graphicLifeCounterOfThis.value()], Layer.BACKGROUND);
        }
    }
}
