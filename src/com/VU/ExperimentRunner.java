package com.VU;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static com.VU.Utils.printDiff;

public class ExperimentRunner {
    Channel channel;
    CodeString codeString;

    public ExperimentRunner() {
        channel = new Channel();
        codeString = new CodeString();
    }

    public ExperimentRunner(float errorProbability) {
        channel = new Channel(errorProbability);
        codeString = new CodeString();
    }

    public void runVectorTests(float probability) {
        final int experimentCount = 1000;
        final int vectorLength = 100;
        String originalVector = "";

        for (int i = 0; i < vectorLength; i++) {
            if (Math.random() <= 0.5)
                originalVector += '1';
            else
                originalVector += '0';
        }

        int successfulDecodeCount = 0;
        int incorrectBitCount = 0;
        int lowestDifference = experimentCount * vectorLength;
        for (int i = 0; i < experimentCount; i++) {
            codeString.encode(originalVector);
            channel.sendMessage(codeString);
            codeString.decode();
            int diffCount = (int) IntStream.range(0, codeString.getRawString().length())
                    .filter(index -> codeString.getRawString().charAt(index) != codeString.getDecodedString().charAt(index))
                    .count();
            successfulDecodeCount += diffCount == 0 ? 1 : 0;
            incorrectBitCount += diffCount;
            lowestDifference = Math.min(lowestDifference, diffCount);
        }
        System.out.println("Success count: " + successfulDecodeCount + " / " + experimentCount);
        System.out.printf("Decoding success rate: %.4f", ((double) successfulDecodeCount / (double) experimentCount) * 100);
        System.out.println("%.");
        System.out.println("Incorrect bit count: " + incorrectBitCount + " / " + experimentCount * vectorLength);
        System.out.print("Average difference from original message (for " + experimentCount + " cases): ");
        double totalBitCount = vectorLength * experimentCount;
        System.out.printf("%.4f", (double) (incorrectBitCount / totalBitCount) * 100);
        System.out.println("%.");
        System.out.println("Message with the lowest amount of errors had " + lowestDifference + " errors.");


    }
}
