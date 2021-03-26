package sample.Hierarchy;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.io.Externalizable;

@XmlSeeAlso({Velites.class, Hastati.class, Princeps.class, Triarii.class, Evocati.class})
public abstract class Unit implements Externalizable {
    protected String name;
    protected String imagePath;
    protected int rank;
    protected int attack;
    protected int defence;
    protected int speed;
    protected int count;

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    @XmlElement
    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getAttack() {
        return attack;
    }

    @XmlElement
    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getDefence() {
        return defence;
    }

    @XmlElement
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    @XmlElement
    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    @XmlElement
    public void setImagePath(String path) {
        this.imagePath = path;
    }

    public String getImagePath() {
        return imagePath;
    }

    public abstract boolean addRank();

    public abstract boolean addWarriors(int count);
    public abstract void removeWarrior();

    public abstract Unit copy();
}
