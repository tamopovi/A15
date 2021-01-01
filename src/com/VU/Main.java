package com.VU;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
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

    // errors
    public static final String ERROR_RANGE = ANSI_RED + "Error! Input probability must be in range [0 - 1]!" + ANSI_RESET;
    public static final String ERROR_NO_ARGUMENTS_FOR_CEP = ANSI_RED + "Error! No arguments found for command \"cep\". Expected \"cep {float numberInRangeFrom0To1}\"." + ANSI_RESET;


    public static void commandNotFound(String inputStr) {
        System.out.println(ANSI_RED + "Command \"" + inputStr + "\" was not found." + ANSI_RESET);
    }

    public static void printCurrentErrorProbability() {
        System.out.println("Current error probability is: " + ANSI_GREEN + getErrorProbability() + ANSI_RESET + ".");
    }

    public static float getErrorProbability() {
        return errorProbability;
    }

    public static void setErrorProbability(float newProbability) {
        if (newProbability >= 0 && newProbability <= 1) {
            errorProbability = newProbability;
            System.out.println(ANSI_YELLOW + "Error probability was changed to: " + ANSI_GREEN + errorProbability + ANSI_RESET+ ".");
        }
    }

    public static float errorProbability = 0;

    public static void main(String[] args) {

        System.out.println("Welcome to A15 encoder/decoder!");
        Scanner scanner = new Scanner(System.in);
        String inputLine;
        do {
            System.out.print(">");
            inputLine = scanner.nextLine();
            String[] inputArgs = inputLine.split(" ");
            String command = inputArgs[0];
            String[] params = Arrays.copyOfRange(inputLine.split(" "), 1, inputArgs.length);
            if (params.length > 1)
                commandNotFound(inputLine);
            else
                switch (command.toLowerCase()) {
                    case CMD_CHANGE_ERROR_PROB: {
                        if (params.length == 0) {
                            System.out.println(ERROR_NO_ARGUMENTS_FOR_CEP);
                            break;
                        }
                        setErrorProbability(readProbability(params[0]));
                        break;
                    }
                    case CMD_PROBABILITY: {
                        printCurrentErrorProbability();
                        break;
                    }
                    case CMD_INFO: {
                        System.out.println("Program written by Vilnius University 4th year CS Bachelors student Povilas Tamošauskas, 2021.");
                        break;
                    }
                    case CMD_HELP: {
                        printHelp();
                    }
                    default: {
                        commandNotFound(inputLine);
                        break;
                    }
                    case "exit":
                    case "quit": {
                        break;
                    }
                }

        }
        while (!inputLine.equalsIgnoreCase("exit") && !inputLine.equalsIgnoreCase("quit"));
    }

    public static float readProbability(String inputStr) {
        float probability;
        inputStr = inputStr.replaceAll(",", ".");
        probability = Float.valueOf(inputStr);
        if (!((probability >= 0) && (probability <= 1))) {
            System.out.println(ANSI_RED + ERROR_RANGE + ANSI_RESET);
        }
        return probability;
    }

    public static void printHelp() {
        System.out.println("Commands: ");
        System.out.println(ANSI_CYAN + "cep" + ANSI_RESET + " — change error probability");
        System.out.println(ANSI_CYAN + "info" + ANSI_RESET + " — program info");
        System.out.println(ANSI_CYAN + "help" + ANSI_RESET + " — help menu");
    }
}
