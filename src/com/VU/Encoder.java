package com.VU;

import java.util.ArrayList;
import java.util.Collections;

public class Encoder {
    private ArrayList<Integer> encodingChain;

    public Encoder() {
        encodingChain = new ArrayList<Integer>();
        for (int i = 0; i < 6; i++)
            encodingChain.add(0);
    }

    public Character getEncodedBit(Character encodingBit) {

        int returningBit = -1;
        encodingChain.add((int) encodingBit);
        if (encodingChain.size() > 6) {
            returningBit = encodingChain.get(0) + encodingChain.get(1) + encodingChain.get(4) + (int) encodingBit;
            encodingChain.remove(0);
        }
        return returningBit % 2 == 0 ? '0' : '1';
    }

    public void printEncodingChain() {
        ArrayList reversedChain = new ArrayList(encodingChain);
        Collections.reverse(reversedChain);
        System.out.println(reversedChain.toString());
    }


}
