package com.VU;

import java.util.ArrayList;
import java.util.Random;

public final class Utils {
    public static int countOccurences(
            String someString, char searchedChar, int index) {
        if (index >= someString.length()) {
            return 0;
        }

        int count = someString.charAt(index) == searchedChar ? 1 : 0;
        return count + countOccurences(
                someString, searchedChar, index + 1);
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
}
