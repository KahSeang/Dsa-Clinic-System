package adt;

public interface QueueInterface<T> {

    void enqueue(T value);

    T dequeue();

    T peek();

    boolean isEmpty();

    int size();
}
