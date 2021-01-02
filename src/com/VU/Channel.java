package com.VU;

import static com.VU.Constants.*;

public class Channel {
    private float errorProbability;

    public Channel() {
        errorProbability = 0;
    }

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
                System.out.println("Sending \"" + ANSI_YELLOW + codeString.getEncodedString() + ANSI_RESET + "\" through the channel...");
                System.out.println(MSG_SUCCESS);
                codeString.setReceivedString("MESSAGE THROUGH THE CHANNEL");
                System.out.println("Message received through the channel: \"" + ANSI_YELLOW + codeString.getReceivedString() + ANSI_RESET + "\".");
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
