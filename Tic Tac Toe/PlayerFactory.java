public class PlayerFactory {

    /**
     * constructor for this class.
     */
    public PlayerFactory() {
    }

    /**
     * this function gets the type of the player and accordingly create the desired player
     * @param typeOfPlayerInput String representing the type of the desired player
     * @return object of the fitting class of player, or null in case the type doesn't fit anything.
     */
    public Player buildPlayer(String typeOfPlayerInput) {
        switch (typeOfPlayerInput) {
            case "human":
                return new HumanPlayer();

            case "whatever":
                return new WhateverPlayer();

            case "clever":
                return new CleverPlayer();

            case "snartypamts":
                return new SnartypamtsPlayer();

            default:
                return null;
        }
    }
}