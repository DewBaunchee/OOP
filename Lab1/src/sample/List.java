package sample;

public class List<T> {
    private class Link {
        T data;
        Link next;

        Link(T inData) {
            data = inData;
            next = null;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    Link begin, end;
    int length;

    public List() {
        begin = end = null;
        length = 0;
    }

    public void add(T data) {
        if(begin == null) {
            begin = end = new Link(data);
        } else {

        }
        length++;
    }
}
