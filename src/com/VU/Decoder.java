package com.VU;

import java.util.ArrayList;

public class Decoder {
    private ArrayList<Integer> upperChain;
    private ArrayList<Integer> lowerChain;
    private ArrayList<Integer> MDE;
    public Decoder() {
        upperChain = new ArrayList<>();
        lowerChain = new ArrayList<>();
        MDE = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            upperChain.add(0);
            lowerChain.add(0);
        }
    }

    public Character getDecodedBit(ArrayList<Character> twoBitList) {
        int upperBit = -1;
        int lowerBit = -1;
        int returningBit;
        try {
            upperChain.add((int) twoBitList.get(0));
            if (upperChain.size() > 6) {
                upperBit = upperChain.get(0);
                upperChain.remove(0);
                lowerBit = upperChain.get(0) + upperChain.get(1) + upperChain.get(4) +
                        (int) twoBitList.get(0) + (int) twoBitList.get(1);
                MDE.add(lowerBit);
                lowerChain.add(lowerBit);
                if (lowerChain.size() > 6) {
                    MDE.add(lowerChain.get(0));
                    MDE.add(lowerChain.get(2));
                    MDE.add(lowerChain.get(5));
                    if (MDE.size() == 4) {
                        lowerBit = getMDEResult();
                        MDE.clear();
                    }
                }
            }
            returningBit = upperBit + lowerBit;
            return returningBit % 2 == 0 ? '0' : '1';
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return '-';
        }
    }

    private Character getMDEResult() throws Exception {
        if (MDE.size() == 4) {
            long zeroBitsCount = MDE.stream().filter(el -> el == 0).count();
            long oneBitsCount = MDE.stream().filter(el -> el == 1).count();
            return zeroBitsCount >= oneBitsCount ? '0' : '1';
        } else {
            throw (new Exception("MDE has " + MDE.size() + " elements!"));
        }
    }
}
