package sample.Hierarchy;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.ArrayList;

@XmlRootElement
public class Legion implements Externalizable, Serializable {

    private static Legion instance;

    public static Legion getLegion() {
        if (instance == null) instance = new Legion();
        return instance;
    }

    public Warrior getGeneral() {
        return general;
    }

    @XmlElement
    public void setGeneral(Warrior general) {
        this.general = general;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public boolean setName(String name) {
        if (name == null || name.length() == 0 || name.length() > 20) return false;
        this.name = name;
        return true;
    }

    private Warrior general;
    private String name;
    private int count;
    private Unit[] units;

    public int getCount() {
        return count;
    }

    @XmlElement
    public void setCount(int count) {
        this.count = count;
    }

    public Unit[] getUnits() {
        return units;
    }

    @XmlElement
    public void setUnits(Unit[] units) {
        this.units = units;
    }

    Legion() {
        units = new Unit[20];
    }

    public Legion(String inName, Warrior inGeneral) {
        if (!setName(inName)) return;
        if (inGeneral == null) return;

        general = inGeneral;
        units = new Unit[20];
        count = 0;
    }

    public boolean addRankTo(int index) {
        if (index > -1 && index < count) {
            return units[index].addRank();
        }
        return false;
    }

    public boolean removeRankTo(int index) {
        if (index > -1 && index < count) {
            return units[index].removeRank();
        }
        return false;
    }

    public boolean addUnit(Unit unit) {
        if (count == 20) return false;
        units[count++] = unit;
        return true;
    }

    public boolean insertUnit(int index, Unit unit) {
        if (count == 20) return false;

        for(int i = index; i < count + 1; i++) {
            Unit temp = units[i];
            units[i] = unit;
            unit = temp;
        }

        count++;
        return true;
    }

    public Unit removeUnit(int index) {
        if (count == 0 || index < 0 || index >= count) return null;

        Unit temp = units[index];
        units[index] = null;
        for (int i = index + 1; i < count; i++) {
            units[index++] = units[i];
        }
        count--;
        return temp;
    }

    public Unit removeUnit(Unit unit) {
        if (unit == null) return null;

        for(int i = 0; i < count; i++) {
            if(unit.equals(units[i])) {
                return removeUnit(i);
            }
        }
        return null;
    }

    public boolean switchMount(int index) {
        if (count == 0 || index < 0 || index >= count || !(units[index] instanceof Cavalry)) return false;
        ((Cavalry) units[index]).switchMount();
        return true;
    }

    public ArrayList<Unit> getUnitList() {
        ArrayList<Unit> copiedList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            copiedList.add(units[i].copy());
        }
        return copiedList;
    }

    public void clear() {
        setName("");
        setGeneral(null);
        setCount(0);
        setUnits(new Unit[20]);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        general.writeExternal(out);
        out.writeObject(name);
        out.writeObject(count);

        for (int i = 0; i < count; i++) {
            out.writeObject(units[i].getClass());
            units[i].writeExternal(out);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        general.readExternal(in);
        name = (String) in.readObject();
        count = (int) in.readObject();

        units = new Unit[20];
        for (int i = 0; i < count; i++) {
            Unit unit = chooseObject(in);
            if (unit == null) throw new IOException("Invalid object");
            unit.readExternal(in);
            units[i] = unit;
        }
    }

    public byte[] xmlSerialize() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(this.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter sw = new StringWriter();
        marshaller.marshal(this, sw);
        return sw.toString().getBytes();
    }

    public void xmlDeserialize(StringReader xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(this.getClass());
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Legion legion = (Legion) unmarshaller.unmarshal(xml);
        this.name = legion.name;
        this.general = legion.general;
        this.count = legion.count;
        this.units = legion.units;
    }

    private Unit chooseObject(ObjectInput in) throws IOException, ClassNotFoundException {
        Class<?> type = (Class<?>) in.readObject();
        if (Hastati.class.equals(type)) return new Hastati("");
        if (Evocati.class.equals(type)) return new Evocati("");
        if (Princeps.class.equals(type)) return new Princeps("");
        if (Triarii.class.equals(type)) return new Triarii("");
        if (Velites.class.equals(type)) return new Velites("");
        return null;
    }

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder(name);
        answer.append(":\n").append("****General******\nName: ").append(general).append("\n*****************\n");

        for (int i = 0; i < count; i++) {
            answer.append(units[i].toString()).append("\n");
        }

        return answer.toString();
    }
}