package com.VU;

import java.util.ArrayList;

public class Encoder {
    private ArrayList<Integer> encodingChain;

    public Encoder() {
        encodingChain = new ArrayList<>();
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
}
