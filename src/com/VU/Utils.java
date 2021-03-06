package com.VU;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import static com.VU.Constants.*;
import static com.VU.Constants.ERROR_RANGE;

public final class Utils {

    // get a float value for a probability from a String with format "x.y" or "x,y"
    public static float readProbability(String inputStr) {
        float probability;
        inputStr = inputStr.replaceAll(",", ".");
        probability = Float.valueOf(inputStr);
        if (!((probability >= 0) && (probability <= 1))) {
            System.out.println(ANSI_RED + ERROR_RANGE + ANSI_RESET);
        }
        return probability;
    }

    // count occurrences of a char in a String
    public static long countOccurrences(String someString, char searchedChar) {
        return someString.chars().filter(ch -> ch == searchedChar).count();
    }

    public static float getRandomFloatInRange(int min, int max) {
        Random r = new Random();
        return min + r.nextFloat() * (max - min);
    }

    public static String charArrayListToString(ArrayList<Character> list) {
        StringBuilder builder = new StringBuilder(list.size());
        for (Character ch : list) {
            builder.append(ch);
        }
        return builder.toString();
    }

    public static char generateRandomUnicodeChar() {
        Random r = new Random();
        return (char) r.nextInt(10000);
    }

    // convert String of text into a String of binary
    public static String convertStringToBinary(String input) {
        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))   // char -> int, auto-cast
                            .replaceAll(" ", "0")                         // zero pads
            );
        }
        return result.toString();
    }

    // get text String from a binary string containing text data
    public static String getTextFromBinary(String input) {
        StringBuilder sb = new StringBuilder(); // Some place to store the chars

        Arrays.stream( // Create a Stream
                input.split("(?<=\\G.{8})") // Splits the input string into 8-char-sections (Since a char has 8 bits = 1 byte)
        ).forEach(s -> // Go through each 8-char-section...
                sb.append((char) Integer.parseInt(s, 2)) // ...and turn it into an int and then to a char
        );

        return sb.toString();
    }

    public static void byteArrayToImage(byte[] data, String imageName) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage bImage2 = ImageIO.read(bis);
        ImageIO.write(bImage2, "bmp", new File(imageName));
    }

    public static String byteArrayToBinaryString(byte[] data) {
        StringBuilder resultString = new StringBuilder("");
        for (byte b : data) {
            resultString.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }
        return resultString.toString();
    }

    public static byte[] binaryStringToByteArray(String binaryString) {
        ArrayList<Integer> bytes = new ArrayList<>();
        Arrays.stream(
                binaryString.split("(?<=\\G.{8})"))
                .forEach(s -> bytes.add(Integer.parseInt(s, 2)));
        byte[] resultByteArr = new byte[(bytes.size())];
        for (int i = 0; i < bytes.size(); i++) {
            resultByteArr[i] = bytes.get(i).byteValue();
        }
        return resultByteArr;
    }

    // print the differences between one String and another
    public static void printDiff(String str1, String str2) {

        int diffCount = (int) IntStream.range(0, str1.length())
                .filter(i -> str1.charAt(i) != str2.charAt(i)) // corresponding characters from both the strings
                .count();
        System.out.println("Encountered " + ANSI_GREEN + diffCount + ANSI_RESET + " inconsistencies while comparing messages.");
        double percentage = Math.round((float) diffCount / (float) str1.length() * 1000) / 10.0;
        System.out.println("Approx. " + ANSI_GREEN + percentage + "%" + ANSI_RESET + " difference.");
    }
}
