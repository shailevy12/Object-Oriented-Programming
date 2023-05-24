package src.brick_strategies;

// imports
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Clock;
import src.gameobjects.StatusDefiner;

import java.util.Random;


public class ChangePaceOfGameStrategy extends RemoveBrickStrategyDecorator {

    // Paths
    private static final String RED_CLOCK_PATH = "assets/quicken.png";
    private static final String GREEN_CLOCK_PATH = "assets/slow.png";

    // Clock parameters
    private static final float CLOCK_HEIGHT = 28.2f;
    private static final float CLOCK_WIDTH = 54;
    private static final float CLOCK_SPEED = 170;

    // Window timescale parameters
    private static final float QUICK_TIME_SCALE = 1.1f;
    private static final float SLOW_TIME_SCALE = 0.9f;

    // Fields
    private final ImageReader imageReader;
    private final WindowController windowController;


    /**
     * Constructs the changing pace of game strategy.
     *
     * @param toBeDecorated    the collision strategy to decorate using RemoveBrickStrategyDecorator class.
     * @param windowController window controller of the game
     * @param imageReader      image reader to create renderable object of the GameObject to add.
     */
    ChangePaceOfGameStrategy(CollisionStrategy toBeDecorated,
                             WindowController windowController, ImageReader imageReader) {
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.windowController = windowController;
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
        super.onCollision(thisObj, otherObj, counter);

        Renderable clockImage = null;
        Random rand = new Random();
        boolean typeOfClock = rand.nextBoolean();


        if (windowController.getTimeScale() == QUICK_TIME_SCALE) {
            typeOfClock = true; // Green clock
            clockImage = imageReader.readImage(GREEN_CLOCK_PATH, true);
        } else if (windowController.getTimeScale() == SLOW_TIME_SCALE) {
            typeOfClock = false; // Red clock
            clockImage = imageReader.readImage(RED_CLOCK_PATH, true);
        }

        creatingClock(thisObj, clockImage, typeOfClock);
    }


    /**
     * Private method for generating the clock object.
     *
     * @param brick       the brick object which just has been break.
     * @param clockImage  the Renderable object of the clock
     * @param typeOfClock the type of clock (slow clock or quicken clock)
     */
    private void creatingClock(GameObject brick, Renderable clockImage, boolean typeOfClock) {
        StatusDefiner clock = new Clock(Vector2.ZERO, new Vector2(CLOCK_WIDTH, CLOCK_HEIGHT),
                clockImage, getGameObjectCollection(), windowController, typeOfClock);

        clock.setCenter(new Vector2(brick.getCenter().x(),
                brick.getTopLeftCorner().y() + (CLOCK_WIDTH / 2)));
        clock.setSpeed(CLOCK_SPEED);
        getGameObjectCollection().addGameObject(clock);
    }
}


