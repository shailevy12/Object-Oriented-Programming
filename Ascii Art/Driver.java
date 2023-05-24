package ascii_art;

import image.Image;

import java.util.logging.Logger;

/**
 * this class only holds the main function of the program. responsible for creating the shell object and
 * checking the validity of the program arguments.
 */
public class Driver {
    private static final String ERROR_NUM_ARGS_MSG = "USAGE: java asciiArt ";
    private static final String INVALID_FILE_MSG = "Failed to open image file ";
    private static final int VALID_ARGS_NUM = 1;

    public static void main(String[] args) throws Exception {
        if (args.length != VALID_ARGS_NUM) {
            System.err.println(ERROR_NUM_ARGS_MSG);
            return;
        }
        Image img = Image.fromFile(args[0]);
        if (img == null) {
            Logger.getGlobal().severe(INVALID_FILE_MSG +
                    args[0]);
            return;
        }
        new Shell(img).run();
    }
}