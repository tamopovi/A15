package com.VU;

public final class Constants {
    public static final String INPUT_PREFIX = ">";
    public static final float DEFAULT_ERROR_PROBABILITY = 0;
    public static final int bmpHeaderBitSize = 1104;    // 14 (Bitmap file header) + 124 (DIB header) bytes

    // colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";

    // commands
    public static final String CMD_CHANGE_ERROR_PROB = "cep";
    public static final String CMD_DECODE = "decode";
    public static final String CMD_EDIT = "edit";
    public static final String CMD_ENCODE = "encode";
    public static final String CMD_EXIT = "exit";
    public static final String CMD_HELP = "help";
    public static final String CMD_INFO = "info";
    public static final String CMD_INPUT = "input";
    public static final String CMD_PROBABILITY = "prob";
    public static final String CMD_QUIT = "quit";
    public static final String CMD_SEND = "send";
    public static final String CMD_SENDIMG = "sendimg";
    public static final String CMD_SEND_ORIGINAL = "sendoriginal";
    public static final String CMD_STATE = "state";
    public static final String CMD_TEST_VECTOR = "testvector";
    public static final String CMD_TEST_STRING = "teststring";


    // errors
    public static final String ERROR_RANGE = ANSI_RED + "Error! Input probability must be in range [0,1]!" + ANSI_RESET;
    public static final String ERROR_NO_ARGUMENTS_FOR_CEP = ANSI_RED + "Error! No arguments found for command \"cep\". Expected \"cep {float numberInRangeFrom0To1}\"." + ANSI_RESET;
    public static final String ERROR_ENCODING = ANSI_RED + "Error! Invalid encoding string was provided." + ANSI_RESET;
    public static final String ERROR_DECODING_EMPTY = ANSI_RED + "Error! No received string sent from the channel" +
            " was found." + ANSI_RESET;
    public static final String ERROR_SENDING_NULL = ANSI_RED + "Error! A message must be encoded before sending it through the channel." + ANSI_RESET;
    public static final String ERROR_EDIT_EMPTY = ANSI_RED + "Error! A message must be sent through the channel before editing it." + ANSI_RESET;

    // messages
    public static final String MSG_SUCCESS = ANSI_GREEN + "Success!" + ANSI_RESET;
    public static final String MSG_DECODING_FAILED = ANSI_RED + "Decoding failed." + ANSI_RESET;
    public static final String MSG_ENCODING_FAILED = ANSI_RED + "Encoding failed." + ANSI_RESET;
    public static final String MSG_FAILED_CHANNEL_TRANSFER = ANSI_RED + "Message transfer through the channel was unsuccessful." + ANSI_RESET;
    public static final String MSG_EDIT_EMPTY = ANSI_RED + "First encode a message, then send it through the channel." + ANSI_RESET;
    public static final String MSG_COMPARING_RECEIVED_TO_ORIGINAL = "Comparing received message to original message...";
    public static final String MSG_CHECK_RECEIVED_BMP = "Message received through the channel. Check received-message.bmp file.";
    public static final String MSG_MESSAGE_RECEIVED = "Message received through the channel.";
    public static final String MSG_COMPARING_DECODED_TO_ORIGINAL = "Comparing decoded message to the original message...";
}
