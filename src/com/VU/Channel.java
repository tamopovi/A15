package com.VU;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.VU.Constants.*;
import static com.VU.Utils.*;

public class Channel {
    private float errorProbability;

    public Channel(float errorProbability) {
        this.errorProbability = errorProbability;
    }

    public float getErrorProbability() {
        return errorProbability;
    }

    public void setErrorProbability(float errorProbability) {
        if (errorProbability >= 0 && errorProbability <= 1) {
            this.errorProbability = errorProbability;
            System.out.println(ANSI_YELLOW + "Error probability was changed to: " + ANSI_GREEN + getErrorProbability() + ANSI_RESET + ".");
        }
    }

    public void sendMessage(CodeString codeString) {
        try {
            // send the message through the channel
            if (codeString.getEncodedString() == null) {
                throw (new Exception(ERROR_SENDING_NULL));
            } else {
                System.out.println("Sending \"" + ANSI_YELLOW + codeString.getEncodedString() + ANSI_RESET +
                        "\" through the channel... (Error probability: " + ANSI_GREEN + getErrorProbability()
                        + ANSI_RESET + ")");
                ArrayList<Character> inputMessageVector = new ArrayList<>(
                        codeString.getEncodedString().chars()
                                .mapToObj(e -> (char) e)
                                .collect(
                                        Collectors.toList()
                                )
                );
                ArrayList<Character> outputMessageVector = (ArrayList<Character>) inputMessageVector.stream().map(el -> {
                    if (getRandomFloatInRange(0, 1) <= errorProbability) {
                        // create an error
                        if (el == '0') {
                            return '1';
                        }
                        if (el == '1') {
                            return '0';
                        }
                        return generateRandomUnicodeChar();
                    } else {
                        // pass without an error
                        return el;
                    }
                }).collect(Collectors.toList());
                System.out.println(MSG_SUCCESS);
                codeString.setReceivedString(charArrayListToString(outputMessageVector));
                System.out.println("Message received through the channel: \"" + ANSI_YELLOW + codeString.getReceivedString() + ANSI_RESET + "\".");
                codeString.printErrorPositions();
            }
        } catch (Exception e) {
            System.out.println(MSG_FAILED_CHANNEL_TRANSFER);
            System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
        }
    }

    public void sendImage(CodeString codeString) {
        try {
            // send the message through the channel
            if (codeString.getEncodedString() == null) {
                throw (new Exception(ERROR_SENDING_NULL));
            } else {
                System.out.println("Sending image through the channel... (Error probability: " + ANSI_GREEN + getErrorProbability()
                        + ANSI_RESET + ")");
                ArrayList<Character> inputMessageVector = new ArrayList<>(
                        codeString.getEncodedString().chars()
                                .mapToObj(e -> (char) e)
                                .collect(
                                        Collectors.toList()
                                )
                );
                int bmpHeaderBitSize = 1104;    // 14 (Bitmap file header) + 124 (DIB header) bytes
                ArrayList<Character> outputMessageVector = new ArrayList<>();
                for (int i = 0; i < inputMessageVector.size(); i++) {
                    char el = inputMessageVector.get(i);
                    if (i < bmpHeaderBitSize)
                        outputMessageVector.add(inputMessageVector.get(i));
                    else if (getRandomFloatInRange(0, 1) <= errorProbability) {
                        // create an error
                        if (el == '0') {
                            outputMessageVector.add('1');
                        }
                        if (el == '1') {
                            outputMessageVector.add('0');
                        }
                    } else {
                        // pass without an error
                        outputMessageVector.add(inputMessageVector.get(i));
                    }
                }
                System.out.println(MSG_SUCCESS);
                codeString.setReceivedString(charArrayListToString(outputMessageVector));
                System.out.println("Message received through the channel. Check decoded-{originalFileName} file.");
                codeString.printErrorPositions();
            }
        } catch (Exception e) {
            System.out.println(MSG_FAILED_CHANNEL_TRANSFER);
            System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
        }
    }

    public void printCurrentErrorProbability() {
        System.out.println("Current error probability is: " + ANSI_GREEN + getErrorProbability() + ANSI_RESET + ".");
    }

}
