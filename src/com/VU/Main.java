package com.VU;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.VU.Constants.*;
import static com.VU.Utils.countOccurences;
import static com.VU.Utils.*;

public class Main {


    public static void commandNotFound(String inputStr) {
        System.out.println(ANSI_RED + "Command \"" + inputStr + "\" was not found." + ANSI_RESET);
    }


    public static void main(String[] args) {
        printGreeting();
        Scanner scanner = new Scanner(System.in);
        Channel channel = new Channel(DEFAULT_ERROR_PROBABILITY);
        CodeString codeString = new CodeString();
        String inputLine;
        do {
            System.out.print(INPUT_PREFIX);
            inputLine = scanner.nextLine();
            String[] inputArgs = inputLine.split(" ");
            String command = inputArgs[0];
            String[] params = Arrays.copyOfRange(inputLine.split(" "), 1, inputArgs.length);

            switch (command.toLowerCase()) {
                case CMD_CHANGE_ERROR_PROB: {
                    if (params.length == 0) {
                        System.out.println(ERROR_NO_ARGUMENTS_FOR_CEP);
                        break;
                    }
                    channel.setErrorProbability(readProbability(params[0]));
                    break;
                }

                case CMD_PROBABILITY: {
                    channel.printCurrentErrorProbability();
                    break;
                }
                case CMD_INFO: {
                    System.out.println("Program written by Vilnius University 4th year CS Bachelors student Povilas Tamosauskas, 2021.");
                    break;
                }
                case CMD_HELP: {
                    printHelp();
                    break;
                }
                case CMD_ENCODE: {
                    if (params.length == 0) {
                        codeString.encode(codeString.getRawString());
                    } else {
                        if (countOccurences(inputLine, '"') % 2 == 0) {
                            Pattern p = Pattern.compile("\"([^\"]*)\"");
                            Matcher m = p.matcher(inputLine);
                            while (m.find()) {
                                codeString.encode(m.group(1));
                            }
                        } else {
                            System.out.println(ERROR_ENCODING);
                        }
                    }
                    break;
                }
                case CMD_DECODE: {
                    codeString.decode();
                    break;
                }
                case CMD_STATE: {
                    channel.printCurrentErrorProbability();
                    codeString.printCurrentState();
                    break;
                }
                case CMD_INPUT: {
                    Pattern p = Pattern.compile("\"([^\"]*)\"");
                    Matcher m = p.matcher(inputLine);
                    while (m.find()) {
                        codeString.setRawString(m.group(1));
                    }
                    break;
                }
                case CMD_SEND: {
                    channel.sendMessage(codeString);
                    break;
                }
                case CMD_EDIT: {
                    codeString.editReceivedString();
                    break;
                }
                case CMD_SEND_ORIGINAL: {
                    System.out.println("Enter the path to the input BMP picture. The format is 'C:/.../picture.bmp'");
                    String fileName = scanner.next();
                    if (fileName != null)
                        try {
                            File fnew = new File(fileName);
                            BufferedImage bImage = ImageIO.read(fnew);
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            ImageIO.write(bImage, "bmp", bos);
                            byte[] imageBytes = bos.toByteArray();
                            codeString.setRawString(byteArrayToBinaryString(imageBytes));

                            channel.sendOriginalImage(codeString);
                            byte[] imageBytesFromChannel = binaryStringToByteArray(codeString.getReceivedString());
                            byteArrayToImage(imageBytesFromChannel, "received-message.bmp");
                        } catch (Exception e) {
                            System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
                        }
                    break;
                }
                case CMD_SENDIMG: {
                    System.out.println("Enter the path to the input BMP picture. The format is 'C:/.../picture.bmp'");
                    String fileName = scanner.next();
                    if (fileName != null)
                        try {
                            File fnew = new File(fileName);
                            BufferedImage bImage = ImageIO.read(fnew);
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            ImageIO.write(bImage, "bmp", bos);
                            byte[] imageBytes = bos.toByteArray();

                            // encode
                            codeString.encodeImg(byteArrayToBinaryString(imageBytes));

                            // send
                            channel.sendEncodedImage(codeString);

                            //decode
                            codeString.decode();
                            byte[] decodedBytes = binaryStringToByteArray(codeString.getDecodedString());
                            byteArrayToImage(decodedBytes, "decoded-message.bmp");
                            System.out.println("Received message was decoded. Check decoded-message.bmp file.");
                        } catch (Exception e) {
                            System.out.println(ANSI_RED + e.getMessage() + ANSI_RESET);
                        }
                    break;
                }
                case CMD_TEST_VECTOR: {
                    if (params.length == 0) {
                        System.out.println(ANSI_RED + "Provide a channel error probability for running vector experiments." + ANSI_RESET);
                        break;
                    }
                    float errorProbability = readProbability(params[0]);
                    ExperimentRunner er = new ExperimentRunner(errorProbability);
                    er.runVectorTests(errorProbability);
                    break;
                }
                case CMD_TEST_STRING: {
                    if (params.length == 0) {
                        System.out.println(ANSI_RED + "Provide a channel error probability for running text experiments." + ANSI_RESET);
                        break;
                    }
                    float errorProbability = readProbability(params[0]);
                    ExperimentRunner er = new ExperimentRunner(errorProbability);
                    er.runTextTests(errorProbability);
                    break;
                }
                default: {
                    commandNotFound(inputLine);
                    break;
                }
                case CMD_EXIT:
                case CMD_QUIT: {
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
        System.out.println(ANSI_CYAN + CMD_CHANGE_ERROR_PROB + ANSI_RESET + " — change error probability. Usage: "
                + ANSI_YELLOW + CMD_CHANGE_ERROR_PROB + " {float numberInRangeFrom0To1}" + ANSI_RESET + ".");
        System.out.println(ANSI_CYAN + CMD_DECODE + ANSI_RESET + " — decode last encoded string.");
        System.out.println(ANSI_CYAN + CMD_ENCODE + ANSI_RESET + " — encode provided string. Usage: "
                + ANSI_YELLOW + CMD_ENCODE + " \"{String someString}\"" + ANSI_RESET + ".");
        System.out.println(ANSI_CYAN + CMD_EDIT + ANSI_RESET + " — edit the received message from the channel. " +
                "A message must be sent before editing.");
        System.out.println(ANSI_CYAN + CMD_HELP + ANSI_RESET + " — help menu.");
        System.out.println(ANSI_CYAN + CMD_INFO + ANSI_RESET + " — program info.");
        System.out.println(ANSI_CYAN + CMD_INPUT + ANSI_RESET + " — input new raw string to be encoded or decoded." +
                " Usage: " + ANSI_YELLOW + CMD_INPUT + " \"{String someString}\"" + ANSI_RESET + ".");
        System.out.println(ANSI_CYAN + CMD_PROBABILITY + ANSI_RESET + " — print current error probability.");
        System.out.println(ANSI_CYAN + CMD_SEND + ANSI_RESET + " — send codestring through the channel. Encoded message must be set before running this command.");
        System.out.println(ANSI_CYAN + CMD_SENDIMG + ANSI_RESET + " — send image from provided path through the channel with encoding" +
                ".  Usage: " + ANSI_YELLOW + CMD_SENDIMG + ANSI_RESET + " press enter, add the image path, enter.");
        System.out.println(ANSI_CYAN + CMD_SEND_ORIGINAL + ANSI_RESET + " — send image from provided path through" +
                " the channel without encoding.  Usage: " + ANSI_YELLOW + CMD_SENDIMG + ANSI_RESET + " press enter, add the image path, enter.");
        System.out.println(ANSI_CYAN + CMD_STATE + ANSI_RESET + " — print current state of the coded vector.");
    }

    public static void printGreeting() {
        System.out.println("Welcome to A15 encoder/decoder!");
        System.out.println("Default channel error probability is: " + ANSI_GREEN + DEFAULT_ERROR_PROBABILITY + ANSI_RESET + ".");
        System.out.println("Get help by using the " + ANSI_CYAN + CMD_HELP + ANSI_RESET + " command.");
    }
}
