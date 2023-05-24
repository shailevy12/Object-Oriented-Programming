package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public abstract class RemoveBrickStrategyDecorator implements CollisionStrategy {
    private final CollisionStrategy collisionStrategy;

    /**
     * Constructs Remove Brick Strategy Decorator.
     * @param toBeDecorated collision strategy to be decorated.
     */
    RemoveBrickStrategyDecorator(CollisionStrategy toBeDecorated) {
        this.collisionStrategy = toBeDecorated;
    }

    /**
     * @return The collection of all game objects in the game.
     */
    public GameObjectCollection getGameObjectCollection() {
        return collisionStrategy.getGameObjectCollection();
    }

    /**
     * Handle with cases when two objects of the game had collided with each other.
     * In this case handle with cases when the brick collide with other GameObject, acts
     * according to this.collisionStrategy.
     *
     * @param thisObj  the brick in our case
     * @param otherObj the object which the brick collide with
     * @param counter  the number of bricks in the game.
     */
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        collisionStrategy.onCollision(thisObj, otherObj, counter);
    }

    public static int uniqueMorseRepresentations(String[] words) {
        Map<Character, String> morse_chars = Map.ofEntries(
                Map.entry('a', ".-"), Map.entry('b', "-..."), Map.entry('c', "-.-."),
                Map.entry('d', "-.."), Map.entry('e', "."), Map.entry('f', "..-."),
                Map.entry('g', "--."), Map.entry('h', "...."), Map.entry('i', ".."),
                Map.entry('j', ".---"), Map.entry('k', "-.-"), Map.entry('l', ".-.."),
                Map.entry('m', "--"), Map.entry('n', "-."), Map.entry('o', "---"),
                Map.entry('p', ".--."), Map.entry('q', "--.-"), Map.entry('r', ".-."),
                Map.entry('s', "..."), Map.entry('t', "-"), Map.entry('u', "..-"),
                Map.entry('v', "...-"), Map.entry('w', ".--"), Map.entry('x', "-..-"),
                Map.entry('y', "-.--"), Map.entry('z', "--.."));

        HashSet<String> morseWord = new HashSet<>();
        StringBuilder temp;
        for( String word: words) {
            for (int i = 0; i < word.length(); i++) {
                temp.append(word.charAt(i));
            }
            morseWord.add(temp.toString());
        }
        return morseWord.size();
    }
}

