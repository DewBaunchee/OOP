package sample.Hierarchy;

public interface Unit {

    String getName();
    void setImagePath(String path);
    String getImagePath();

    int getRank();
    void setRank(int rank);

    int getAttack();
    int getDefence();
    int getSpeed();

    boolean addWarriors(int count);
    void removeWarrior();

    Unit copy();

    int count();
}
