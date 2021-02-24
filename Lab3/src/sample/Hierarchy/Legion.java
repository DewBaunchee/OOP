package sample.Hierarchy;

import java.util.ArrayList;

public class Legion {
    public Warrior getGeneral() {
        return general;
    }

    public void setGeneral(Warrior general) {
        this.general = general;
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        if(name == null || name.length() == 0 || name.length() > 20) return false;
        this.name = name;
        return true;
    }

    private Warrior general;
    private ArrayList<Unit> units;
    private String name;

    public Legion(String inName, Warrior inGeneral) {
        if(!setName(inName)) return;
        if(inGeneral == null) return;

        general = inGeneral;
        units = new ArrayList<>();
    }

    public boolean addUnit(Unit unit) {
        if(units.size() == 20) return false;
        units.add(unit);
        return true;
    }

    public Unit removeUnit(int index) {
        if(units.size() == 0 || index < 0 || index >= units.size()) return null;

        return units.remove(index);
    }

    public boolean switchMount(int index) {
        if(units.size() == 0 || index < 0 || index >= units.size() || !(units.get(index) instanceof Cavalry)) return false;
        ((Cavalry) units.get(index)).switchMount();
        return true;
    }

    public ArrayList<Unit> getUnitList() {
        ArrayList<Unit> copiedList = new ArrayList<>();
        for (Unit unit : units) {
            copiedList.add(unit.copy());
        }
        return copiedList;
    }

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder(name);
        answer.append(":\n").append("****General******\nName: ").append(general).append("\n*****************\n");

        for(Unit unit : units) {
            answer.append(unit.toString()).append("\n");
        }

        return answer.toString();
    }
}
