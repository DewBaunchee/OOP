package sample.plugins;

public interface IPlugin {
    byte[] encrypt(byte[] plain, byte[] key);
    byte[] decrypt(byte[] cypher, byte[] key);
    String keyRegex();
    String name();
}