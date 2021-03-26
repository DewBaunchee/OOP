package sample.Hierarchy;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({Evocati.class})
public abstract class Cavalry extends Unit {
    protected boolean isMount;

    @XmlElement
    protected void setIsMount(boolean isMount) {
        this.isMount = isMount;
    }

    public boolean getIsMount() {
        return isMount;
    }

    public abstract void switchMount();
}
