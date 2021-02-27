package sample.Hierarchy;

import java.io.Externalizable;

public abstract class Unit implements Externalizable {
    protected String name;
    protected String imagePath;
    protected int rank;
    protected int attack;
    protected int defence;
    protected int speed;
    protected int count;

    public abstract String getName();
    public abstract void setImagePath(String path);
    public abstract String getImagePath();

    public abstract int getRank();
    public abstract boolean addRank();

    public abstract int getAttack();
    public abstract int getDefence();
    public abstract int getSpeed();

    public abstract boolean addWarriors(int count);
    public abstract void removeWarrior();

    public abstract Unit copy();

    public abstract int count();
}
