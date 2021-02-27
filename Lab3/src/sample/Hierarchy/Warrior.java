package sample.Hierarchy;

import java.io.*;

public class Warrior implements Externalizable {
    private String name;

    public String getName() {
        return name;
    }

    public Warrior(String inName) {
        name = inName;
    }

    public Warrior copy() {
        return new Warrior(name);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
    }

    @Override
    public String toString() {
        return name;
    }
}
