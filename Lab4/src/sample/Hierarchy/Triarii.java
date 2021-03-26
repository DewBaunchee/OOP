package sample.Hierarchy;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Triarii extends Unit {
    private static final int maxRank = 10;
    private static final int maxAttack = 80;
    private static final int maxDefence = 100;
    private static final int maxSpeed = 25;
    private static final int maxCount = 60;

    private static final int minRank = 1;
    private static final int minAttack = 60;
    private static final int minDefence = 70;
    private static final int minSpeed = 20;

    Triarii() {}

    public Triarii(String inName) {
        name = inName;
        rank = minRank;
        attack = minAttack;
        defence = minDefence;
        speed = minSpeed;
        count = maxCount;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public void setImagePath(String path) {
        imagePath = path;
    }

    @Override
    public int getRank() {
        return rank;
    }

    @Override
    public boolean addRank()
    {
        if(rank != maxRank) {
            rank++;
            attack = (int) ((maxAttack - minAttack) * ((double) (rank - 1) / (maxRank - minRank)) + minAttack);
            defence = (int) ((maxDefence - minDefence) * ((double) (rank - 1) / (maxRank - minRank)) + minDefence);
            speed = (int) ((maxSpeed - minSpeed) * ((double) (rank - 1) / (maxRank - minRank)) + minSpeed);
            return true;
        }
        return false;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public int getDefence() {
        return defence;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public boolean addWarriors(int inCount) {
        if(inCount < 1 || inCount + count > maxCount) return false;

        count = count + inCount;

        return true;
    }

    @Override
    public void removeWarrior() {
        count--;
    }

    @Override
    public Triarii copy() {
        Triarii copied = new Triarii(name);
        copied.imagePath = imagePath;
        copied.defence = defence;
        copied.attack = attack;
        copied.speed = speed;
        copied.rank = rank;
        copied.count = count;
        return copied;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeObject(rank);
        out.writeObject(attack);
        out.writeObject(defence);
        out.writeObject(speed);
        out.writeObject(count);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        rank = (int) in.readObject();
        attack = (int) in.readObject();
        defence = (int) in.readObject();
        speed = (int) in.readObject();
        count = (int) in.readObject();
    }

    @Override
    public String toString() {
        return "----Triarii------\n" +
                "Name: " + name + "\n" +
                "Rank: " + rank + "\n" +
                "Attack: " + attack + "\n" +
                "Defence: " + defence + "\n" +
                "Speed: " + speed + "\n" +
                "Count: " + count;
    }
}