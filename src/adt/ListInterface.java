package adt;

public interface ListInterface<T> {

    void add(T value);

    T get(int index);

    void set(int index, T value);

    T remove(int index);

    int size();

    boolean isEmpty();

    void clear();
}
