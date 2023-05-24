package ascii_art.img_to_char;

import image.Image;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This class in general matching between chars and sub-images by their brights levels using formulas and
 * some more operates.
 * At the end this class can covert the given image it to many ascii chars that present this img, using its
 * own methods.
 */
public class BrightnessImgCharMatcher {


    private static final int PIXELS_NUM = 16;
    private static final int MAX_OF_PIXELS = 255;
    private final Image img;
    private final String fontName;
    private final HashMap<Image, Double> cache = new HashMap<>();

    /**
     * constructor for BrightnessImageCharMatcher
     *
     * @param img      img to convert to ascii chars.
     * @param fontName the font according to convert chars
     */
    public BrightnessImgCharMatcher(Image img, String fontName) {
        this.img = img;
        this.fontName = fontName;
    }


    /**
     * Private method which fitting to each char its brightness level.
     *
     * @param charSet array of chars
     * @return array of brightness levels of the given chars.
     */
    private double[] calculateBrightLevelOfChar(Character[] charSet) {
        double[] charsBrightLevel = new double[charSet.length];
        for (int i = 0; i < charSet.length; i++) {
            boolean[][] pixelsOfChar = CharRenderer.getImg(charSet[i], PIXELS_NUM, fontName);
            int counterTrue = countingTrueNum(pixelsOfChar);
            charsBrightLevel[i] = (double) counterTrue / (double) PIXELS_NUM;
        }
        return charsBrightLevel;
    }

    /**
     * helper method for calculateBrightLevelOfChar method, which counts all the amount of 'True' that
     * the given array contain
     *
     * @param pixelsOfChar the given array to count all the num of 'True' in.
     * @return the counter
     */
    private int countingTrueNum(boolean[][] pixelsOfChar) {
        int counterTrue = 0;
        for (boolean[] booleans : pixelsOfChar) {
            for (boolean bool : booleans) {
                if (bool) {
                    counterTrue++;
                }
            }
        }
        return counterTrue;
    }

    /**
     * Private method which get the brightness level to be normal according to formula that has been
     * given in the PDF
     *
     * @param charsBrightLevel the brightness level of chars
     * @return normal brightness of chars
     */
    private double[] linearStretching(double[] charsBrightLevel) {
        if (charsBrightLevel.length == 0) {
            return charsBrightLevel;
        }

        double minBrightChar = Arrays.stream(charsBrightLevel).min().getAsDouble();
        double maxBrightChar = Arrays.stream(charsBrightLevel).max().getAsDouble();

        for (int i = 0; i < charsBrightLevel.length; i++) {
            if (minBrightChar != maxBrightChar) {
                charsBrightLevel[i] =
                        (charsBrightLevel[i] - minBrightChar) / (maxBrightChar - minBrightChar);
            }
        }
        return charsBrightLevel;
    }

    /**
     * Private method that going on all the pixels of the img and calculate the average of them.
     * first convert each pixel to be a gray pixel and then doing an average.
     *
     * @param img img to calculate its pixels average
     * @return the average of the pixels in img.
     */
    private double getAveragePixelOfImage(Image img) {
        if (img == null) {
            return 0;
        }
        double sumPixels = 0;
        double counterPixels = 0;
        for (Color pixel : img.pixels()) {
            sumPixels += (pixel.getRed() * 0.2126 + pixel.getGreen() * 0.7152 + pixel.getBlue() * 0.0722);
            counterPixels += 1;
        }
        if (counterPixels == 0) {
            return 0;
        }
        return sumPixels / counterPixels / MAX_OF_PIXELS;
    }

    /**
     * convert the img to an ascii chars by dividing the img to some sub images, calculating their averages
     * and fitting each sub image a char according to its brightness level using the other private methods of
     * this class.
     *
     * @param numCharsInRow     num chars in row to be in the ascii art.
     * @param pixels            num of pixels to divide the image to.
     * @param normalBrightLevel the array of the normal brightness level of chars
     * @param charSet           the array of chars
     * @return array of ascii chars that have been fitted to the image.
     */
    private char[][] convertImageToAscii(int numCharsInRow, int pixels, double[] normalBrightLevel,
                                         Character[] charSet) {
        if (img == null || numCharsInRow == 0) {
            return null;
        }

        char[][] asciiArt = new char[img.getHeight() / pixels][img.getWidth() / pixels];
        int k = 0; // index of row
        int l = 0; // index of col

        for (Image subImage : img.squareSubImagesOfSize(pixels)) {
            if (l == img.getWidth() / pixels) {
                l = 0;
                k++;
            }
            Double brightLevelOfSubImg = getBrightLevelOfImg(subImage);
            int ind = getIndOfClosestChar(normalBrightLevel, brightLevelOfSubImg);
            asciiArt[k][l] = charSet[ind];
            l++;
        }
        return asciiArt;
    }

    /**
     * private helper method for convertImageToAscii that checks if we already calculate the bright level
     * of the given sub img, if not then it calculates in and put it on cache so later,
     * there is no need to calculate again.
     *
     * @param subImage sub img of the img field
     * @return the bright level of the current sub img
     */
    private Double getBrightLevelOfImg(Image subImage) {
        Double brightLevelOfSubImg = cache.get(subImage);
        if (brightLevelOfSubImg == null) {
            brightLevelOfSubImg = getAveragePixelOfImage(subImage);
            cache.put(subImage, brightLevelOfSubImg);
        }
        return brightLevelOfSubImg;
    }

    /**
     * private helper method for convertImageToAscii, that going over all the bright level of the given chars
     * and fitting to this sub img the closest chars by comparing their bright levels.
     *
     * @param normalBrightLevel   all the bright levels of the chars, fitting by index.
     * @param brightLevelOfSubImg the bright level of sub img
     * @return the closest bright level index of normalBrightLevel array.
     */
    private int getIndOfClosestChar(double[] normalBrightLevel, Double brightLevelOfSubImg) {
        double delta = 1;
        int ind = 0;
        for (int i = 0; i < normalBrightLevel.length; i++) {
            if (Math.abs(normalBrightLevel[i] - brightLevelOfSubImg) < delta) {
                delta = Math.abs(normalBrightLevel[i] - brightLevelOfSubImg);
                ind = i;
            }
        }
        return ind;
    }

    /**
     * fitting the img chars from the char set according to its brightness levels.
     * calculate the brightness level of the chars, get them to be normal and calculate the number of pixel
     * according to the sizes of image and numCharInRow.
     *
     * @param numCharsInRow the number of chars to be in the row of image.
     * @param charSet       array of chars to fit to img.
     * @return all the chars that have been chosen for the img for all parts.
     */
    public char[][] chooseChars(int numCharsInRow, Character[] charSet) {
        if (img == null || numCharsInRow == 0) {
            return null;
        }
        double[] normalBrightLevel = linearStretching(calculateBrightLevelOfChar(charSet));
        int pixels = img.getWidth() / numCharsInRow;
        return convertImageToAscii(numCharsInRow, pixels, normalBrightLevel, charSet);

    }
}