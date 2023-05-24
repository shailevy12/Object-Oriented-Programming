package src.brick_strategies;


// Imports

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Puck;

import java.util.Random;

public class PuckStrategy extends RemoveBrickStrategyDecorator {


    // Puck parameters.
    private static final String PUCK_IMAGE_PATH = "assets/mockBall.png";
    private static final String SOUND_FILE_PATH = "assets/blop_cut_silenced.wav";
    private static final float PUCK_SPEED = 200;
    private static final int NUM_OF_PUCKS = 3;

    // Fields
    private final ImageReader imageReader;
    private final SoundReader soundReader;

    /**
     * Constructs the Puck Strategy of the brick.
     *
     * @param toBeDecorated the Collision Strategy to be Decorated
     * @param imageReader   image Reader which create the Renderable object of the puck
     * @param soundReader   sound Reader which create the sound when puck collide with other object.
     */
    PuckStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader, SoundReader soundReader) {
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.soundReader = soundReader;
    }


    /**
     * Handle with cases when two objects of the game had collided with each other.
     * In this case handle with cases when the brick collide with other GameObject, and
     * then NUM_OF_PUCKS pucks will be created.
     *
     * @param thisObj  the brick in our case
     * @param otherObj the object which the brick collide with
     * @param counter  the number of bricks in the game.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        creatingPucks(thisObj);
    }

    /**
     * private method to create the pucks
     *
     * @param brick the brick which has this strategy.
     */
    private void creatingPucks(GameObject brick) {

        Renderable puckImage = imageReader.readImage(PUCK_IMAGE_PATH, true);
        Sound collisionSound = soundReader.readSound(SOUND_FILE_PATH);

        for (int i = 0; i < NUM_OF_PUCKS; i++) {
            GameObject puck = new Puck(Vector2.ZERO,
                    new Vector2(brick.getDimensions().x() / NUM_OF_PUCKS,
                            brick.getDimensions().x() / NUM_OF_PUCKS), puckImage, collisionSound);

            puck.setCenter(new Vector2(brick.getCenter().x() +
                    (i - 1) * (brick.getDimensions().x() / NUM_OF_PUCKS), brick.getCenter().y()));

            setVelOfPuck(puck);
            getGameObjectCollection().addGameObject(puck);
        }
    }

    /**
     * sets the velocity of the ball object.
     */
    private void setVelOfPuck(GameObject puck) {
        float puckVelx = PUCK_SPEED;
        float puckVelY = PUCK_SPEED;

        Random rand = new Random();
        if (rand.nextBoolean()) {
            puckVelx *= -1;
        }
        if (rand.nextBoolean()) {
            puckVelY *= -1;
        }
        puck.setVelocity(new Vector2(puckVelx, puckVelY));
    }
}

