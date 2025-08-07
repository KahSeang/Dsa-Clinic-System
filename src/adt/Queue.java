package adt;

public class Queue<T> implements QueueInterface<T> {

    private static class Node<T> {

        T value;
        Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }
    private Node<T> front, rear;
    private int size = 0;

    public void enqueue(T value) {
        Node<T> node = new Node<>(value);
        if (rear != null) {
            rear.next = node;
        }
        rear = node;
        if (front == null) {
            front = node;
        }
        size++;
    }

    public T dequeue() {
        if (front == null) {
            return null;
        }
        T value = front.value;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return value;
    }

    public T peek() {
        return (front != null) ? front.value : null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // Search for the first index of a value (using equals)
    public int indexOf(T value) {
        Node<T> curr = front;
        int idx = 0;
        while (curr != null) {
            if (curr.value.equals(value)) {
                return idx;
            }
            curr = curr.next;
            idx++;
        }
        return -1;
    }
    

    // Filter: returns a new Queue<T> with elements matching the predicate
    public Queue<T> filter(Predicate<T> predicate) {
        Queue<T> result = new Queue<>();
        Node<T> curr = front;
        while (curr != null) {
            if (predicate.test(curr.value)) {
                result.enqueue(curr.value);
            }
            curr = curr.next;
        }
        return result;
    }

    // Predicate interface for filter
    public interface Predicate<T> {
        boolean test(T value);
    }
}
