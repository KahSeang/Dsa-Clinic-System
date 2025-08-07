package adt;

public class Stack<T> implements StackInterface<T> {

    private static class Node<T> {

        T value;
        Node<T> next;

        Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }
    private Node<T> top;
    private int size = 0;

    public void push(T value) {
        top = new Node<>(value, top);
        size++;
    }

    public T pop() {
        if (top == null) {
            return null;
        }
        T value = top.value;
        top = top.next;
        size--;
        return value;
    }

    public T peek() {
        return (top != null) ? top.value : null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // Search for the first index of a value (from top, using equals)
    public int indexOf(T value) {
        Node<T> curr = top;
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

    // Filter: returns a new Stack<T> with elements matching the predicate (order preserved)
    public Stack<T> filter(Predicate<T> predicate) {
        Stack<T> temp = new Stack<>();
        Stack<T> result = new Stack<>();
        Node<T> curr = top;
        while (curr != null) {
            if (predicate.test(curr.value)) {
                temp.push(curr.value);
            }
            curr = curr.next;
        }
        // Reverse to preserve order
        while (!temp.isEmpty()) {
            result.push(temp.pop());
        }
        return result;
    }

    // Predicate interface for filter
    public interface Predicate<T> {
        boolean test(T value);
    }
}
