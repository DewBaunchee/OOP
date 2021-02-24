package sample.Hierarchy;

public class Velites implements Unit {
    private final String name;
    private String imagePath;
    private int rank;
    private int attack;
    private int defence;
    private int speed;
    private int count;

    public Velites(String inName) {
        name = inName;
        rank = 1;
        attack = 30;
        defence = 10;
        speed = 40;
        count = 120;
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
    public void setRank(int inRank) {
        rank = inRank;
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
        if(inCount < 1 || inCount + count > 120) return false;

        count = count + inCount;

        return true;
    }

    @Override
    public void removeWarrior() {
        count--;
    }

    @Override
    public Velites copy() {
        Velites copied = new Velites(name);
        copied.imagePath = imagePath;
        copied.defence = defence;
        copied.attack = attack;
        copied.speed = speed;
        copied.rank = rank;
        copied.count = count;
        return copied;
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public String toString() {
        return "----Velites------\n" +
                "Name: " + name + "\n" +
                "Rank: " + rank + "\n" +
                "Attack: " + attack + "\n" +
                "Defence: " + defence + "\n" +
                "Speed: " + speed + "\n" +
                "Count: " + count();
    }
}