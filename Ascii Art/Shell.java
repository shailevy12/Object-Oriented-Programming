package ascii_art;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_output.AsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;

import java.util.*;

/**
 * this class managing all the communication with the user, and according to his command converts the given
 * img to asciiArt using the chars that appears in the charSet field.
 */
public class Shell {
    // sizes constants
    private static final int MIN_PIXELS_PER_CHAR = 2;
    private static final int INITIAL_CHARS_IN_ROW = 64;
    private static final int MIN_CHARS_IN_ROW = 1;

    // inits constants
    private static final String FONT_NAME = "Courier New";
    private static final String OUTPUT_FILENAME = "out.html";
    private static final String INITIAL_CHARS_RANGE = "0-9";

    // special chars constant
    private static final char SPACE_CHAR = ' ';
    private static final String SPACE_STRING = " ";

    // change res method constants
    private static final int RES_FACTOR = 2;
    private static final String INCREASE_RES = "up";
    private static final String DECREASE_RES = "down";
    private static final String RESOLUTION_CHANGE_MSG = "Width set to ";
    private static final String MAXIMUM_REACHED_MSG = "We've already reached the maximum";
    private static final String MINIMUM_REACHED_MSG = "We've already reached the minimum";

    // messages
    private static final String INVALID_COMMAND_MSG = "invalid command, try again: ";

    // keywords command
    private static final String END_PROGRAM = "exit";

    // add and remove constants
    private static final int SINGLE_CHAR_LEN = 1;
    private static final int RANGE_LEN = 3;
    private static final char WAVE_CHAR = '~';
    private static final char RANGE_CHARS_INDICATOR = '-';
    private static final String KEYWORD_FOR_SPACE_CHAR = "space";
    private static final String KEYWORD_FOR_ALL_CHARS = "all";
    private static final String ADD_CHARS_COMMAND = "add";
    private static final String REMOVE_CHARS_COMMAND = "remove";

    // run method constants
    private static final String COMMAND_START = ">>> ";

    // fields
    private boolean renderConsole = false;
    private int charsInRow;
    private final int minCharsInRow;
    private final int maxCharsInRow;
    private final Set<Character> charSet = new HashSet<>();
    private final BrightnessImgCharMatcher charMatcher;
    private final AsciiOutput output;

    /**
     * constructs the shell object and initialize all the relevant fields.
     *
     * @param img the img which we want to convert to ascii chars.
     */
    public Shell(Image img) {
        addRemoveChars(INITIAL_CHARS_RANGE, ADD_CHARS_COMMAND);
        minCharsInRow = Math.max(MIN_CHARS_IN_ROW, img.getWidth() / img.getHeight());
        maxCharsInRow = img.getWidth() / MIN_PIXELS_PER_CHAR;
        charsInRow = Math.max(Math.min(INITIAL_CHARS_IN_ROW, maxCharsInRow), minCharsInRow);
        charMatcher = new BrightnessImgCharMatcher(img, FONT_NAME);
        output = new HtmlAsciiOutput(OUTPUT_FILENAME, FONT_NAME);
    }

    /**
     * Renders the img by asciiArt, checks if the user want to print the img to the console window by the
     * field renderConsole which used as a flag. If not, it creates and output HTML file which contains the
     * img representing as ascii chars.
     */
    private void render() {
        Character[] charArray = convertCharSetToArray();
        char[][] asciiArt = charMatcher.chooseChars(charsInRow, charArray);
        if (!renderConsole) {
            output.output(asciiArt);
            return;
        }
        printAsciiCharsToConsole(asciiArt);
    }

    /**
     * private helper method for render, which prints the given array of chars and print it as img that
     * constructs from ascii chars.
     *
     * @param asciiArt the given array to print as img.
     */
    private void printAsciiCharsToConsole(char[][] asciiArt) {
        for (char[] chars : asciiArt) {
            for (char chary : chars) {
                System.out.print(chary + SPACE_STRING);
            }
            System.out.println();
        }
    }

    /**
     * private helper method for render, which converts out char set to an array, so we can use the method
     * "chooseChars".
     *
     * @return the array which contains all the chars that the charSet holds.
     */
    private Character[] convertCharSetToArray() {
        Character[] charArray = new Character[charSet.size()];
        int ind = 0;
        for (Character chary : charSet) {
            charArray[ind] = chary;
            ind++;
        }
        return charArray;
    }

    /**
     * prints all the chars that the charSet holds after the command "chars" has been given.
     */
    private void showChars() {
        charSet.stream().sorted().forEach(c -> System.out.print(c + SPACE_STRING));
        System.out.println();
    }

    /**
     * changing the resolution of the img after the user typed the command "res up" or "res down",
     * also checking that we didn't reach the minimum resolution or the maximum resolution.
     *
     * @param s the string that comes right after the command "res", to know if the user wanted to increase
     *          the resolution or maybe decrease it.
     */
    private void resChange(String s) {
        switch (s) {
            case INCREASE_RES:
                if (charsInRow * RES_FACTOR > maxCharsInRow) {
                    System.out.println(MAXIMUM_REACHED_MSG);
                    return;
                }
                charsInRow *= RES_FACTOR;
                break;
            case DECREASE_RES:
                if (charsInRow / RES_FACTOR < minCharsInRow) {
                    System.out.println(MINIMUM_REACHED_MSG);
                    return;
                }
                charsInRow /= RES_FACTOR;
                break;
        }
        System.out.println(RESOLUTION_CHANGE_MSG + charsInRow);
    }

    /**
     * Responsible for parses the string that coming after the "add" or "remove" command, checks the validity
     * of this string, so it is a legal range.
     *
     * @param param the string coming after the command.
     * @return array of chars in length 2, so we can remove/add all the chars between the char at index 0 to
     * the char in index 1, in the ascii table.
     */
    private static char[] parseCharRange(String param) {

        // add\remove single char
        if (param.length() == SINGLE_CHAR_LEN) {
            return new char[]{param.charAt(0), param.charAt(0)};
        }

        // add\remove space char
        if (param.equals(KEYWORD_FOR_SPACE_CHAR)) {
            return new char[]{SPACE_CHAR, SPACE_CHAR};
        }

        // add\remove all chars in the ascii table
        if (param.equals(KEYWORD_FOR_ALL_CHARS)) {
            return new char[]{SPACE_CHAR, WAVE_CHAR};
        }

        // add\remove the given range
        if (param.length() == RANGE_LEN && param.charAt(1) == RANGE_CHARS_INDICATOR) {
            if (param.charAt(0) < param.charAt(2)) {
                return new char[]{param.charAt(0), param.charAt(2)};
            }
            return new char[]{param.charAt(2), param.charAt(0)};
        }

        // invalid range
        System.out.println(INVALID_COMMAND_MSG);
        return null;
    }


    /**
     * add or remove chars to charSet according to the given command
     *
     * @param s       range/char/keyword to add
     * @param command add/remove command
     */
    private void addRemoveChars(String s, String command) {
        char[] range = parseCharRange(s);
        if (range != null) {
            for (int i = range[0]; i <= range[1]; i++) {
                if (command.equals(ADD_CHARS_COMMAND)) {
                    charSet.add((char) i);
                } else if (command.equals(REMOVE_CHARS_COMMAND)) {
                    charSet.remove((char) i);
                }
            }
        }
    }


    /**
     * the main method of this class which responsible to read the user's commands and calls the other
     * methods of the class accordingly.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(COMMAND_START);
        String inputUser = scanner.nextLine();
        while (!inputUser.equalsIgnoreCase(END_PROGRAM)) {
            String[] splitInputUser = inputUser.split(SPACE_STRING);

            switch (splitInputUser[0].toLowerCase(Locale.ROOT)) {

                case "chars":
                    showChars();
                    break;

                case "add":
                    if (splitInputUser.length > 1) {
                        addRemoveChars(splitInputUser[1], ADD_CHARS_COMMAND);
                    } else {
                        System.out.println(INVALID_COMMAND_MSG);
                    }
                    break;

                case "remove":
                    if (splitInputUser.length > 1) {
                        addRemoveChars(splitInputUser[1], REMOVE_CHARS_COMMAND);
                    }
                    break;

                case "res":
                    if (splitInputUser.length > 1
                            && (splitInputUser[1].equals(INCREASE_RES)
                            || splitInputUser[1].equals(DECREASE_RES))) {
                        resChange(splitInputUser[1]);
                    }
                    break;

                case "console":
                    renderConsole = true;
                    break;

                case "render":
                    render();
                    break;

                default:
                    System.out.println(INVALID_COMMAND_MSG);
                    break;
            }
            System.out.print(COMMAND_START);
            inputUser = scanner.nextLine();
        }
    }
}
