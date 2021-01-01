package com.VU;

public final class Constants {
    public static final String INPUT_PREFIX = ">";

    // colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";

    // commands
    public static final String CMD_CHANGE_ERROR_PROB = "cep";
    public static final String CMD_PROBABILITY = "prob";
    public static final String CMD_INFO = "info";
    public static final String CMD_HELP = "help";
    public static final String CMD_ENCODE = "encode";
    public static final String CMD_DECODE = "decode";
    public static final String CMD_RESULT = "result";
    public static final String CMD_INPUT = "input";
    public static final String CMD_EXIT = "exit";
    public static final String CMD_QUIT = "quit";


    // errors
    public static final String ERROR_RANGE = ANSI_RED + "Error! Input probability must be in range [0,1]!" + ANSI_RESET;
    public static final String ERROR_NO_ARGUMENTS_FOR_CEP = ANSI_RED + "Error! No arguments found for command \"cep\". Expected \"cep {float numberInRangeFrom0To1}\"." + ANSI_RESET;
    public static final String ERROR_ENCODING = ANSI_RED + "Error! Invalid encoding string was provided." + ANSI_RESET;
    public static final String ERROR_DECODING_EMPTY = ANSI_RED + "Error! No encoded string was found." + ANSI_RESET;
}
