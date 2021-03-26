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

    private Link begin, end;
    private int length;

    public List() {
        begin = end = null;
        length = 0;
    }

    public void add(T data) {
        Link addable = new Link(data);
        if(begin == null) {
            begin = end = addable;
        } else {
            end.next = addable;
            end = addable;
        }
        length++;
    }

    public int length() {
        return length;
    }

    @Override
    public String toString() {
        if(begin == null) {
            return "{ EMPTY }";
        }
        StringBuilder answer = new StringBuilder("{ ");

        Link current = begin;
        while(current.next != null) {
            answer.append(current.toString()).append(";\n");
            current = current.next;
        }

        return answer.append(end.toString()).append(" }").toString();
    }

    public Iterator getIterator() {
        return new Iterator();
    }

    public class Iterator {
        private Link next;

        public Iterator() {
            next = begin;
        }

        public T next() {
            T data = next.data;
            next = next.next;
            return data;
        }

        public boolean hasNext() {
            return next != null;
        }
    }
}
