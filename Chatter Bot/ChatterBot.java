import java.util.*;

/**
 * ChatterBot class which create bot who knows to reply smartly to some given statements by the replyTo
 * method, and so can handle a great chat with the user or with other bots.
 *
 * @author Shai Levy
 */
class ChatterBot {
    static final String REQUEST_PREFIX = "say "; // the prefix that start a legal statement
    static final String REQUESTED_PHRASE_PLACEHOLDER = "<phrase>";
    static final String ILLEGAL_REQUEST_PLACEHOLDER = "<request>";

    Random rand = new Random();
    String[] repliesToIllegalRequest; // stack of replies to illegal statement
    String[] repliesToLegalRequest; // stack of replies to legal statement
    String name;

    /**
     * the constructor of the Chatter bots class. get the name of th bot and 2 stack of replies, one for
     * legal requests and one for illegal requests.
     * the constructor initialize 2 arrays of replies by using the initializeTheRepliesArrays method and fill
     * them with the given replies stacks accordingly.
     *
     * @param name                    name of the bot
     * @param repliesToLegalRequest   stack of replies for legal requests to fill the field
     *                                repliesToLegalRequest of the class
     * @param repliesToIllegalRequest stack of replies for illegal requests to fill the field
     *                                repliesToIllegalRequest of the class
     */
    ChatterBot(String name, String[] repliesToLegalRequest,
               String[] repliesToIllegalRequest) {
        this.name = name;
        this.repliesToIllegalRequest = new String[repliesToIllegalRequest.length];
        this.repliesToLegalRequest = new String[repliesToLegalRequest.length];
        initializeTheRepliesArrays(this.repliesToIllegalRequest, repliesToIllegalRequest);
        initializeTheRepliesArrays(this.repliesToLegalRequest, repliesToLegalRequest);
    }

    /**
     * receives 2 arrays and copies one of them to the other (which is one of the class's field manually.)
     *
     * @param repliesToInitializeField the field of the class - the array to fill.
     * @param givenRepliesToInitialize the given array in the constructor - the array to be copied.
     */
    void initializeTheRepliesArrays(String[] repliesToInitializeField, String[] givenRepliesToInitialize) {
        for (int i = 0; i < givenRepliesToInitialize.length; i++) {
            repliesToInitializeField[i] = givenRepliesToInitialize[i];
        }
    }

    /**
     * the main method of the class. the method checks if the given statement starts with the request prefix
     * constant, and accordingly calls to the response method that fits.
     *
     * @param statement the given statement (can be received from the user or from other bot)
     * @return the reply to the given statement using helper methods.
     */
    String replyTo(String statement) {
        if (statement.startsWith(REQUEST_PREFIX)) {
            String phrase = statement.replaceFirst(REQUEST_PREFIX, "");
            return replacePlaceholderInARandomPattern(phrase, repliesToLegalRequest,
                    REQUESTED_PHRASE_PLACEHOLDER);
        }
        return replacePlaceholderInARandomPattern(statement, repliesToIllegalRequest,
                ILLEGAL_REQUEST_PLACEHOLDER);
    }


    /**
     * receives replies array (legal or illegal) and choose by random a replay. then the method replaces all
     * the placeholder strings that there are in the reply with the given statement. In that way, the bot
     * will know how to reply smartly to some requests.
     *
     * @param statement   the given statement to reply to
     * @param replies     array of replies to choose from a random one.
     * @param placeholder the string in the reply that needs to be replaced with the given statement (always
     *                    will be one of the 2 placeholder constants of the class)
     * @return the final reply of the bot after the replacement.
     */
    String replacePlaceholderInARandomPattern(String statement, String[] replies, String placeholder) {
        int randomIndex = rand.nextInt(replies.length);
        String reply = replies[randomIndex];
        reply = reply.replaceAll(placeholder, statement);
        return reply;
    }


    /**
     * @return the name field of this ChatterBot
     */
    String getName() {
        return this.name;
    }
}
