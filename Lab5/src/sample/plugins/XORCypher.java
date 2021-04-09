package sample.plugins;

public class XORCypher implements IPlugin {

    @Override
    public byte[] encrypt(byte[] plain, byte[] key) {
        int keyIndex = -1;
        byte[] cypher = new byte[plain.length];
        for(int i = 0; i < plain.length; i++) cypher[i] = (byte) (plain[i] ^ key[keyIndex = (keyIndex + 1) % key.length]);
        return cypher;
    }

    @Override
    public byte[] decrypt(byte[] cypher, byte[] key) {
        int keyIndex = -1;
        byte[] plain = new byte[cypher.length];
        for(int i = 0; i < cypher.length; i++) plain[i] = (byte) (cypher[i] ^ key[keyIndex = (keyIndex + 1) % key.length]);
        return plain;
    }

    @Override
    public String keyRegex() {
        return ".";
    }

    @Override
    public String name() {
        return "XOR crypt";
    }
}
