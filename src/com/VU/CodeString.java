package com.VU;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.VU.Constants.*;
import static com.VU.Utils.*;

public class CodeString {
    private String rawString;
    private String encodedString;
    private String decodedString;
    private String receivedString;
    private Encoder encoder;

    public CodeString() {
        encoder = new Encoder();
    }

    public String getRawString() {
        return rawString;
    }

    public void setRawString(String newRawString) {
        rawString = newRawString;
        encodedString = null;
        decodedString = null;
        receivedString = null;
        System.out.println("New raw string was set to: \"" + ANSI_YELLOW + rawString + ANSI_RESET + "\".");
    }

    public String getEncodedString() {
        return encodedString;
    }

    public String getDecodedString() {
        return decodedString;
    }

    private void setEncodedString(String newEncodedString) {
        encodedString = newEncodedString;
    }

    private void setDecodedString(String newDecodedString) {
        decodedString = newDecodedString;
    }

    public String getReceivedString() {
        return receivedString;
    }

    public void setReceivedString(String receivedString) {
        this.receivedString = receivedString;
    }

    public void encode(String inputString) {
        try {
            // do some stuff with inputString
            setRawString(inputString);
            if (countOccurences(inputString, '0', 0) +
                    countOccurences(inputString, '1', 0) == inputString.length()) {
                encodeBinaryVector(inputString + "000000");
            } else {

                String inputBits = convertStringToBinary(rawString) + "000000";
                ArrayList<Character> encoded = new ArrayList<Character>();
                for (char bit : inputBits.toCharArray()) {
                    encoded.add(bit);
                    encoded.add(encoder.getEncodedBit(bit));
                }
                setEncodedString(charArrayListToString(encoded));
            }
            System.out.println(MSG_SUCCESS);
            System.out.println("Raw string was: \"" + ANSI_YELLOW + inputString + ANSI_RESET + "\"." +
                    "\nEncoded result: \"" + ANSI_YELLOW + encodedString + ANSI_RESET + "\".");
        } catch (Exception e) {
            System.out.println(MSG_ENCODING_FAILED);
            System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
        }
    }

    public void decode() {
        // do some stuff with received string
        if (receivedString == null) {
            System.out.println(ERROR_DECODING_EMPTY);
        } else {
            try {
                setDecodedString("NEW DECODED STRING");
                System.out.println(MSG_SUCCESS);
                this.printCurrentState();
            } catch (Exception e) {
                System.out.println(MSG_DECODING_FAILED);
                System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
            }
        }
    }

    public void printCurrentState() {
        System.out.println("Current raw string from input: \"" + ANSI_YELLOW + rawString + ANSI_RESET + "\".");
        System.out.println("Current encoded string: \"" + ANSI_YELLOW + encodedString + ANSI_RESET + "\".");
        System.out.println("Current decoded string: \"" + ANSI_YELLOW + decodedString + ANSI_RESET + "\".");
        System.out.println("Current received string from channel: \"" + ANSI_YELLOW + receivedString + ANSI_RESET + "\".");
        if (rawString == null || encodedString == null || decodedString == null || receivedString == null) {
            System.out.println(ANSI_YELLOW + "Some string values are null.\nYou might have not initialized the " +
                    "coding string or did not use the commands \"" + CMD_ENCODE + "\", \"" + CMD_DECODE + "\" or \""
                    + CMD_SEND + "\"." + ANSI_RESET);
        }
    }

    public void editReceivedString() {
        if (receivedString == null) {
            System.out.println(ERROR_EDIT_EMPTY);
            if (encodedString == null && rawString == null && receivedString == null) {
                System.out.println(MSG_EDIT_EMPTY);
            }
        } else {
            try {
                Scanner scanner = new Scanner(System.in);
                String inputLine;
                System.out.println("Current received string from channel: \"" + ANSI_YELLOW + receivedString
                        + ANSI_RESET + "\".");
                System.out.println("Enter new received message:");
                inputLine = scanner.nextLine();
                System.out.print(INPUT_PREFIX);
                setReceivedString(inputLine);
                System.out.println(MSG_SUCCESS);
                System.out.println("Received message was set to: \"" + ANSI_YELLOW + getReceivedString()
                        + ANSI_RESET + "\".");
            } catch (Exception e) {
                System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
            }
        }
    }

    private void encodeBinaryVector(String inputBits) {
        System.out.println("Encoding binary vector...");
        ArrayList<Character> encoded = new ArrayList<Character>();
        for (char bit : inputBits.toCharArray()) {
            encoded.add(bit);
            encoded.add(encoder.getEncodedBit(bit));
        }
        setEncodedString(charArrayListToString(encoded));
    }

//    public void enc(Integer encodingBit) {
//        Integer encodedBit = encoder.getEncodedBit(encodingBit);
//        String encodedResult = encodingBit.toString() + encodedBit.toString();
//        encoder.printEncodingChain();
//        System.out.println(encodedResult);
//    }

    // TODO: write positions and percentages when encoding/decoding is done
    public void printErrorPositions() {
        // compare encodedString with receivedString
        System.out.println("Encoded string was: \"" + ANSI_YELLOW + encodedString + ANSI_RESET + "\". " +
                "(length = " + ANSI_GREEN + encodedString.length() + ANSI_RESET + ")");
        System.out.println("Received string was: \"" + ANSI_YELLOW + receivedString + ANSI_RESET + "\". " +
                "(length = " + ANSI_GREEN + receivedString.length() + ANSI_RESET + ")");
        String str1;
        String str2;
        if (encodedString.length() >= receivedString.length()) {
            str1 = receivedString;
            str2 = encodedString;
        } else {
            str1 = encodedString;
            str2 = receivedString;
        }
        int diffCount = (int) IntStream.range(0, str1.length())
                .filter(i -> str1.charAt(i) != str2.charAt(i)) // corresponding characters from both the strings
                .count();

        List positionDiffArray = IntStream
                .range(0, encodedString.length())
                .filter(i -> encodedString.charAt(i) != receivedString.charAt(i))
                .mapToObj(i -> i)
                .collect(Collectors.toList());
        System.out.println("Encountered " + ANSI_GREEN + diffCount + ANSI_RESET + " inconsistencies while comparing " +
                "the encoded message and the received message.");
        System.out.println("Differences found in these positions:");
        System.out.println(Arrays.toString(positionDiffArray.toArray()));
    }
}
