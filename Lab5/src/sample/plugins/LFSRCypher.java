package sample.plugins;

public class LFSRCypher implements IPlugin {
    private static final String keyRegex = "[01]";

    @Override
    public byte[] encrypt(byte[] plain, byte[] key) {
        LFSR lfsr = new LFSR(getStartValue(new String(key)));

        byte[] cypher = new byte[plain.length];
        for(int i = 0; i < plain.length; i++) {
            cypher[i] = (byte) (plain[i] ^ getKeyByte(lfsr));
        }
        return cypher;
    }

    @Override
    public byte[] decrypt(byte[] cypher, byte[] key) {
        LFSR lfsr = new LFSR(getStartValue(new String(key)));

        byte[] plain = new byte[cypher.length];
        for(int i = 0; i < cypher.length; i++) {
            plain[i] = (byte) (cypher[i] ^ getKeyByte(lfsr));
        }
        return plain;
    }

    @Override
    public String keyRegex() {
        return keyRegex;
    }

    @Override
    public String name() {
        return "Crypt with LFSR";
    }

    private static boolean[] getStartValue(String key) {
        boolean[] startValue = new boolean[key.length()];
        for(int i = 0; i < key.length(); i++) {
            startValue[i] = key.charAt(i) == '1';
        }
        return startValue;
    }

    private static byte getKeyByte(LFSR lfsr) {
        byte answer = 0;
        for(int i = 0; i < 7; i++) {
            answer = (byte) (answer | (lfsr.getNext() ? 1 : 0));
            answer = (byte) (answer << 1);
        }
        return (byte) (answer | (lfsr.getNext() ? 1 : 0));
    }

    private static class LFSR {
        static class Bit {
            boolean bit;
            Bit prev;

            Bit(boolean in) {
                bit = in;
                prev = null;
            }
        }

        private Bit left, right;

        public LFSR(boolean[] lfsr) {
            if(lfsr == null) return;
            for (boolean bit : lfsr) {
                if(right == null) {
                    right = left = new Bit(bit);
                } else {
                    Bit newBit = new Bit(bit);
                    newBit.prev = left;
                    left = newBit;
                }
            }
        }

        public boolean getNext() {
            boolean returnable = left.bit;
            left = left.prev;

            right.prev = new Bit(returnable ^ right.bit);
            right = right.prev;

            return returnable;
        }
    }

}

