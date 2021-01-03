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

import static com.VU.Constants.ANSI_GREEN;
import static com.VU.Constants.ANSI_RESET;

public final class Utils {
    public static long countOccurences(String someString, char searchedChar) {
        long count = someString.chars().filter(ch -> ch == searchedChar).count();
        return count;
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

    public static String getTextFromBinary(String input) {
        StringBuilder sb = new StringBuilder(); // Some place to store the chars

        Arrays.stream( // Create a Stream
                input.split("(?<=\\G.{8})") // Splits the input string into 8-char-sections (Since a char has 8 bits = 1 byte)
        ).forEach(s -> // Go through each 8-char-section...
                sb.append((char) Integer.parseInt(s, 2)) // ...and turn it into an int and then to a char
        );

        String output = sb.toString(); // Output text (t)
        return output;
    }

    public static void byteArrayToImage(byte[] data, String imageName) throws IOException {
        System.out.println("Data length: " + data.length + " bytes.");
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage bImage2 = ImageIO.read(bis);
        ImageIO.write(bImage2, "bmp", new File(imageName.substring(0, imageName.length() - 4) + ".bmp"));
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

    public static void printDiff(String str1, String str2) {

        int diffCount = (int) IntStream.range(0, str1.length())
                .filter(i -> str1.charAt(i) != str2.charAt(i)) // corresponding characters from both the strings
                .count();
        System.out.println("Encountered " + ANSI_GREEN + diffCount + ANSI_RESET + " inconsistencies while comparing messages.");
        double percentage = Math.round((float) diffCount / (float) str1.length() * 1000) / 10.0;
        System.out.println("Approx. " + ANSI_GREEN + percentage + "%" + ANSI_RESET + " difference.");
    }
}
