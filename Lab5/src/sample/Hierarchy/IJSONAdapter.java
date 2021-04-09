package sample.Hierarchy;

public interface IJSONAdapter {
    byte[] toJson();
    void fromJson(byte[] json);
}
