package adt;

public interface StackInterface<T> {

    void push(T value);

    T pop();

    T peek();

    boolean isEmpty();

    int size();
}
