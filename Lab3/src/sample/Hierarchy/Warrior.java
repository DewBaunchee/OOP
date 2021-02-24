package sample.Hierarchy;

public class Warrior {
    private final String name;

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
    public String toString() {
        return name;
    }
}
