package com.VU;

import static com.VU.Constants.*;

public class CodeString {
    private String rawString;
    private String encodedString;
    private String decodedString;

    public CodeString() {
    }

    public String getRawString() {
        return rawString;
    }

    public void setRawString(String newRawString) {
        rawString = newRawString;
        encodedString = null;
        decodedString = null;
        System.out.println("New raw string was set to: \"" + rawString + "\".");
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

    public void encode(String inputString) {
        try {
            // do some stuff with inputString
            setRawString(inputString);
            setEncodedString("NEW ENCODED STRING FROM INPUT");
            System.out.println(ANSI_GREEN + "Success!" + ANSI_RESET + "\nRaw string was: \"" + inputString + "\".\nEncoded result: \"" + encodedString + "\".");
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Encoding failed." + ANSI_RESET);
            System.out.println(e.getMessage());
        }
    }

    public void decode() {
        // do some stuff with encoded string
        if (encodedString == null) {
            System.out.println(ERROR_DECODING_EMPTY);
        } else {
            try {
                setDecodedString("NEW DECODED STRING");
                System.out.println(ANSI_GREEN + "Success!" + ANSI_RESET);
                this.printCurrentState();
            } catch (Exception e) {
                System.out.println(ANSI_RED + "Decoding failed." + ANSI_RESET);
                System.out.println(e.getMessage());
            }
        }
    }

    public void printCurrentState() {
        System.out.println("Current raw string from input: \"" + rawString + "\".");
        System.out.println("Current encoded string: \"" + encodedString + "\".");
        System.out.println("Current decoded string: \"" + decodedString + "\".");
        if (rawString == null || encodedString == null || decodedString == null) {
            System.out.println(ANSI_YELLOW + "Some string values are null.\nYou might have not initialized the " +
                    "coding string or did not use the commands \"" + CMD_ENCODE + "\" or \"" + CMD_DECODE + "\"."
                    + ANSI_RESET);
        }
    }
}
