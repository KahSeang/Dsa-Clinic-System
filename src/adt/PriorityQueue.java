package adt;

public class PriorityQueue<T extends Comparable<T>> {
    private final List<T> list = new List<>();

    public void offer(T value) {
        list.add(value);
        list.sort();
    }

    public T poll() {
        if (list.isEmpty()) return null;
        return list.remove(0);
    }

    public T peek() {
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}



