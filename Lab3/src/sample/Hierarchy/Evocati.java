package sample.Hierarchy;

public class Evocati implements Cavalry {
    private final String name;
    private String imagePath;
    private int rank;
    private int attack;
    private int defence;
    private int speed;
    private boolean isMount;
    private int count;

    public Evocati(String inName) {
        name = inName;
        rank = 1;
        attack = 40;
        defence = 30;
        speed = 80;
        isMount = true;
        count = 60;
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
    public boolean isMount() {
        return isMount;
    }

    @Override
    public void switchMount() {
        isMount = !isMount;
    }

    @Override
    public boolean addWarriors(int inCount) {
        if (inCount < 1 || inCount + count > 60) return false;

        count = count + inCount;

        return true;
    }

    @Override
    public void removeWarrior() {
        count--;
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public Evocati copy() {
        Evocati copied = new Evocati(name);
        copied.imagePath = imagePath;
        copied.isMount = isMount;
        copied.defence = defence;
        copied.attack = attack;
        copied.speed = speed;
        copied.rank = rank;
        copied.count = count;

        return copied;
    }

    @Override
    public String toString() {
        return "----Evocati------\n" +
                "Name: " + name + "\n" +
                "Rank: " + rank + "\n" +
                "Attack: " + attack + "\n" +
                "Defence: " + defence + "\n" +
                "Speed: " + speed + "\n" +
                "Mounted: " + (isMount ? "Yes" : "No") + "\n" +
                "Count: " + count();
    }
}
