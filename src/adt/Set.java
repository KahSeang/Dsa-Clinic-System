package adt;

public class Set<T> {
    private final List<T> items = new List<>();

    public boolean add(T value) {
        if (contains(value)) return false;
        items.add(value);
        return true;
    }

    public boolean contains(T value) {
        return items.indexOf(value) != -1;
    }

    public boolean remove(T value) {
        int idx = items.indexOf(value);
        if (idx == -1) return false;
        items.remove(idx);
        return true;
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}



